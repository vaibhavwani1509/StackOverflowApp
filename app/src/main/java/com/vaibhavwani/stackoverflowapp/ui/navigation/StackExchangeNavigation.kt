package com.vaibhavwani.stackoverflowapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavArgument
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.vaibhavwani.stackoverflowapp.ui.screens.AnswersScreen
import com.vaibhavwani.stackoverflowapp.ui.screens.QuestionsScreen
import com.vaibhavwani.stackoverflowapp.viewmodel.AnswersViewModel
import com.vaibhavwani.stackoverflowapp.viewmodel.QuestionsViewModel

@Composable
fun StackExchangeNavigation(
    viewModel: QuestionsViewModel
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "questions") {
        composable(route = "questions") {
            QuestionsScreen(viewModel = viewModel) { id ->
                navController.navigate("answers/${id}")
            }
        }

        composable(route = "answers/{id}", arguments = listOf(
            navArgument(
                name = "id"
            ){
                type = NavType.StringType
            }
        )) {
            val answerViewModel: AnswersViewModel = viewModel()
            AnswersScreen(viewModel = answerViewModel, id = it.arguments?.getString("id").orEmpty())
        }
    }
}