package br.giovannimontanari.pokemonkmp.data.repository

import br.giovannimontanari.pokemonkmp.data.model.PokemonDetailResponse
import br.giovannimontanari.pokemonkmp.data.model.PokemonResponse
import br.giovannimontanari.pokemonkmp.data.remote.PokemonApi


class PokemonRepository(private val api: PokemonApi) {

    suspend fun getPokemonList(
        limit: Int = 20,
        offset: Int = 0
    ): PokemonResponse = api.getPokemonList(limit, offset)

    suspend fun getPokemonDetail(name: String): PokemonDetailResponse =
        api.getPokemonDetail(name)
}
