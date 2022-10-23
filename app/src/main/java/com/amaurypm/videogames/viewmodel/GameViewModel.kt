package com.amaurypm.videogames.viewmodel

import androidx.lifecycle.*
import com.amaurypm.videogames.db.GameRepository
import com.amaurypm.videogames.model.entities.GameEntity
import kotlinx.coroutines.launch

/**
 * Creado por Amaury Perea Matsumura el 22/10/22
 */
class GameViewModel(private val repository: GameRepository): ViewModel() {

    //El objetivo del viewmodel es proveer datos a la UI, sobreviviendo a cambios de
    //configuración.
    //Actúa como un centro de comunicación entre el repositorio y la UI

    fun insertGame(game: GameEntity) = viewModelScope.launch {
        repository.insertGameData(game)
    }

    val allGamesList: LiveData<List<GameEntity>> = repository.allGamesList.asLiveData()

    fun getGame(id: Long): LiveData<GameEntity> = repository.getGame(id).asLiveData()

    fun deleteGame(game: GameEntity) = viewModelScope.launch {
        repository.deleteGame(game)
    }

    fun updateGame(game: GameEntity) = viewModelScope.launch {
        repository.updateGame(game)
    }

    class GameViewModelFactory(private val repository: GameRepository): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(GameViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return GameViewModel(repository) as T
            }
            throw  java.lang.IllegalArgumentException("Clase ViewModel desconocida")
        }
    }
}