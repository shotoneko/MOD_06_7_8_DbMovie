package modulo_06.ej06.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.valentinilk.shimmer.shimmer
import modulo_06.ej06.room.DbMovie

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(
    viewModel: HomeViewVM = hiltViewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("New Movies List", color = Color.White) },
                actions = {
                    IconButton(
                        onClick = { viewModel.addMovie() }
                    ) {
                        Icon(Icons.Default.Add, "Add", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFFF8A65)
                )
            )
        }
    ) {
        ContentHomeView(it, viewModel)
    }
}

@Composable
fun ContentHomeView(
    paddingValues: PaddingValues,
    viewModel: HomeViewVM
) {
    val movies by viewModel.movies.collectAsState(listOf())
    val isLoading by viewModel.isLoading.collectAsState(false)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(8.dp)
    ) {
        items(movies) { movie ->
            MovieCard(movie = movie, onDeleteClick = { viewModel.deleteMovie(movie) })
        }
        // Loading indicator at the bottom
        item {
            if (isLoading) {
                Box(modifier = Modifier.fillMaxWidth().wrapContentSize(Alignment.Center)) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
fun MovieCard(movie: DbMovie, onDeleteClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(movie.title, style = TextStyle(fontWeight = FontWeight.Bold))
                Text(movie.overview ?: "") // Handle null overview
            }
            Spacer(modifier = Modifier.width(16.dp))
            IconButton(
                onClick = onDeleteClick
            ) {
                Icon(Icons.Default.Delete, "Delete")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieCardPreview() {
    MovieCard(
        movie = DbMovie(
            id = 1,
            title = "Sample Movie",
            overview = "This is a sample movie overview.",
            poster_path = "https://example.com/poster.jpg" // Replace with a valid URL
        ),
        onDeleteClick = {}
    )
}
