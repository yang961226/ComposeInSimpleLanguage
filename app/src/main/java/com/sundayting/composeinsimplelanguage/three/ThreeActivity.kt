package com.sundayting.composeinsimplelanguage.three

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sundayting.composeinsimplelanguage.four.FourActivity
import com.sundayting.composeinsimplelanguage.ui.ChapterBackground
import com.sundayting.composeinsimplelanguage.ui.commonChapterModifier
import kotlin.random.Random

class ThreeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChapterThreeScreen(onClickNext = {
                startActivity(Intent(this, FourActivity::class.java))
            })
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChapterThreeScreen(onClickNext: () -> Unit = {}) {
    ChapterBackground(
        modifier = commonChapterModifier,
        title = "第三节-MutableState",
        desc = "本案例展现了mutableStateOf和mutableStateListOf的使用方式",
        onClickNext = onClickNext
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f, false)
                    .background(color = Color.Blue.copy(alpha = 0.05f))
                    .padding(horizontal = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    var name by remember { mutableStateOf("") }
                    Text(text = "可以观察到，当输入框改变name的时候，所有引用了name的可组合函数都发生了重组（其中包括Text和OutlinedTextField）")
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
            Divider()
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f, false)
                    .background(color = Color.Green.copy(alpha = 0.05f))
            ) {
                val list = remember { mutableStateListOf<ListItem>() }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                ) {
                    Text("当mutableStateListOf数组发生改变时（添加或者减少元素），LazyColumn就会发生重组")
                    Row {
                        Button(onClick = {
                            list.add(
                                ListItem(
                                    content = Random.nextInt().toString(),
                                    time = System.currentTimeMillis()
                                )
                            )
                        }) {
                            Text("添加一个元素")
                        }
                        Spacer(modifier = Modifier.size(10.dp))
                        Button(onClick = {
                            list.removeLastOrNull()
                        }) {
                            Text("减少一个元素")
                        }
                    }

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f, false),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(list, key = { it.time }) { item ->
                            Column {
                                Card(
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color.Red.copy(alpha = 0.1f)
                                    )
                                ) {
                                    Text(
                                        text = item.content,
                                        modifier = Modifier
                                            .padding(10.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.size(10.dp))
                            }

                        }
                    }
                }
            }
        }
    }


}

data class ListItem(
    val content: String,
    val time: Long,
)