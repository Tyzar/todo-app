package com.tyzar.test.todoapp.ui.components.sheets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.yearMonth
import com.tyzar.test.todoapp.ui.components.ConfirmSection
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerSheet(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    sheetState: SheetState,
    initialDate: LocalDate?,
    onCancel: () -> Unit,
    onSave: (selectedDate: LocalDate?) -> Unit
) {
    AppModalSheet(
        modifier = modifier.height(620.dp),
        sheetState = sheetState,
        title = "Set Date",
        onDismissRequest = onDismissRequest
    ) {
        val coroutineScope = rememberCoroutineScope()

        var selectedDate by remember {
            mutableStateOf(initialDate)
        }

        val daysOfWeek = remember {
            daysOfWeek()
        }

        val calendarState = rememberCalendarState(
            startMonth = getMonthOf(initialDate),
            endMonth = getMonthOf(initialDate).plusMonths(5),
        )

        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.height(20.dp))
            MonthNavigator(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                visibleMonth = calendarState.firstVisibleMonth.yearMonth,
                onPrev = {
                    coroutineScope.launch {
                        calendarState.animateScrollToMonth(
                            calendarState.firstVisibleMonth.yearMonth.minusMonths(
                                1
                            )
                        )
                    }
                },
                onNext = {
                    coroutineScope.launch {
                        calendarState.animateScrollToMonth(
                            calendarState.firstVisibleMonth.yearMonth.plusMonths(
                                1
                            )
                        )
                    }
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            DaysOfWeekTitle(daysOfWeek = daysOfWeek)
            HorizontalCalendar(
                modifier = Modifier.fillMaxWidth(),
                state = calendarState,
                dayContent = {
                    Day(day = it, isSelected = selectedDate == it.date, onDayClicked = {
                        selectedDate = if (selectedDate == it.date) null
                        else it.date
                    })
                })
            Spacer(modifier = Modifier.height(30.dp))
            ConfirmSection(
                negativeText = "Cancel",
                positiveText = "Save",
                onNegative = onCancel, onPositive = {
                    onSave(selectedDate)
                })
        }
    }
}

@Composable
fun MonthNavigator(
    modifier: Modifier = Modifier,
    visibleMonth: YearMonth,
    onNext: () -> Unit,
    onPrev: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            modifier = Modifier
                .size(24.dp)
                .clickable {
                    onPrev()
                },
            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
            contentDescription = "prev-month"
        )
        Text(
            modifier = Modifier.weight(1f),
            text = visibleMonth.format(DateTimeFormatter.ofPattern("MMMM", Locale.getDefault())),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold
            )
        )
        Icon(
            modifier = Modifier
                .size(24.dp)
                .clickable {
                    onNext()
                },
            imageVector = Icons.AutoMirrored.Outlined.ArrowForward,
            contentDescription = "next-month"
        )
    }
}

@Composable
fun DaysOfWeekTitle(daysOfWeek: List<DayOfWeek>) {
    Row(modifier = Modifier.fillMaxWidth()) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.Gray
                )
            )
        }
    }
}

@Composable
fun Day(day: CalendarDay, isSelected: Boolean, onDayClicked: () -> Unit) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clickable(
                enabled = day.position == DayPosition.MonthDate,
                onClick = { onDayClicked() }
            ), // This is important for square sizing!
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier
                .clip(MaterialTheme.shapes.small)
                .background(
                    color = if (isSelected) MaterialTheme.colorScheme.primary
                    else Color.Transparent
                )
                .padding(horizontal = 5.dp, vertical = 4.dp),
            text = day.date.dayOfMonth.toString(), style = MaterialTheme.typography.bodyMedium.copy(
                color = if (isSelected) MaterialTheme.colorScheme.onPrimary else Color.Black
            )
        )
    }
}

private fun getMonthOf(date: LocalDate?): YearMonth {
    if (date == null) {
        return LocalDate.now().yearMonth
    }

    return date.yearMonth
}