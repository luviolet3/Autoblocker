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
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import kotlinx.coroutines.runBlocking
import org.iolet.messagesautoblocker.R
import org.iolet.messagesautoblocker.util.Rule
import org.iolet.messagesautoblocker.util.RuleDao

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun RulesListPane(navHelper: NavigatorHelper, ruleDao: RuleDao) {
    val ruleList = ruleDao.getAll().collectAsState(initial = emptyList())
    Scaffold(
        topBar = { TopBar(navHelper) },
        floatingActionButton = { NewRuleFAB(navHelper, ruleDao) }
    ) {
        LazyColumn(modifier = Modifier
            .padding(it)
            .fillMaxSize()
        ) {
            itemsIndexed(ruleList.value) { index, item -> RuleCard(navHelper, index, item) }
        }
    }
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
private fun RuleCard(navHelper: NavigatorHelper, index: Int, item: Rule) {
    Column (modifier = Modifier
        .fillMaxWidth()
    ) {
        HorizontalDivider(color = MaterialTheme.colorScheme.outline)
        ListItem(
            leadingContent = { Text("${item.id+1}") },
            headlineContent = { Text(item.rule, style = MaterialTheme.typography.bodyLarge) },
            colors = ListItemDefaults.colors(
                containerColor = MaterialTheme.colorScheme.background,
                headlineColor = MaterialTheme.colorScheme.onBackground,
                leadingIconColor = MaterialTheme.colorScheme.onBackground
            ),
            modifier = Modifier.clickable(onClick = { navHelper.rule(index) })
        )
        HorizontalDivider(color = MaterialTheme.colorScheme.outline)
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3AdaptiveApi::class)
@Composable
private fun TopBar(navHelper: NavigatorHelper) {
    return CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        ),
        title = { Text(text = "Rules", style = MaterialTheme.typography.headlineSmall) },
        actions = navHelper.settingsButton
    )
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
private fun NewRuleFAB(navHelper: NavigatorHelper, ruleDao: RuleDao) {
    FloatingActionButton(
        onClick = {
            val index = runBlocking { ruleDao.size() }
            val rule = Rule(
                id = index,
                rule = ""
            )
            runBlocking { ruleDao.add(rule) }
            navHelper.rule(index)
        },
    ) {
        Icon(
            painter = painterResource(R.drawable.add_24px),
            contentDescription = "New",
            tint = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}