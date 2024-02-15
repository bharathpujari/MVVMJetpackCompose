package com.mindiotics.projectschool

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mindiotics.projectschool.ui.theme.ProjectSchoolTheme
import com.mindiotics.projectschool.ui.data.School
import com.mindiotics.projectschool.ui.viewmodel.HighSchoolViewModel

class MainActivity : ComponentActivity() {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProjectSchoolTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel = remember {
                        HighSchoolViewModel()
                    }
                    HighSchoolList(viewModel)
                }
            }
        }
    }
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun HighSchoolList(viewModel: HighSchoolViewModel) {
    viewModel.fetchHighSchools()
    val schoolResult by viewModel.schoolResult.collectAsState()
    when(schoolResult) {
        is UIState.Success -> {
            val highSchools = (schoolResult as UIState.Success<List<School>>).data
            LazyColumn {
                items(highSchools) { highSchool ->
                    HighSchoolItem(highSchool)
                }
            }
        }

        is UIState.Error -> {
            val errorMessage = (schoolResult as UIState.Error).message
            println(errorMessage)
        }

        is UIState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HighSchoolItem(highSchool: School) {
    val showDialog = remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .padding(5.dp)

    ) {
        Row {
            Text(
                text = highSchool.dbn,
                modifier = Modifier
                    .wrapContentWidth()

            )
            Text(
                text = highSchool.school_name,
                fontSize = 25.sp,
                color = Color.Blue,
                modifier = Modifier
                    .padding(16.dp)
                    .wrapContentWidth()
                    .clickable {
                        showDialog.value = true
                    }
            )
        }
    }

    if(showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value =false },
            modifier = Modifier
                .background(Color.White)
                .padding(16.dp)
        ) {
            Column {
                Text(
                    text = highSchool.school_name,
                    fontSize = 30.sp,
                    fontStyle = FontStyle.Italic
                )

                Divider(
                    color = Color.Black,
                    thickness = 2.dp
                )
                Text(
                    text = highSchool.overview_paragraph
                )
            }
        }
    }
}

@Preview
@Composable
fun HighSchoolListPreview() {
    ProjectSchoolTheme {

    }
}

