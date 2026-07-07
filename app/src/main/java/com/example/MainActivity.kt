package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.example.ui.screens.MainPortalScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.CivicViewModel
import com.example.ui.viewmodel.CivicViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        // Initialize Locator, Repository and ViewModel
        val repository = CivicLocator.getRepository(applicationContext)
        val viewModel = ViewModelProvider(
            this,
            CivicViewModelFactory(repository)
        )[CivicViewModel::class.java]

        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainPortalScreen(viewModel = viewModel)
                }
            }
        }
    }
}

