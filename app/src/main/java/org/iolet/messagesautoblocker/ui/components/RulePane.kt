package org.iolet.messagesautoblocker.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.painterResource
import kotlinx.coroutines.runBlocking
import org.iolet.messagesautoblocker.R
import org.iolet.messagesautoblocker.util.Rule
import org.iolet.messagesautoblocker.util.RuleDao

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun RulePane(navHelper: NavigatorHelper, ruleDao: RuleDao) {
    navHelper.navigator.currentDestination?.contentKey?.let { index ->
        Scaffold(topBar = { TopBar(navHelper, ruleDao, index) }) {
            val rule = runBlocking { ruleDao[index] }
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ruleInput(ruleDao, rule)
            }
        }

    }
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(navHelper: NavigatorHelper, ruleDao: RuleDao, index: Int) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        ),
        title = { Text(text = "Rule", style = MaterialTheme.typography.headlineSmall) },
        navigationIcon = navHelper.backButton,
        actions = { DeleteButton(navHelper, ruleDao, index) },
    )
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
private fun DeleteButton(navHelper: NavigatorHelper, ruleDao: RuleDao, index: Int) {
    val openDialog = remember { mutableStateOf(false) }
    ConfirmDeleteDialog(navHelper, ruleDao, openDialog, index)
    IconButton(onClick = { openDialog.value = true }) {
        Icon(
            painter = painterResource(R.drawable.delete_24px),
            contentDescription = "Delete",
            tint = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}

@Composable
private fun ConfirmDeleteDialog(navHelper: NavigatorHelper, ruleDao: RuleDao, openDialog: MutableState<Boolean>, index: Int) {
    if (openDialog.value) {
        AlertDialog(
            title = { Text("Confirm deletion of this rule?") },
            text = { Text("This action cannot be undone.") },
            onDismissRequest = { openDialog.value = false },
            confirmButton = {
                TextButton(onClick = {
                    openDialog.value = false
                    runBlocking {
                        ruleDao.delete(index)
                        ruleDao.updateAfterDelete(index)
                    }
                    navHelper.back()
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

@Composable
private fun ruleInput(ruleDao: RuleDao, rule: Rule) {
    val state = rememberTextFieldState(rule.rule)
    TextField(
        state = state,
        label = { Text("Rule") },
        lineLimits = TextFieldLineLimits.SingleLine,
        modifier = Modifier.onFocusChanged { _ ->
            rule.rule = state.text as String
            runBlocking { ruleDao.update(rule) }
        }
    )
}