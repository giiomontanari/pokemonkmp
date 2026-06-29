package br.giovannimontanari.pokemonkmp.data.model

data class PokemonListModel(
    val count: Int,
    val next: String?,
    val previous: String?,
    val pokemons: List<PokemonModel>
) {
    constructor(response: PokemonResponse) : this(
        count = response.count,
        next = response.next,
        previous = response.previous,
        pokemons = response.results.map { PokemonModel(it) }
    )
}