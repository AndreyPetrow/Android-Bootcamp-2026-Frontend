package ru.sicampus.bootcamp2026.incomingbooks

import android.icu.util.TimeZone
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.sicampus.bootcamp2026.R
import ru.sicampus.bootcamp2026.schedule.TestBookData
import ru.sicampus.bootcamp2026.ui.theme.Typography
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.Calendar
import java.util.Date
import java.util.SimpleTimeZone

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun IncomingScreen(modifier: Modifier = Modifier) {
    val listIncoming = remember { mutableStateOf<MutableList<TestBookData>>(
        mutableListOf<TestBookData>(TestBookData(
            title = "Ревью дизайна",
            address = "312 каб",
            date = ZonedDateTime.of(LocalDateTime.now().plusDays(2), ZoneId.systemDefault()).toInstant().toEpochMilli(),
            timeStart = "13:00",
            timeEnd = "14:00",
            organizerName = "Елена босс"
        ))
    ) }
    Scaffold(
        topBar = {
            Box(Modifier
                .fillMaxWidth()
                .height(60.dp).background(Color.White)) {
                Text("Входящие", modifier = Modifier.padding(top = 20.dp, start = 20.dp),
                    fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
            }
        },
        modifier = Modifier,
        containerColor = Color(0xffEEEEEE)
    ) { it1 ->
        LazyColumn(Modifier.padding(top = (it1.calculateTopPadding().value + 20).dp).fillMaxWidth()){
            items(listIncoming.value){
                Box(modifier = Modifier.fillMaxWidth()) {
                    Box(
                        modifier = Modifier.fillMaxWidth(0.9f).height(150.dp).clip(
                            RoundedCornerShape(20.dp)
                        ).background(Color.White).align(Alignment.Center)
                    ) {
                        Column() {
                            Row(
                                Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(Modifier.padding(start = 18.dp, top = 10.dp)) {
                                    Text(
                                        it.title, fontWeight = FontWeight.SemiBold,
                                        fontSize = 20.sp
                                    )
                                    Text(
                                        "От ${it.organizerName}",
                                        color = Color(0xff636363),
                                        fontSize = 14.sp, fontWeight = FontWeight.SemiBold
                                    )
                                    Row(Modifier.padding(top = 10.dp)) {
                                        Icon(
                                            painterResource(R.drawable.clock),
                                            "",
                                            tint = Color(0xff1480F0),
                                            modifier = Modifier.size(20.dp)
                                        )
                                        Spacer(Modifier.size(3.dp))
                                        Text("${it.timeStart}-${it.timeEnd}",
                                            modifier = Modifier.align(Alignment.CenterVertically))
                                        Spacer(Modifier.size(11.dp))
                                        Icon(
                                            painterResource(R.drawable.location),
                                            "",
                                            tint = Color(0xff1480F0),
                                            modifier = Modifier.size(21.dp)
                                        )
                                        Spacer(Modifier.size(2.dp))
                                        Text(it.address,
                                            modifier = Modifier.align(Alignment.CenterVertically)
                                        )
                                    }

                                }
                                it.date?.let{it2 ->
                                    Text(  getDisplayDateIncome(it.date), fontSize = 16.sp,
                                        color = Color(0xff636363).copy(0.8f), fontWeight = FontWeight.SemiBold,
                                        modifier = Modifier.padding(end = 20.dp, top = 15.dp))}

                            }
                            Row(Modifier.fillMaxWidth().padding(start = 10.dp, top = 20.dp, end = 10.dp)) {
                                Button(onClick = {},
                                    modifier = Modifier.height(35.dp).weight(1f),
                                    shape = RoundedCornerShape(16.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xff155DFC))) {
                                        Text("Принять")
                                }
                                Spacer(Modifier.size(10.dp))
                                Button(onClick = {},
                                    shape = RoundedCornerShape(16.dp),
                                    modifier = Modifier.height(35.dp).weight(1f),
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xffE6E6E6))) {
                                        Text("Отклонить", color = Color.Black, fontWeight = FontWeight.SemiBold)
                                }
                            }

                        }
                    }
                }
            }
        }
    }
}
@RequiresApi(Build.VERSION_CODES.O)
fun getDisplayDateIncome(l: Long): String{
    if(Instant.ofEpochMilli(l).atZone(ZoneId.systemDefault()).toLocalDate() ==
        LocalDate.now()
    ){
        return "Сегодня"
    }else if (
        dateToLong(Instant.ofEpochMilli(l).atZone(ZoneId.systemDefault()).toLocalDate())-
        dateToLong(LocalDate.now()) == 1.toLong()
    ){
        return "Завтра"
    }
    return "${Instant.ofEpochMilli(l).atZone(ZoneId.systemDefault()).toLocalDate().month} ${Instant.ofEpochMilli(l).atZone(ZoneId.systemDefault()).toLocalDate().dayOfMonth}"
}

@RequiresApi(Build.VERSION_CODES.O)
fun dateToLong(date: LocalDate): Long{
    return "${date.year}${date.monthValue}${date.dayOfMonth}".toLong()
}
@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun DDAWD() {
    IncomingScreen()

}