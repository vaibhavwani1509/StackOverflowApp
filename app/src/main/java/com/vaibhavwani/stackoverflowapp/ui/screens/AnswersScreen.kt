package com.vaibhavwani.stackoverflowapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vaibhavwani.stackoverflowapp.model.Answer
import com.vaibhavwani.stackoverflowapp.model.Question
import com.vaibhavwani.stackoverflowapp.viewmodel.AnswersViewModel
import com.vaibhavwani.stackoverflowapp.viewmodel.QuestionsViewModel
import com.vaibhavwani.stackoverflowapp.viewmodel.UiState

@Composable
fun AnswersScreen(
    viewModel: AnswersViewModel,
    id: String
) {
    val uiState = viewModel.uiState.collectAsState()
    viewModel.getAnswers(id)
    when (uiState.value) {
        UiState.Error -> {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "Error!"
                )
            }
        }

        UiState.Loading -> {
            CircularProgressIndicator()
        }

        is UiState.Success<*> -> {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items((uiState.value as UiState.Success<Answer>).data) {
                    Column(
                        modifier = Modifier
                            .padding(10.dp)
                            .wrapContentSize()
                            .background(color = Color.LightGray, shape = RoundedCornerShape(10.dp))
                            .padding(vertical = 12.dp, horizontal = 18.dp),
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = it.answerId ?: "",
                            textAlign = TextAlign.Start
                        )
                    }
                }
            }
        }
    }
}
