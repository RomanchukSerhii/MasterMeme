package com.serhiiromanchuk.mastermeme.presentation.screens.home

import android.net.Uri
import com.serhiiromanchuk.mastermeme.domain.entity.Meme
import com.serhiiromanchuk.mastermeme.domain.rejpository.MemeDbRepository
import com.serhiiromanchuk.mastermeme.presentation.core.base.BaseViewModel
import com.serhiiromanchuk.mastermeme.presentation.core.utils.DropdownSortItem
import com.serhiiromanchuk.mastermeme.presentation.screens.home.handling.HomeActionEvent
import com.serhiiromanchuk.mastermeme.presentation.screens.home.handling.HomeUiEvent
import com.serhiiromanchuk.mastermeme.presentation.screens.home.handling.HomeUiEvent.BottomSheetDismissed
import com.serhiiromanchuk.mastermeme.presentation.screens.home.handling.HomeUiEvent.DeleteDialogConfirmed
import com.serhiiromanchuk.mastermeme.presentation.screens.home.handling.HomeUiEvent.FabClicked
import com.serhiiromanchuk.mastermeme.presentation.screens.home.handling.HomeUiEvent.MemeClicked
import com.serhiiromanchuk.mastermeme.presentation.screens.home.handling.HomeUiEvent.MemeFavouriteToggled
import com.serhiiromanchuk.mastermeme.presentation.screens.home.handling.HomeUiEvent.MemeLongPressed
import com.serhiiromanchuk.mastermeme.presentation.screens.home.handling.HomeUiEvent.MemeSelectionToggled
import com.serhiiromanchuk.mastermeme.presentation.screens.home.handling.HomeUiEvent.SelectionModeToggled
import com.serhiiromanchuk.mastermeme.presentation.screens.home.handling.HomeUiEvent.ShareIconClicked
import com.serhiiromanchuk.mastermeme.presentation.screens.home.handling.HomeUiEvent.ShowDeleteDialog
import com.serhiiromanchuk.mastermeme.presentation.screens.home.handling.HomeUiEvent.SortOptionClicked
import com.serhiiromanchuk.mastermeme.presentation.screens.home.handling.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

private typealias BaseHomeViewModel = BaseViewModel<HomeUiState, HomeUiEvent, HomeActionEvent>

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val memeDbRepository: MemeDbRepository
) : BaseHomeViewModel() {
    override val initialState: HomeUiState
        get() = HomeUiState()

    private val favoriteSortMemesFlow = memeDbRepository.getMemesFavouriteSorted()
    private val isSortedByDate = MutableStateFlow(false)

    init {
        launch {
            val cleanUpJob = launch { memeDbRepository.cleanUpInvalidMemes() }
            cleanUpJob.join()

            val unselectedJob = launch { memeDbRepository.unselectedAllMemes() }
            unselectedJob.join()

            combine(
                favoriteSortMemesFlow,
                isSortedByDate
            ) { favouriteSortMemes, isSortedByDate ->
                if (isSortedByDate) {
                    favouriteSortMemes.sortedByDescending { it.creationTimestamp }
                } else favouriteSortMemes
            }.collect { memes ->
                updateState { it.copy(memes = memes) }
            }
        }
    }

    override fun onEvent(event: HomeUiEvent) {
        when (event) {
            FabClicked -> updateBottomSheetState(true)
            BottomSheetDismissed -> updateBottomSheetState(false)
            SelectionModeToggled -> launch { toggleSelectionMode() }
            DeleteDialogConfirmed -> launch { deleteMemes() }
            ShareIconClicked -> shareSelectedMemes()
            is MemeFavouriteToggled -> toggleFavouriteMeme(memeId = event.memeId)
            is SortOptionClicked -> updateTopBarSelectedItem(event.sortItem)
            is MemeClicked -> {
                updateBottomSheetState(false)
                sendActionEvent(HomeActionEvent.NavigateToEditor(event.memeResId))
            }

            is MemeLongPressed -> launch { toggleSelectionMode(event.selectedMeme) }
            is MemeSelectionToggled -> toggleSelectedMeme(event.meme)
            is ShowDeleteDialog -> showDeleteDialog(event.isVisible)

        }
    }

    private fun shareSelectedMemes() {
        val memeUriList = currentState.memes.filter { it.isSelected }.map { Uri.parse(it.filePath) }
        launch { sendActionEvent(HomeActionEvent.ShareMemes(memeUriList)) }
    }

    private fun showDeleteDialog(isVisible: Boolean) {
        updateState { it.copy(showDeleteDialog = isVisible) }
    }

    private suspend fun deleteMemes() {
        val deleteJob = launch { memeDbRepository.deleteSelectedMemes() }
        deleteJob.join()
        updateState {
            it.copy(
                showDeleteDialog = false,
                isSelectionMode = false
            )
        }
    }

    private fun toggleFavouriteMeme(memeId: Int) {
        val meme = currentState.memes.find { it.id == memeId }
        meme?.let {
            launch {
                memeDbRepository.upsertMeme(meme.copy(isFavourite = !meme.isFavourite))
            }
        }
    }

    private fun updateBottomSheetState(openBottomSheet: Boolean) {
        updateState { it.copy(bottomSheetOpened = openBottomSheet) }
    }

    private fun updateTopBarSelectedItem(selectedItem: DropdownSortItem) {
        when (selectedItem) {
            DropdownSortItem.Favourite -> isSortedByDate.value = false
            DropdownSortItem.Newest -> isSortedByDate.value = true
        }
        updateState { it.copy(selectedItem = selectedItem) }
    }

    private suspend fun toggleSelectionMode(selectedMeme: Meme? = null) {
        if (selectedMeme != null) {
            val updatedMemeJob = toggleSelectedMeme(selectedMeme)
            updatedMemeJob.join()
            updateSelectionMode()
        } else {
            val updatedMemes = currentState.memes.map { it.copy(isSelected = false) }
            val updatedJob = launch { memeDbRepository.updateMemes(updatedMemes) }
            updatedJob.join()
            updateSelectionMode()
        }
    }

    private fun updateSelectionMode() {
        updateState { it.copy(isSelectionMode = !currentState.isSelectionMode) }
    }

    private fun toggleSelectedMeme(meme: Meme): Job {
        val updatedMeme = meme.copy(isSelected = !meme.isSelected)
        return launch { memeDbRepository.upsertMeme(updatedMeme) }
    }
}