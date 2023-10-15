package com.example.composestarter.bottomBar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dangerous
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.More
import androidx.compose.material.icons.outlined.Dangerous
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.More
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.composestarter.utils.ScreenRoutes

internal enum class TabItem(
    val title: String,
    val route: String,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
) {

    Agents(
        title = "Agents",
        selectedIcon = Icons.Filled.Home,
        unSelectedIcon = Icons.Outlined.Home,
        route = ScreenRoutes.AgentsRouteNavHost
    ),
    Maps(
        title = "Maps",
        selectedIcon = Icons.Filled.Map,
        unSelectedIcon = Icons.Outlined.Map,
        route = ScreenRoutes.MapsRouteNavHost
    ),
    Weapons(
        title = "Weapons",
        selectedIcon = Icons.Filled.Dangerous,
        unSelectedIcon = Icons.Outlined.Dangerous,
        route = ScreenRoutes.WeaponsRouteNavHost
    ),
    Favorites(
        title = "Favorites",
        selectedIcon = Icons.Filled.Favorite,
        unSelectedIcon = Icons.Outlined.FavoriteBorder,
        route = ScreenRoutes.FavoritesRouteNavHost
    ),
    More(
        title = "More",
        selectedIcon = Icons.Filled.More,
        unSelectedIcon = Icons.Outlined.More,
        route = ScreenRoutes.MoreRouteNavHost
    ),
}

private val bottomBarItems =
    listOf(TabItem.Agents, TabItem.Maps, TabItem.Weapons, TabItem.Favorites, TabItem.More)

@Composable
internal fun AppNavigationBar(
    navController: NavHostController,
    scrollState: ScrollState,
    lazyGridState: LazyGridState,
    lazyListState: LazyListState
) {
    val currentTopLevelDestination by navController.currentTabItemAsState()

    val isScrolling by remember(lazyListState, lazyGridState, scrollState) {
        derivedStateOf { lazyListState.isScrollInProgress || lazyGridState.isScrollInProgress || scrollState.isScrollInProgress }
    }

    AnimatedVisibility(visible = !isScrolling) {
        NavigationBar {
            bottomBarItems.forEachIndexed { index, item ->
                val isTabSelected = item == currentTopLevelDestination
                NavigationBarItem(
                    icon = {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            imageVector = if (isTabSelected) item.selectedIcon else item.unSelectedIcon,
                            contentDescription = item.title
                        )
                    },
                    label = { Text(item.title) },
                    selected = isTabSelected,
                    onClick = {
                        navController.navigateToTabItem(
                            item = item,
                            restoreTabStack = !isTabSelected,
                        )
                    }
                )
            }
        }
    }
}

/**
 * Adds an [NavController.OnDestinationChangedListener] to this [NavController] and updates the
 * returned [State] which is updated as the destination changes.
 */
@Composable
private fun NavController.currentTabItemAsState(): State<TabItem> {
    val selectedItem = rememberSaveable { mutableStateOf(TabItem.Agents) }

    DisposableEffect(this) {
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            when {
                destination.hierarchy.any { it.route == TabItem.More.route } -> {
                    selectedItem.value = TabItem.More
                }

                destination.hierarchy.any { it.route == TabItem.Maps.route } -> {
                    if (selectedItem.value != TabItem.Maps) selectedItem.value = TabItem.Maps
                }

                destination.hierarchy.any { it.route == TabItem.Weapons.route } -> {
                    selectedItem.value = TabItem.Weapons
                }

                destination.hierarchy.any { it.route == TabItem.Favorites.route } -> {
                    selectedItem.value = TabItem.Favorites
                }

                // TopLevelDestination.HOME is the start destination and, therefore, part of any stack
                else -> {
                    selectedItem.value = TabItem.Agents
                }
            }
        }
        addOnDestinationChangedListener(listener)

        onDispose {
            removeOnDestinationChangedListener(listener)
        }
    }

    return selectedItem
}

private fun NavHostController.navigateToTabItem(
    item: TabItem,
    restoreTabStack: Boolean,
) {
    navigate(item.route) {
        // Pop up to the start destination of the graph to
        // avoid building up a large stack of destinations
        // on the back stack as users select items
        popUpTo(graph.findStartDestination().id) {
            saveState = restoreTabStack
        }
        // Avoid multiple copies of the same destination when
        // reselecting the same item
        launchSingleTop = true
        // Restore state when reselecting a previously selected item
        restoreState = restoreTabStack
    }
}
