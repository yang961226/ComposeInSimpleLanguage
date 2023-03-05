package com.sundayting.composeinsimplelanguage.four

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sundayting.composeinsimplelanguage.ui.ChapterBackground
import com.sundayting.composeinsimplelanguage.ui.commonChapterModifier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FourViewModel : ViewModel() {

    data class UiState(
        val num: Int = 1,
    )

    private val _uiStateFlow = MutableStateFlow(UiState())
    val uiStateFlow = _uiStateFlow.asStateFlow()

    fun updateNum(num: Int) {
        if (num < 20) {
            _uiStateFlow.update { it.copy(num = num * 2) }
        }
    }

}

class FourActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChapterFourScreen(
                onClickNext = {}
            )
        }
    }

}

@Composable
fun ChapterFourScreen(
    viewModel: FourViewModel = viewModel(),
    onClickNext: () -> Unit = {},
) {

    val uiState by viewModel.uiStateFlow.collectAsState()

    ChapterBackground(
        modifier = commonChapterModifier,
        title = "第四节-状态提升",
        desc = "本案例提供了3种不同的可组合函数，分别是带状态的可组合函数，还有2种是状态提升的可组合函数，你可以通过对比来感知状态提升的作用性",
        onClickNext = onClickNext
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Yellow.copy(alpha = 0.1f))
                .padding(horizontal = 20.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                var num by remember { mutableStateOf(1) }
                val over10 = remember(num) { num > 10 }

                Text(
                    text = "自带状态的可组合函数，状态在其内部维护，外部无法获取其状态和拦截其变化，难以复用和测试",
                    fontSize = 20.sp
                )
                ComposableWithState()

                Spacer(Modifier.size(30.dp))

                Text(
                    text = "状态提升的可组合函数，状态提升到他的父可组合函数中,父可组合函数可以获知其状态和拦截其变化",
                    fontSize = 20.sp
                )
                Row {
                    ComposableWithNoState(num = num, onValueChange = { currentValue ->
                        num += currentValue
                    })
                    if (over10) {
                        Text("大于10了")
                    }
                }

                Spacer(Modifier.size(30.dp))

                Text(
                    text = "状态提升的可组合函数，状态提升到viewModel中,viewModel可以获知其状态和拦截其变化",
                    fontSize = 20.sp
                )
                ComposableWithNoState(num = uiState.num, onValueChange = { currentValue ->
                    viewModel.updateNum(currentValue)
                })
            }
        }
    }

}

/**
 * 自带状态的组件
 */
@Composable
private fun ComposableWithState() {

    var num by remember { mutableStateOf(1) }
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(15.dp))
            .clickable { num++ }
            .background(color = Color.Blue.copy(alpha = 0.5f))
            .padding(horizontal = 10.dp, vertical = 5.dp)

    ) {
        Text("我是数字：$num", color = Color.White, fontSize = 20.sp)
    }

}

/**
 * 不带状态的组件
 */
@Composable
private fun ComposableWithNoState(num: Int, onValueChange: (Int) -> Unit = {}) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(15.dp))
            .clickable { onValueChange(num) }
            .background(color = Color.Blue.copy(alpha = 0.5f))
            .padding(horizontal = 10.dp, vertical = 5.dp)

    ) {
        Text("我是数字：$num", color = Color.White, fontSize = 20.sp)
    }
}