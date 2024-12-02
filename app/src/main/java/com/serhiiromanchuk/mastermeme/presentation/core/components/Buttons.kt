package com.serhiiromanchuk.mastermeme.presentation.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serhiiromanchuk.mastermeme.R
import com.serhiiromanchuk.mastermeme.presentation.core.utils.GradientScheme
import com.serhiiromanchuk.mastermeme.presentation.theme.Manrope

@Composable
fun MasterMemeFAB(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = stringResource(R.string.new_meme_button)
        )
    }
}

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    labelText: String,
    enabled: Boolean = true
) {
    Button(
        modifier = modifier
            .background(
                brush = GradientScheme.PrimaryGradient,
                shape = RoundedCornerShape(8.dp)
            )
            .height(40.dp),
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        enabled = enabled,
        contentPadding = PaddingValues(0.dp)
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = labelText,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
fun OutlinedButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    labelText: String,
    enabled: Boolean = true
) {
    Button(
        modifier = modifier
            .border(
                width = 1.dp,
                brush = GradientScheme.PrimaryGradient,
                shape = RoundedCornerShape(8.dp)
            )
            .height(40.dp),
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        enabled = enabled,
        contentPadding = PaddingValues(0.dp)
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = labelText,
            style = TextStyle(
                fontFamily = Manrope,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                brush = GradientScheme.PrimaryGradient
            )
        )
    }
}