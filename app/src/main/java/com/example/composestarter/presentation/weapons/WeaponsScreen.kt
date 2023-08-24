import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.composestarter.presentation.weapons.WeaponsViewModel


@Composable
fun WeaponsScreen(
    openDetail : (String) -> Unit,
    viewModel: WeaponsViewModel = hiltViewModel()
) {

    val weapons by viewModel.weapons.collectAsStateWithLifecycle()
    Box(
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Skins Screen")
    }
}