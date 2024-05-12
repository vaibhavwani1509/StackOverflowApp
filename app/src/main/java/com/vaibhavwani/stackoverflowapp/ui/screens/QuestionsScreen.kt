package com.vaibhavwani.stackoverflowapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vaibhavwani.stackoverflowapp.model.Question
import com.vaibhavwani.stackoverflowapp.viewmodel.QuestionsViewModel
import com.vaibhavwani.stackoverflowapp.viewmodel.UiState.Error
import com.vaibhavwani.stackoverflowapp.viewmodel.UiState.Loading
import com.vaibhavwani.stackoverflowapp.viewmodel.UiState.Success
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun QuestionsScreen(
    viewModel: QuestionsViewModel,
    getAnswers: (String) -> Unit
) {
    val uiState = viewModel.uiState.collectAsState()
    val refreshScope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }

    fun refresh() = refreshScope.launch {
        refreshing = true
        delay(1500)
        viewModel.getQuestions()
        refreshing = false
    }

    val refreshState = rememberPullRefreshState(refreshing, ::refresh)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(refreshState)
    ) {
        when (uiState.value) {
            Error -> {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "Error!"
                )
            }

            Loading -> {
                CircularProgressIndicator(modifier = Modifier
                    .size(30.dp)
                    .align(Alignment.Center)
                )
            }

            is Success<*> -> {

                val state = rememberLazyListState()
                LaunchedEffect(key1 = state) {
                    // You can access the last visible item index from listState
                    if (state.isScrolledToTheEnd()) {
                        viewModel.loadMore()
                    }
                    // Do something with the last visible item index
                    // For example, you can update some state or perform some action
                }
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items((uiState.value as? Success<Question>)?.data ?: emptyList()) {
                        Column(
                            modifier = Modifier
                                .padding(10.dp)
                                .wrapContentSize()
                                .background(
                                    color = Color.LightGray,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .padding(vertical = 12.dp, horizontal = 18.dp)
                                .clickable {
                                    getAnswers(it.id ?: "")
                                },
                        ) {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = it.title ?: "",
                                textAlign = TextAlign.Start
                            )
                            Divider(
                                modifier = Modifier
                                    .padding(vertical = 12.dp)
                                    .fillMaxWidth()
                                    .height(1.dp),
                                color = Color.Black.copy(alpha = 0.5f)
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                Text(
                                    modifier = Modifier.weight(1f),
                                    text = it.score.toString() ?: "",
                                )
                                Text(
                                    modifier = Modifier.wrapContentWidth(),
                                    text = it.date ?: "",
                                )
                            }
                        }
                    }
                    item {
                        if (state.isScrolledToTheEnd()) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .size(50.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
        PullRefreshIndicator(refreshing, refreshState, Modifier.align(Alignment.TopCenter))
    }
}

fun LazyListState.isScrolledToTheEnd() =
    layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1
