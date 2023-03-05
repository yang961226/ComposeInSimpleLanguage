package com.sundayting.composeinsimplelanguage.three

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sundayting.composeinsimplelanguage.ui.ChapterBackground
import com.sundayting.composeinsimplelanguage.ui.commonChapterModifier

class ThreeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChapterThreeScreen()
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChapterThreeScreen(onClickNext: () -> Unit = {}) {
    ChapterBackground(
        modifier = commonChapterModifier,
        title = "第三节-MutableState",
        desc = "本案例是一种经典的MutableState使用方式（配合remember)\n可以观察到，当输入框改变name的时候，所有引用了name的可组合函数都发生了重组（其中包括Text和OutlinedTextField）",
        onClickNext = onClickNext
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Blue.copy(alpha = 0.05f)),
            contentAlignment = Alignment.Center
        ) {
            Column {
                var name by remember { mutableStateOf("") }
                if (name.isNotEmpty()) {
                    Text(
                        text = "Hello, $name!",
                        modifier = Modifier.padding(bottom = 8.dp),
                    )
                }
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") }
                )
            }
        }
    }


}