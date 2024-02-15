package com.mindiotics.projectschool

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.mindiotics.projectschool.ui.data.School
import com.mindiotics.projectschool.ui.viewmodel.HighSchoolViewModel
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class MainActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule (MainActivity ::class.java)

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun highSchoolList_Displayed() {
        //We are mocking the school list

        val mockViewModel = mock(HighSchoolViewModel::class.java)
        `when`(mockViewModel.schoolResult.value).thenReturn(
            UIState.Success(
                listOf(
                    School("1", "School 1", "Stree 1"),
                    School("2", "School 2", "Stree 2")
                )
            )
        )
        composeTestRule.setContent {
            HighSchoolList(mockViewModel)
        }

        composeTestRule.onNodeWithText(
            "School 1"
        ).assertExists()

        composeTestRule.onNodeWithText(
            "School 2"
        ).assertExists()
    }

    @Test
    fun clickTestShowAlertDialog() {
        onView(withText("School 1")).perform(click())
    }
}