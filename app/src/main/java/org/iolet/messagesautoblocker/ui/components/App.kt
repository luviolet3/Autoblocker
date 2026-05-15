package org.iolet.messagesautoblocker.ui.components

import androidx.compose.foundation.background
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import org.iolet.messagesautoblocker.RuleDbHelper
import org.iolet.messagesautoblocker.RuleItem

@OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun App() {
    val navigator = rememberListDetailPaneScaffoldNavigator<Int>()
    val scope = rememberCoroutineScope()
    val ruleList = remember { mutableStateListOf<RuleItem>() }

    NavigableListDetailPaneScaffold (
        navigator = navigator,
        listPane = { RulesListPane(navigator, scope, ruleList) },
        detailPane = { RulesSettingsPane(navigator, scope, ruleList) },
        Modifier.background(MaterialTheme.colorScheme.background)
    )
}