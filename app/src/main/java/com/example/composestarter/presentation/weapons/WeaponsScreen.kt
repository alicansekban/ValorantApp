@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.caseapp.R
import com.example.composestarter.customViews.TopBarView
import com.example.composestarter.customViews.WeaponsItem
import com.example.composestarter.domain.Error
import com.example.composestarter.domain.Loading
import com.example.composestarter.domain.Success
import com.example.composestarter.domain.model.weapons.WeaponsUIModel
import com.example.composestarter.presentation.weapons.WeaponsViewModel
import com.example.composestarter.utils.ScreenRoutes


@Composable
fun WeaponsScreen(
    openDetail: (String) -> Unit,
    viewModel: WeaponsViewModel = hiltViewModel(),
    scrollState: ScrollState
) {

    val weapons by viewModel.weapons.collectAsStateWithLifecycle()

    when (weapons) {
        is Error -> {
            Text(text = (weapons as Error<List<WeaponsUIModel>>).errorMessage ?: "")
        }

        is Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is Success -> {

            val response =
                (weapons as Success<List<WeaponsUIModel>>).response
            StateLessWeaponsScreen(
                response,
                openDetail,
                scrollState
            )
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StateLessWeaponsScreen(
    weapons: List<WeaponsUIModel>,
    openDetail: (String) -> Unit,
    scrollState: ScrollState
) {


    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState)
        ) {

            TopBarView(title = stringResource(R.string.weapons_title), showBackButton = {
                false
            }, onBackClick = {})

            if (weapons.isNotEmpty()) {


                val groupedWeapons = weapons
                    .groupBy { it.shopData?.category }


                groupedWeapons.forEach { (category, weaponsInCategory) ->

                    WeaponsItem(
                        weapons = weaponsInCategory,
                        categoryTitle = category ?: "Melee",
                        onWeaponClicked = {
                            openDetail(
                                ScreenRoutes.WeaponsDetailRoute.replace(
                                    oldValue = "{id}",
                                    newValue = it
                                )
                            )
                        })
                }
            }

        }

    }

}
