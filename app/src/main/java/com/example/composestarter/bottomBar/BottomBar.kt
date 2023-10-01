package com.example.composestarter.bottomBar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.More
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.More
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.caseapp.R
import com.example.composestarter.utils.BottomNavigationItem
import com.example.composestarter.utils.ScreenRoutes
import com.example.composestarter.utils.heightPercent

@Composable
fun BottomBar(
    navController: NavController,
    scrollState: ScrollState,
    lazyGridState: LazyGridState,
    scrollingUp: Boolean,
) {

    val navigation: (String) -> Unit = { route ->
        if (route == "-1") {
            navController.popBackStack()
        } else {
            navController.navigate(route)
        }
    }
    val items = listOf(
        BottomNavigationItem(
            title = "Agents",
            selectedIcon = Icons.Filled.Home,
            unSelectedIcon = Icons.Outlined.Home,
            route = ScreenRoutes.AgentsRouteNavHost

        ),
        BottomNavigationItem(
            title = "Maps",
            selectedIcon = ImageVector.vectorResource(id = R.drawable.ic_maps_selected),
            unSelectedIcon = ImageVector.vectorResource(id = R.drawable.ic_maps_unselected),
            route = ScreenRoutes.MapsRouteNavHost
        ),
        BottomNavigationItem(
            title = "Weapons",
            selectedIcon = ImageVector.vectorResource(id = R.drawable.ic_weapons_selected),
            unSelectedIcon = ImageVector.vectorResource(id = R.drawable.ic_weapons_unselected),
            route = ScreenRoutes.WeaponsRouteNavHost
        ),
        BottomNavigationItem(
            title = "Favorites",
            selectedIcon = Icons.Filled.Favorite,
            unSelectedIcon = Icons.Outlined.FavoriteBorder,
            route = ScreenRoutes.FavoritesRouteNavHost
        ),
        BottomNavigationItem(
            title = "More",
            selectedIcon = Icons.Filled.More,
            unSelectedIcon = Icons.Outlined.More,
            route = ScreenRoutes.MoreRouteNavHost
        ),
    )
    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    val isScrolling by remember(scrollingUp, lazyGridState, scrollState) {
        derivedStateOf { scrollingUp || lazyGridState.isScrollInProgress || scrollState.isScrollInProgress }
    }

    AnimatedVisibility(
        visible = isScrolling,
    ) {
        NavigationBar(
            modifier = Modifier.heightPercent(
                0.110f,
                LocalConfiguration.current
            )
        ) {
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    selected = selectedItemIndex == index,
                    onClick = {
                        selectedItemIndex = index
                        navigation(item.route)
                    },
                    label = {
                        Text(
                            text = item.title
                        )
                    },
                    icon = {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            imageVector = if (index == selectedItemIndex) item.selectedIcon else item.unSelectedIcon,
                            contentDescription = item.title
                        )
                    })
            }
        }
    }

}