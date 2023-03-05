package com.sundayting.composeinsimplelanguage.one

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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

class OneActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChapterOneScreen(onClickNext = {

            })
        }
    }

}

@Composable
fun ChapterOneScreen(onClickNext: () -> Unit = {}) {
    ChapterBackground(
        modifier = commonChapterModifier,
        title = "第一节-智能重组",
        desc = "本案例可以让你清楚得体验到智能重组的高效性(请查看Tag为「临时测试」的Log)",
        onClickNext = onClickNext
    ) {
        var blockOneNum by remember { mutableStateOf(0) }
        var blockTwoNum by remember { mutableStateOf(0) }
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            ComposeBlock(
                num = blockOneNum,
                logTag = "方块1",
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f, false)
                    .background(color = Color.Gray.copy(alpha = 0.2f)),
                onClick = {
                    blockOneNum++
                }
            )
            ComposeBlock(
                num = blockTwoNum,
                logTag = "方块2",
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f, false)
                    .background(color = Color.Green.copy(alpha = 0.2f)),
                onClick = {
                    blockTwoNum++
                }
            )
        }
    }

}

/**
 * 体验智能重组的可重组函数
 * @param num 显示的数字
 * @param logTag 日志标签，用于查看Log.d
 */
@Composable
private fun ComposeBlock(
    num: Int,
    logTag: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {

    Box(
        modifier = modifier.clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        //此代码的作用在第一章中不需要理解，你只需要知道，当本可重组函数进入重组的时候，SideEffect内部的lambda函数会执行就可以了
        SideEffect {
            Log.d("临时测试", "${logTag}发生了重组")
        }
        Text("$num")
    }
}
