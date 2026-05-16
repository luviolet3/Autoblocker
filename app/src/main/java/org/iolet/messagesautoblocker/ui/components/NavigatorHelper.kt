package org.iolet.messagesautoblocker.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.ThreePaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.window.core.layout.WindowSizeClass
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.iolet.messagesautoblocker.R

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
class NavigatorHelper(val navigator: ThreePaneScaffoldNavigator<Int>, val scope: CoroutineScope) {

    @OptIn(ExperimentalMaterial3AdaptiveApi::class)
    val settingsButton : @Composable RowScope.() -> Unit = {
        IconButton(onClick = { settings() }) {
            Icon(
                painter = painterResource(R.drawable.settings_24px),
                contentDescription = "Settings",
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }

    @OptIn(ExperimentalMaterial3AdaptiveApi::class)
    val backButton : @Composable () -> Unit = {
        if (!currentWindowAdaptiveInfo().windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND)) {
            IconButton(onClick = { back() }) {
                Icon(
                    painter = painterResource(R.drawable.arrow_back_24px),
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }

    @OptIn(ExperimentalMaterial3AdaptiveApi::class)
    fun rule(index: Int) {
        scope.launch {
            navigator.navigateTo(ListDetailPaneScaffoldRole.Detail, index)
        }
    }

    @OptIn(ExperimentalMaterial3AdaptiveApi::class)
    fun settings() {
        scope.launch {
            navigator.navigateTo(ListDetailPaneScaffoldRole.Extra)
        }
    }

    @OptIn(ExperimentalMaterial3AdaptiveApi::class)
    fun back() {
        scope.launch {
            navigator.navigateBack()
        }
    }
}