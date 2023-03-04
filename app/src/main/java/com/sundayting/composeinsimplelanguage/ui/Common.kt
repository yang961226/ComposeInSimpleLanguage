package com.sundayting.composeinsimplelanguage.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val commonChapterModifier = Modifier
    .fillMaxSize()
    .background(color = Color.White)
    .padding(10.dp)

@Composable
@Preview
fun PreviewChapterBackground() {
    ChapterBackground(
        title = "第一章",
        desc = "我们随便说说",
        modifier = commonChapterModifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()

        )
    }
}

/**
 * 每一章的背景
 */
@Composable
fun ChapterBackground(
    title: String,
    desc: String,
    modifier: Modifier = Modifier,
    onClickNext: () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit,
) {

    Box(modifier = modifier) {
        Button(onClick = onClickNext, modifier = Modifier.align(Alignment.TopEnd)) {
            Text("下一章", fontSize = 15.sp)
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = title, fontSize = 30.sp, fontWeight = FontWeight.Bold)
            Text(text = desc, fontSize = 20.sp)
            Spacer(Modifier.height(20.dp))
            Card(
                modifier = Modifier
                    .weight(1f, false)
                    .fillMaxSize(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent
                ),
                border = BorderStroke(1.dp, color = Color.Gray)
            ) {
                content()
            }
        }
    }


}