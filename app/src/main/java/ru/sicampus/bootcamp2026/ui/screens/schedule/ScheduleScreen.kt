package ru.sicampus.bootcamp2026.ui.screens.schedule

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import ru.sicampus.bootcamp2026.R
import ru.sicampus.bootcamp2026.ui.root.nav.ItemsNav
import ru.sicampus.bootcamp2026.ui.root.theme.BlueMain
import ru.sicampus.bootcamp2026.utils.TimeUtils
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale


data class TestBookData(
    val id: Long = 0,
    val title: String = "",
    val address: String = "",
    val description: String = "",
    val organizerId: Long = 0,
    val organizerName: String = "",
    val date: Long? = null,
    val timeStart: String = "",
    val timeEnd: String = ""
)


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScheduleScreen(
    context: Context,
    navController: NavHostController,
    viewModel: ScheduleViewModel = viewModel(factory = ScheduleViewModelFactory.create(context)),
    index1: MutableState<Int>
) {
    val uiState by viewModel.uiState.collectAsState()
    var isRefreshing by remember { mutableStateOf(false) }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xffEEEEEE))
    ) {
        IconButton(
            onClick = {
                navController.navigate(ItemsNav.BottomNavItems[3].route)
            }, colors = IconButtonDefaults.iconButtonColors(
                containerColor = Color(0xff155DFC)
            ), modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(10.dp)
                .size(54.dp)
                .zIndex(1f)
        ) {
            Icon(
                painterResource(R.drawable.plus),
                "", tint = Color.White
            )
        }

        Column(
            Modifier.fillMaxSize()
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                Column(
                    Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    DatePick(viewModel)
                    var selectedIndex by remember { mutableIntStateOf(0) }
                    val options = listOf(
                        stringResource(R.string.day), stringResource(R.string.week),
                        stringResource(R.string.month)
                    )
                    Spacer(Modifier.size(10.dp))
                    SingleChoiceSegmentedButtonRow(
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .align(Alignment.CenterHorizontally)
                    ) {
                        options.forEachIndexed { index, label ->
                            SegmentedButton(
                                modifier =
                                    Modifier,
                                shape = SegmentedButtonDefaults.itemShape(
                                    index = index,
                                    count = options.size
                                ),
                                onClick = { selectedIndex = index },
                                selected = index == selectedIndex,
                                label = { Text(label) },
                                colors = SegmentedButtonDefaults.colors(
                                    activeContainerColor = Color(0xff155DFC).copy(0.7f),
                                    activeContentColor = Color.White,
                                    inactiveContainerColor = Color.White
                                )
                            )
                        }
                    }
                    Spacer(Modifier.size(8.dp))
                }
            }
            Spacer(Modifier.size(10.dp))

            when (uiState) {
                is ScheduleState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(64.dp)
                        )
                    }
                }

                is ScheduleState.DayData -> DayContent(viewModel,navController, index1)
                is ScheduleState.ErrorData -> ErrorContent(viewModel)
            }

        }
    }
}

@Composable
fun DayContent(
    viewModel: ScheduleViewModel,
    navHostController: NavHostController,
    ind: MutableState<Int>
) {
    val state by viewModel.state.collectAsState()
    var isRefreshing by remember { mutableStateOf(false) }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing)

    SwipeRefresh(
        modifier = Modifier.fillMaxSize(),
        state = swipeRefreshState,
        onRefresh = { viewModel.refresh() },
    ) {
        if (state.dayMeetings.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(Modifier.size(36.dp))
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = state.errorMessage ?: "Встреч нет",
                        color = Color.Blue,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .fillMaxWidth(0.9f)
                    )
                }
            }
        } else {

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(state.dayMeetings) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(0.94f),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        onClick = {
                            ind.value = state.dayMeetings.indexOf(it)
                            navHostController.navigate(ItemsNav.BottomNavItems[4].route)
                        }
                    ) {
                        Row(Modifier.fillMaxWidth()) {
                            Box(
                                Modifier
                                    .width(15.dp)
                                    .clip(
                                        RoundedCornerShape(
                                            topStart = 15.dp,
                                            bottomStart = 15.dp
                                        )
                                    )
                                    .zIndex(1f)
                                    .height(if (it.description != null) 120.dp else 86.dp)
                                    .background(BlueMain.copy(0.7f))
                            )

                            Column(
                                Modifier.padding(
                                    top = 15.dp,
                                    start = 10.dp,
                                    end = 10.dp,
                                    bottom = 10.dp
                                )
                            ) {
                                Row(
                                    Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        it.title,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Text(
                                        TimeUtils.timeHourMinutes(it.timeStart) ?: "Indefinite",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color.Black.copy(0.5f)
                                    )
                                }
                                Spacer(Modifier.size(5.dp))

                                if (it.description != null) {
                                    Text(
                                        it.description,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color.Black.copy(0.5f),
                                        maxLines = 2,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Spacer(Modifier.size(20.dp))
                                }

                                Row {
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(15.dp))
                                            .background(color = Color(0xffEFF6FF))
                                    ) {
                                        Row {
                                            Icon(
                                                painterResource(R.drawable.clock), "",
                                                Modifier
                                                    .size(24.dp)
                                                    .padding(
                                                        start = 2.dp, end = 2.dp,
                                                        top = 4.dp, bottom = 4.dp
                                                    ),
                                                tint = Color(0xff6151E8)
                                            )
                                            Text(
                                                "${TimeUtils.timeHourMinutes(it.timeStart)}-${
                                                    TimeUtils.timeHourMinutes(
                                                        it.timeEnd
                                                    )
                                                }", color = Color(0xff6151E8),
                                                fontSize = 12.sp, fontWeight = FontWeight.SemiBold,
                                                modifier = Modifier.padding(
                                                    top = 5.dp,
                                                    bottom = 5.dp,
                                                    end = 8.dp
                                                )
                                            )
                                        }
                                    }
                                    Spacer(Modifier.size(12.dp))
                                    Box(
                                        Modifier
                                            .clip(RoundedCornerShape(15.dp))
                                            .background(
                                                color = Color(0xffDEDEDE)
                                            )
                                    ) {
                                        Row {
                                            Icon(
                                                painterResource(R.drawable.location), "",
                                                Modifier
                                                    .size(24.dp)
                                                    .padding(
                                                        start = 1.dp, end = 1.dp,
                                                        top = 4.dp, bottom = 4.dp
                                                    )
                                            )
                                            Text(
                                                it.address, color = Color.Black,
                                                fontSize = 12.sp, fontWeight = FontWeight.SemiBold,
                                                modifier = Modifier.padding(
                                                    top = 5.dp,
                                                    bottom = 5.dp,
                                                    end = 8.dp
                                                )
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                    Spacer(Modifier.size(12.dp))

                }
            }
        }
    }
}

@Composable
fun ErrorContent(viewModel: ScheduleViewModel) {

    val state by viewModel.state.collectAsState()
    var isRefreshing by remember { mutableStateOf(false) }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing)

    SwipeRefresh(
        modifier = Modifier.fillMaxSize(),
        state = swipeRefreshState,
        onRefresh = { viewModel.refresh() },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = state.errorMessage ?: "Обновите экран",
                    color = Color.Red,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .fillMaxWidth(0.9f)
                )
            }
        }
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DatePick(viewModel: ScheduleViewModel) {

    val state by viewModel.state.collectAsState()

    val minDate = LocalDate.now().minusDays(30)
    val maxDate = LocalDate.now().plusDays(30)
    val shortDays = remember {
        listOf("Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс")
    }
    Box(
        modifier = Modifier
            .height(45.dp)
            .fillMaxWidth(0.95f)
            .clip(
                RoundedCornerShape(20.dp)
            )
            .background(color = Color(0xffF4F4F4))
    ) {
        Row {
            IconButton(onClick = {
                viewModel.selectDate(state.selectedDate.minusDays(1))
            }, modifier = Modifier) {
                Icon(Icons.Default.KeyboardArrowLeft, "")
            }
            Spacer(Modifier.weight(1f))
            Text(
                "${
                    state.selectedDate.dayOfWeek
                        .getDisplayName(TextStyle.SHORT, Locale("ru"))
                        .replaceFirstChar { it.uppercase() }
                }, ${state.selectedDate.dayOfMonth} ${
                    state.selectedDate.month.getDisplayName(
                        TextStyle.FULL, Locale("ru")
                    )
                }", fontSize = 16.sp, modifier = Modifier.align(
                    Alignment.CenterVertically
                )
            )
            Spacer(Modifier.weight(1f))
            IconButton(onClick = {
                viewModel.selectDate(state.selectedDate.plusDays(1))
            }, modifier = Modifier) {
                Icon(Icons.Default.KeyboardArrowRight, "")
            }
        }
    }
}

