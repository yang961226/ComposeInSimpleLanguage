package com.sundayting.composeinsimplelanguage.five

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sundayting.composeinsimplelanguage.ui.ChapterBackground
import com.sundayting.composeinsimplelanguage.ui.commonChapterModifier
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.random.Random

class FiveActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChapterFiveScreen()
        }
    }

}

@Composable
fun ChapterFiveScreen() {

    ChapterBackground(
        modifier = commonChapterModifier,
        title = "?????????-SideEffect",
        desc = "??????????????????????????????5???SideEffect??????????????????????????????",
    ) {
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EachEffectContainer(
                title = "LaunchedEffect\n?????????????????????????????????????????????",
                color = Color.Cyan.copy(alpha = 0.1f)
            ) {
                var countDown by remember { mutableStateOf(0) }
                var changedFlag by remember { mutableStateOf(false) }
                //???changedFlag???????????????????????????
                LaunchedEffect(key1 = changedFlag) {
                    while (isActive && countDown > 0) {
                        delay(1_000L)
                        countDown--
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Button(onClick = {
                        changedFlag = !changedFlag
                        countDown = 3
                    }) {
                        Text("??????????????????")
                    }

                    Spacer(Modifier.size(20.dp))

                    if (countDown > 0) {
                        Text(remember(countDown) { "??????????????????$countDown" })
                    }
                }

            }
            Divider()
            EachEffectContainer(
                title = "rememberCoroutineScope\n????????????????????????????????????????????????",
                color = Color.Yellow.copy(alpha = 0.1f)
            ) {

                var countDown by remember { mutableStateOf(0) }
                val scope = rememberCoroutineScope()
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Button(onClick = {
                        countDown = 3
                        scope.launch {
                            while (isActive && countDown > 0) {
                                delay(1_000L)
                                countDown--
                            }
                        }
                    }, enabled = countDown == 0) {
                        Text("??????????????????")
                    }
                    Spacer(Modifier.size(20.dp))

                    if (countDown > 0) {
                        Text(remember(countDown) { "??????????????????$countDown" })
                    }
                }

            }
            Divider()
            EachEffectContainer(
                title = "rememberUpdatedState\n???????????????????????????????????????????????????????????????????????????????????????",
                color = Color.Red.copy(alpha = 0.1f)
            ) {
                var show by remember { mutableStateOf(false) }
                var showNum by remember { mutableStateOf(0) }
                Column {
                    Row {
                        Button(onClick = { show = !show }) {
                            Text("????????????")
                        }
                        Spacer(Modifier.size(10.dp))
                        Button(onClick = { showNum++ }) {
                            Text("????????????1?????????:$showNum")
                        }
                    }
                    if (show) {
                        CheckUpdatedStateWidget(num = showNum)
                    }
                }
            }
            Divider()
            EachEffectContainer(
                title = "DisposableEffect\n???????????????????????????????????????????????????????????????????????????????????????",
                color = Color.Green.copy(alpha = 0.1f)
            ) {
                DisposableEffectWidget()
            }
            Divider()
            EachEffectContainer(
                title = "derivedStateOf\n???????????????????????????????????????????????????(????????????mutableStateListOf?????????)",
                color = Color.Magenta.copy(alpha = 0.1f)
            ) {

                val list = remember { mutableStateListOf<String>() }
                val listSizeString by remember {
                    derivedStateOf { "???????????????${list.size}" }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(onClick = { list.add("??????") }) {
                        Text("????????????????????????")
                    }
                    Spacer(modifier = Modifier.size(10.dp))
                    Text(listSizeString, fontSize = 20.sp)
                }


            }
        }
    }

}


@Composable
private fun CheckUpdatedStateWidget(
    num: Int,
) {

    val realNum by rememberUpdatedState(newValue = num)
    var showText by remember { mutableStateOf("") }
    var wrongText by remember { mutableStateOf("") }
    LaunchedEffect(key1 = Unit) {
        delay(3000L)
        showText = "???????????????:$realNum"
        wrongText = "?????????rememberUpdatedState?????????:$num"
    }

    if (showText.isNotEmpty()) {
        Column {
            Text(text = showText)
            Text(text = wrongText)
        }

    } else {
        Text("???????????????????????????????????????????????????????????????1??????")
    }


}

/**
 * ????????????????????????
 */
object Broadcast {

    interface Observer {

        fun observer(value: String)

    }

    init {
        MainScope().launch {
            while (isActive) {
                observerList.forEach {
                    it.observer("????????????${Random.nextInt()}")
                }
                delay(1000L)
            }
        }
    }

    private val observerList = mutableListOf<Observer>()

    fun addObserver(observer: Observer) {
        Log.d("????????????", "???????????????:$observer")
        observerList.add(observer)
    }

    fun removeObserver(observer: Observer) {
        Log.d("????????????", "???????????????:$observer")
        observerList.remove(observer)
    }

}

@Composable
private fun DisposableEffectWidget() {
    var observerContent by remember { mutableStateOf("") }
    var show by remember { mutableStateOf(true) }
    val showText = remember(show) {
        if (show) "????????????" else " ????????????"
    }
    Row {
        Button(onClick = { show = !show }) {
            Text(showText)
        }
        if (show) {
            DisposableEffect(key1 = Unit) {
                val observer = object : Broadcast.Observer {
                    override fun observer(value: String) {
                        observerContent = value
                    }
                }
                Broadcast.addObserver(observer)
                onDispose {
                    Broadcast.removeObserver(observer)
                }
            }
            Text(text = observerContent, modifier = Modifier.padding(10.dp), fontSize = 20.sp)
        }
    }


}

@Composable
private fun EachEffectContainer(
    title: String,
    color: Color,
    content: @Composable () -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color)
            .padding(start = 20.dp, end = 20.dp, bottom = 20.dp, top = 10.dp)
    ) {
        Text(text = title, fontSize = 20.sp)
        content()
    }

}