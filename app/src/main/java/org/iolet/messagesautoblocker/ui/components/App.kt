package org.iolet.messagesautoblocker.ui.components

import androidx.compose.foundation.background
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import org.iolet.messagesautoblocker.util.RuleDao

@OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun App(ruleDao: RuleDao) {
    val navigator = rememberListDetailPaneScaffoldNavigator<Int>()
    val scope = rememberCoroutineScope()
    val navHelper = NavigatorHelper(navigator, scope)

    NavigableListDetailPaneScaffold (
        navigator = navigator,
        listPane = { RulesListPane(navHelper, ruleDao) },
        detailPane = { RulePane(navHelper, ruleDao) },
        extraPane = { SettingsPane(navHelper, ruleDao) },
        modifier = Modifier.background(MaterialTheme.colorScheme.background)
    )
}