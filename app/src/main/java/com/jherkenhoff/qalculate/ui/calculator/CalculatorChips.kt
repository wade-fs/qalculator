package com.jherkenhoff.qalculate.ui.calculator

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jherkenhoff.qalculate.R
import com.jherkenhoff.qalculate.model.UserPreferences
import com.jherkenhoff.qalculate.ui.common.SingleEnumSelectDialog

@Composable
fun CalculatorChips(
    userPreferences: UserPreferences,
    modifier: Modifier = Modifier,
    onUserPreferencesChanged: (UserPreferences) -> Unit = {},
) {

    var angleUnitDialogOpen by remember { mutableStateOf(false) }
    var approximationModeDialogOpen by remember { mutableStateOf(false) }
    var numericalDisplayModeDialogOpen by remember { mutableStateOf(false) }
    var numberFractionFormatDialogOpen by remember { mutableStateOf(false) }

    Row(
        modifier,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Chip(
            text =when (userPreferences.angleUnit) {
                UserPreferences.AngleUnit.DEGREES -> "DEG"
                UserPreferences.AngleUnit.RADIANS -> "RAD"
                UserPreferences.AngleUnit.GRADIANS -> "GRA"
            },
            onClick = { angleUnitDialogOpen = true }
        )
        Chip(
            text =
                when (userPreferences.numericalDisplayMode) {
                    UserPreferences.NumericalDisplayMode.NORMAL -> "NORM"
                    UserPreferences.NumericalDisplayMode.SCIENTIFIC -> "SCI"
                    UserPreferences.NumericalDisplayMode.ENGINEERING -> "ENG"
                },
            onClick = { numericalDisplayModeDialogOpen = true }
        )
        Chip(
            text =
                when (userPreferences.numberFractionFormat) {
                    UserPreferences.NumberFractionFormat.FRACTION_DECIMAL -> "0.00"
                    UserPreferences.NumberFractionFormat.FRACTION_DECIMAL_EXACT -> "0.00"
                    UserPreferences.NumberFractionFormat.FRACTION_FRACTIONAL -> "1/2"
                    UserPreferences.NumberFractionFormat.FRACTION_COMBINED -> "1+1/2"
                    UserPreferences.NumberFractionFormat.FRACTION_PERCENT -> "%"
                    UserPreferences.NumberFractionFormat.FRACTION_PERMILLE -> "‰"
                    UserPreferences.NumberFractionFormat.FRACTION_PERMYRIAD -> "‱"
                },
            onClick = { numberFractionFormatDialogOpen = true }
        )
        Chip(
            text = when (userPreferences.approximationMode) {
                UserPreferences.ApproximationMode.EXACT -> "EXACT"
                UserPreferences.ApproximationMode.TRY_EXACT -> "EXACT"
                UserPreferences.ApproximationMode.APPROXIMATE -> "APPROX"
            },
            onClick = { approximationModeDialogOpen = true },
            highlight = true
        )
    }


    if (angleUnitDialogOpen) {
        val title = stringResource(R.string.chip_angle_unit)
        val deg = stringResource(R.string.chip_angle_unit_degrees)
        val rad = stringResource(R.string.chip_angle_unit_radians)
        val gra = stringResource(R.string.chip_angle_unit_gradians)
        SingleEnumSelectDialog<UserPreferences.AngleUnit>(
            title,
            enumLabelMap = { when (it) {
                UserPreferences.AngleUnit.DEGREES -> deg
                UserPreferences.AngleUnit.RADIANS -> rad
                UserPreferences.AngleUnit.GRADIANS -> gra
            }},
            currentSelection = userPreferences.angleUnit,
            onSelect = { onUserPreferencesChanged(userPreferences.copy(angleUnit = it)) },
            onDismissRequest = { angleUnitDialogOpen = false }
        )
    }

    if (approximationModeDialogOpen) {
        val title = stringResource(R.string.chip_approximation_mode)
        val exact = stringResource(R.string.chip_approximation_mode_exact)
        val tryExact = stringResource(R.string.chip_approximation_mode_try_exact)
        val approx = stringResource(R.string.chip_approximation_mode_approximate)
        SingleEnumSelectDialog<UserPreferences.ApproximationMode>(
            title,
            enumLabelMap = { when (it) {
                UserPreferences.ApproximationMode.EXACT -> exact
                UserPreferences.ApproximationMode.TRY_EXACT -> tryExact
                UserPreferences.ApproximationMode.APPROXIMATE -> approx
            }},
            currentSelection = userPreferences.approximationMode,
            onSelect = { onUserPreferencesChanged(userPreferences.copy(approximationMode = it)) },
            onDismissRequest = { approximationModeDialogOpen = false }
        )
    }

    if (numericalDisplayModeDialogOpen) {
        val title = stringResource(R.string.chip_numerical_display)
        val normal = stringResource(R.string.chip_numerical_display_normal)
        val sci = stringResource(R.string.chip_numerical_display_scientific)
        val eng = stringResource(R.string.chip_numerical_display_engineering)
        SingleEnumSelectDialog<UserPreferences.NumericalDisplayMode>(
            title,
            enumLabelMap = { when (it) {
                UserPreferences.NumericalDisplayMode.NORMAL -> normal
                UserPreferences.NumericalDisplayMode.SCIENTIFIC -> sci
                UserPreferences.NumericalDisplayMode.ENGINEERING -> eng
            }},
            currentSelection = userPreferences.numericalDisplayMode,
            onSelect = { onUserPreferencesChanged(userPreferences.copy(numericalDisplayMode = it)) },
            onDismissRequest = { numericalDisplayModeDialogOpen = false }
        )
    }

    if (numberFractionFormatDialogOpen) {
        val title = stringResource(R.string.chip_number_fraction_format)
        val decimal = stringResource(R.string.chip_number_fraction_format_decimal)
        val decimalExact = stringResource(R.string.chip_number_fraction_format_decimal_exact)
        val fractional = stringResource(R.string.chip_number_fraction_format_fractional)
        val combined = stringResource(R.string.chip_number_fraction_format_combined)
        val percent = stringResource(R.string.chip_number_fraction_format_percent)
        val permille = stringResource(R.string.chip_number_fraction_format_permille)
        val permyriad = stringResource(R.string.chip_number_fraction_format_permyriad)
        SingleEnumSelectDialog<UserPreferences.NumberFractionFormat>(
            title,
            enumLabelMap = { when (it) {
                UserPreferences.NumberFractionFormat.FRACTION_DECIMAL -> decimal
                UserPreferences.NumberFractionFormat.FRACTION_DECIMAL_EXACT -> decimalExact
                UserPreferences.NumberFractionFormat.FRACTION_FRACTIONAL -> fractional
                UserPreferences.NumberFractionFormat.FRACTION_COMBINED -> combined
                UserPreferences.NumberFractionFormat.FRACTION_PERCENT -> percent
                UserPreferences.NumberFractionFormat.FRACTION_PERMILLE -> permille
                UserPreferences.NumberFractionFormat.FRACTION_PERMYRIAD -> permyriad
            }},
            currentSelection = userPreferences.numberFractionFormat,
            onSelect = { onUserPreferencesChanged(userPreferences.copy(numberFractionFormat = it)) },
            onDismissRequest = { numberFractionFormatDialogOpen = false }
        )
    }
}

@Composable
fun Chip(
    text: String,
    onClick: () -> Unit = {},
    highlight: Boolean = false
) {
    Surface(
        color = if (highlight) MaterialTheme.colorScheme.tertiary else Color.Transparent,
        border = if (highlight) null else BorderStroke(1.dp, MaterialTheme.colorScheme.tertiary),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.height(28.dp),
        onClick = onClick
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            Text(text, style = MaterialTheme.typography.labelSmall)
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun DefaultPreview() {
    CalculatorChips(
        userPreferences = UserPreferences(),
        onUserPreferencesChanged = {}
    )
}