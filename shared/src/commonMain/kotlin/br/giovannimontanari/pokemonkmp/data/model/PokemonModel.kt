package br.giovannimontanari.pokemonkmp.data.model

data class PokemonModel(
    val id: Int,
    val name: String,
    val imageUrl: String
) {
    constructor(response: Pokemon) : this(
        id = response.id(),
        name = response.name.replaceFirstChar { it.uppercase() },
        imageUrl = response.imageUrl()
    )
}