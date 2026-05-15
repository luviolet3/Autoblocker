package org.iolet.messagesautoblocker.ui.components

import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.ThreePaneScaffoldNavigator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
fun navigateRule(navigator: ThreePaneScaffoldNavigator<Int>, scope: CoroutineScope, index: Int) {
    scope.launch {
        navigator.navigateTo(ListDetailPaneScaffoldRole.Detail, index)
    }
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
fun navigateSettings(navigator: ThreePaneScaffoldNavigator<Int>, scope: CoroutineScope) {
    scope.launch {
        navigator.navigateTo(ListDetailPaneScaffoldRole.Extra)
    }
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
fun navigateBack(navigator: ThreePaneScaffoldNavigator<Int>, scope: CoroutineScope) {
    scope.launch {
        navigator.navigateBack()
    }
}