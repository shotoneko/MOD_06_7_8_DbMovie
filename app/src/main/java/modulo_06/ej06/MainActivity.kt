package modulo_06.ej06

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import modulo_06.ej06.repository.Repository
import modulo_06.ej06.repository.RepositoryImp
import modulo_06.ej06.ui.theme.DbMovieTheme
import modulo_06.ej06.views.HomeView
import modulo_06.ej06.views.HomeViewVM
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var repository: Repository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: HomeViewVM by viewModels()
        setContent {
            DbMovieTheme {
                HomeView(viewModel)
            }
        }
    }
}


@Composable
fun MainScreen (repo: Repository) {
    LaunchedEffect(Unit) {
       // val response = repo.getAllMoviesApi()
       val response = repo.getNewMovie()
        val response2 = repo.getNewMovie()

    }

    Scaffold {it->
        Text(
            text = "Hello, World!",
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        )
    }
}