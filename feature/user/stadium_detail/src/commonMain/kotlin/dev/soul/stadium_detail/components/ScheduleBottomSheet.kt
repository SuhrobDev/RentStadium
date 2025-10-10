package dev.soul.stadium_detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.soul.domain.model.user.available.response.AvailableModel
import dev.soul.domain.model.user.upcoming_days.response.UpcomingDaysModel
import dev.soul.shared.components.BaseModalBottomSheet
import dev.soul.shared.utils.Logger

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleBottomSheet(
    modifier: Modifier = Modifier,
    sheetState: SheetState,
    selectedDate: Int,
    availableLoading: Boolean,
    weeks: List<UpcomingDaysModel>,
    available: List<AvailableModel>,
    selectedAvailable: List<AvailableModel>,
    onWeekClick: (Int) -> Unit,
    onAvailableClick: (AvailableModel) -> Unit,
    onDismiss: () -> Unit,
) {

    BaseModalBottomSheet(
        modifier = modifier,
        sheetState = sheetState,
        onDismiss = onDismiss,
        content = {
            BoxWithConstraints {
                val maxHeight = maxHeight * 0.8f
                Column(
                    Modifier.fillMaxWidth()
                        .heightIn(maxHeight)
                        .padding(16.dp)
                ) {
                    WeeksSection(
                        weeks = weeks,
                        selectedTabIndex = selectedDate,
                        onWeekClick = onWeekClick
                    )

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(vertical = 12.dp)
                    ) {

                        if (availableLoading)
                            items(7) {
                                ShimmerAvailable(
                                    modifier = Modifier.weight(1f),
                                )
                            }
                        else
                            items(available, key = { it.id }) { item ->
                                AvailableItems(
                                    modifier = Modifier.weight(1f),
                                    item = item,
                                    isSelected = selectedAvailable.any { it.id == item.id },
                                    onItemClick = onAvailableClick
                                )
                            }


                    }
                }
            }
        }
    )
}