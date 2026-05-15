package org.iolet.messagesautoblocker.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation.ThreePaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import kotlinx.coroutines.CoroutineScope
import org.iolet.messagesautoblocker.R
import org.iolet.messagesautoblocker.RuleItem

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun RulesListPane(navigator: ThreePaneScaffoldNavigator<Int>, scope: CoroutineScope, ruleList : MutableList<RuleItem>) {
    Scaffold(
        topBar = { RulesListTopBar(navigator, scope) },
        floatingActionButton = { RulesListFAB(navigator, scope, ruleList) }
    ) {
        LazyColumn(modifier = Modifier
            .padding(it)
            .fillMaxSize()
        ) {
            itemsIndexed(ruleList) { index, item -> RulesListItem(navigator, scope, index, item) }
        }
    }
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
private fun RulesListItem(navigator: ThreePaneScaffoldNavigator<Int>, scope: CoroutineScope, index: Int, item: RuleItem) {
    Column (modifier = Modifier
        .fillMaxWidth()
    ) {
        HorizontalDivider(color = MaterialTheme.colorScheme.outline)
        ListItem(
            leadingContent = { Text("${index+1}") },
            headlineContent = { Text(item.text, style = MaterialTheme.typography.bodyLarge) },
            colors = ListItemDefaults.colors(
                containerColor = MaterialTheme.colorScheme.background,
                headlineColor = MaterialTheme.colorScheme.onBackground,
                leadingIconColor = MaterialTheme.colorScheme.onBackground
            ),
            modifier = Modifier.clickable(onClick = { navigateRule(navigator, scope, index) })
        )
        HorizontalDivider(color = MaterialTheme.colorScheme.outline)
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3AdaptiveApi::class)
@Composable
private fun RulesListTopBar(navigator: ThreePaneScaffoldNavigator<Int>, scope: CoroutineScope) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        ),
        title = { Text(text = "Rules", style = MaterialTheme.typography.headlineSmall) },
        actions = {
            IconButton(onClick = { navigateSettings(navigator, scope) }) {
                Icon(
                    painter = painterResource(R.drawable.settings_24px),
                    contentDescription = "Settings",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
private fun RulesListFAB(navigator: ThreePaneScaffoldNavigator<Int>, scope: CoroutineScope, ruleList : MutableList<RuleItem>) {
    FloatingActionButton(
        onClick = {
            val rule = RuleItem("")
            ruleList.add(rule)
        },
    ) {
        Icon(
            painter = painterResource(R.drawable.add_24px),
            contentDescription = "New",
            tint = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}