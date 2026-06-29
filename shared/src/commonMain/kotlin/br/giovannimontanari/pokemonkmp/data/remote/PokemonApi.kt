package br.giovannimontanari.pokemonkmp.data.remote

import br.giovannimontanari.pokemonkmp.data.model.PokemonDetailResponse
import br.giovannimontanari.pokemonkmp.data.model.PokemonResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class PokemonApi(private val client: HttpClient) {

    suspend fun getPokemonList(
        limit: Int = 20,
        offset: Int = 0
    ): PokemonResponse = client.get("pokemon") {
        parameter("limit", limit)
        parameter("offset", offset)
    }.body()

    suspend fun getPokemonDetail(name: String): PokemonDetailResponse =
        client.get("pokemon/$name").body()
}