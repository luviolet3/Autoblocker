package org.iolet.messagesautoblocker.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigation.ThreePaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.window.core.layout.WindowSizeClass
import kotlinx.coroutines.CoroutineScope
import org.iolet.messagesautoblocker.R
import org.iolet.messagesautoblocker.RuleItem

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun RulesSettingsPane(navigator: ThreePaneScaffoldNavigator<Int>, scope: CoroutineScope, ruleList : MutableList<RuleItem>) {
    Scaffold(topBar = { RulesSettingsTopBar(navigator, scope) }) {
        navigator.currentDestination?.contentKey?.let { it1 ->
            Text(
                text = "TODO: App detailPane for ${ruleList[it1].text}",
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            )
        }
    }
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun RulesSettingsTopBar(navigator: ThreePaneScaffoldNavigator<Int>, scope: CoroutineScope) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        ),
        title = { Text(text = "Rule", style = MaterialTheme.typography.headlineSmall) },
        navigationIcon = {
            if (!currentWindowAdaptiveInfo().windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_MEDIUM_LOWER_BOUND)) {
                IconButton(onClick = { navigateBack(navigator, scope) }) {
                    Icon(
                        painter = painterResource(R.drawable.arrow_back_24px),
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }
    )
}

