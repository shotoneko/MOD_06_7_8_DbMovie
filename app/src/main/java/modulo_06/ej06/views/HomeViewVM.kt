package modulo_06.ej06.views

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import modulo_06.ej06.repository.Repository
import modulo_06.ej06.room.DbMovie
import javax.inject.Inject

@HiltViewModel
class HomeViewVM @Inject constructor(
    private val repo: Repository
): ViewModel() {

    private val _isLoading: MutableStateFlow<Boolean> by lazy {
        MutableStateFlow<Boolean>(false)
    }

    val isLoading: Flow<Boolean> get() = _isLoading

    val movies: Flow<List<DbMovie>> by lazy {
      repo.getAllMoviesDb()
    }

    fun addMovie() {
        if (!_isLoading.value) {
            viewModelScope.launch(Dispatchers.IO) {
                _isLoading.value = true
                repo.getNewMovie()
                _isLoading.value = false
            }
        }
    }

    fun deleteMovie(toDelete: DbMovie) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteDbMovie(toDelete)
        }
    }



}