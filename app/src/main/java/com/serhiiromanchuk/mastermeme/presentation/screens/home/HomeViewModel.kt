package com.serhiiromanchuk.mastermeme.presentation.screens.home

import com.serhiiromanchuk.mastermeme.domain.rejpository.MemeDbRepository
import com.serhiiromanchuk.mastermeme.presentation.core.base.BaseViewModel
import com.serhiiromanchuk.mastermeme.presentation.core.utils.TopBarSortItem
import com.serhiiromanchuk.mastermeme.presentation.screens.home.handling.HomeActionEvent
import com.serhiiromanchuk.mastermeme.presentation.screens.home.handling.HomeUiEvent
import com.serhiiromanchuk.mastermeme.presentation.screens.home.handling.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

private typealias BaseHomeViewModel = BaseViewModel<HomeUiState, HomeUiEvent, HomeActionEvent>

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val memeDbRepository: MemeDbRepository
) : BaseHomeViewModel() {
    override val initialState: HomeUiState
        get() = HomeUiState()

    private val favoriteSortMemesFlow = memeDbRepository.getMemesFavouriteSorted()
    private val dateSortMemesFlow = memeDbRepository.getMemesDateSorted()
    private val isSortedByDate = MutableStateFlow(false)

    init {
        launch {
            val cleanUpJob = launch {
                memeDbRepository.cleanUpInvalidMemes()
            }
            cleanUpJob.join()
            combine(
                favoriteSortMemesFlow,
                dateSortMemesFlow,
                isSortedByDate
            ) { favouriteSortMemes, dateSortMemes, isSortedByDate ->
                if (isSortedByDate) dateSortMemes else favouriteSortMemes
            }.collect { memes ->
                updateState { it.copy(memes = memes) }
            }
        }
    }

    override fun onEvent(event: HomeUiEvent) {
        when (event) {
            HomeUiEvent.FabClicked -> updateBottomSheetState(true)
            HomeUiEvent.BottomSheetDismissed -> updateBottomSheetState(false)
            is HomeUiEvent.MemeClicked -> {
                updateBottomSheetState(false)
                sendActionEvent(HomeActionEvent.NavigateToEditor(event.memeResId))
            }

            is HomeUiEvent.MemeFavouriteToggled -> toggleFavouriteMeme(memeId = event.memeId)
            is HomeUiEvent.SortOptionClicked -> updateTopBarSelectedItem(event.sortItem)
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

    private fun updateTopBarSelectedItem(selectedItem: TopBarSortItem) {
        when (selectedItem) {
            TopBarSortItem.Favourite -> isSortedByDate.value = false
            TopBarSortItem.Newest -> isSortedByDate.value = true
        }
        updateState { it.copy(selectedItem = selectedItem) }
    }
}