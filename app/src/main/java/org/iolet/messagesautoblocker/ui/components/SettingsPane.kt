package org.iolet.messagesautoblocker.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.runBlocking
import org.iolet.messagesautoblocker.util.RuleDao


@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun SettingsPane(navHelper: NavigatorHelper, ruleDao: RuleDao) {
    Scaffold(topBar = { TopBar(navHelper) }) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ClearButton(ruleDao)
        }
    }
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(navHelper: NavigatorHelper) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        ),
        title = { Text(text = "Settings", style = MaterialTheme.typography.headlineSmall) },
        navigationIcon = navHelper.backButton
    )
}

@Composable
private fun ClearButton(ruleDao: RuleDao) {
    val openDialog = remember { mutableStateOf(false) }
    ConfirmClearDialog(ruleDao, openDialog)
    TextButton(onClick = { openDialog.value = true }) {
        Text("Clear all rules")
    }
}

@Composable
private fun ConfirmClearDialog(ruleDao: RuleDao, openDialog: MutableState<Boolean>) {
    if (openDialog.value) {
        AlertDialog(
            title = { Text("Confirm deletion of all rules?") },
            text = { Text("This action cannot be undone.") },
            onDismissRequest = { openDialog.value = false },
            confirmButton = {
                TextButton(onClick = {
                    openDialog.value = false
                    runBlocking { ruleDao.deleteAll() }
                }) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(onClick = { openDialog.value = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}