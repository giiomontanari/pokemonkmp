package br.giovannimontanari.pokemonkmp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.giovannimontanari.pokemonkmp.data.model.PokemonListModel
import br.giovannimontanari.pokemonkmp.data.model.PokemonModel
import br.giovannimontanari.pokemonkmp.data.repository.PokemonRepository
import br.giovannimontanari.pokemonkmp.data.utils.NetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


class PokemonHomeViewModel(
    private val repository: PokemonRepository
) : ViewModel() {

    private var currentOffset = 0
    private val pageSize = 20
    private var isLoadingMore = false

    private val _pokemons =
        MutableStateFlow<NetworkResult<List<PokemonModel>>>(NetworkResult.Loading)
    val pokemons: StateFlow<NetworkResult<List<PokemonModel>>> = _pokemons.asStateFlow()

    fun fetchPokemons(reset: Boolean = false) {
        if (reset) {
            currentOffset = 0
            _pokemons.value = NetworkResult.Loading
        }
        viewModelScope.launch {
            flow {
                emit(repository.getPokemonList(limit = pageSize, offset = currentOffset))
            }
                .map { response -> PokemonListModel(response).pokemons }
                .catch { e -> _pokemons.value = NetworkResult.Error(e.message ?: "Unknown error") }
                .collect { newPokemons ->
                    val current = (_pokemons.value as? NetworkResult.Success)?.data ?: emptyList()
                    currentOffset += pageSize
                    isLoadingMore = false
                    _pokemons.value = NetworkResult.Success(current + newPokemons)
                }
        }
    }

    fun loadMore() {
        if (isLoadingMore) return
        isLoadingMore = true
        fetchPokemons()
    }

}