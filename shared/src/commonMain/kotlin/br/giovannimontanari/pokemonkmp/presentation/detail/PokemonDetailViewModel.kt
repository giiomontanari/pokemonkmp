package br.giovannimontanari.pokemonkmp.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.giovannimontanari.pokemonkmp.data.model.PokemonDetailModel
import br.giovannimontanari.pokemonkmp.data.repository.PokemonRepository
import br.giovannimontanari.pokemonkmp.data.utils.NetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class PokemonDetailViewModel(
    private val repository: PokemonRepository
) : ViewModel() {

    private val _pokemon = MutableStateFlow<NetworkResult<PokemonDetailModel>>(NetworkResult.Loading)
    val pokemon: StateFlow<NetworkResult<PokemonDetailModel>> = _pokemon.asStateFlow()

    fun fetchPokemon(name: String) {
        viewModelScope.launch {
            flow {
                emit(repository.getPokemonDetail(name))
            }
                .map { response -> PokemonDetailModel(response) }
                .onStart { _pokemon.value = NetworkResult.Loading }
                .catch { e -> _pokemon.value = NetworkResult.Error(e.message ?: "Unknown error") }
                .collect { _pokemon.value = NetworkResult.Success(it) }
        }
    }
}