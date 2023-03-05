package com.sundayting.composeinsimplelanguage.two

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.sundayting.composeinsimplelanguage.ui.ChapterBackground
import com.sundayting.composeinsimplelanguage.ui.commonChapterModifier

class TwoActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChapterTwoScreen(onClickNext = {

            })
        }
    }

}

@Composable
fun ChapterTwoScreen(onClickNext: () -> Unit = {}) {
    ChapterBackground(
        modifier = commonChapterModifier,
        title = "第二节-remember",
        desc = "本案例可以让你体验remember与MutableState的简单使用。\n观察Tag为「临时测试」的Log，只有num发生变化的时候，numString才会重新生成，避免每次重组都要生成一次",
        onClickNext = onClickNext
    ) {

        SideEffect { Log.d("临时测试", "外部进入重组") }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Red.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            var num by remember { mutableStateOf(0) }
            val numString = remember(num) {
                Log.d("临时测试", "我要重新生成值了")
                "我是数字$num"
            }
            Button(onClick = { num++ }) {
                SideEffect { Log.d("临时测试", "Button进入重组") }
                Text(text = numString)
            }
        }
    }
}
