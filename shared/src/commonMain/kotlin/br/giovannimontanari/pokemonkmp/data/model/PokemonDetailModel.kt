package br.giovannimontanari.pokemonkmp.data.model

data class PokemonDetailModel(
    val id: Int,
    val name: String,
    val imageUrl: String?,
    val height: Float,
    val weight: Float,
    val types: List<PokemonTypeModel>,
    val stats: List<StatModel>,
    val abilities: List<String>
) {
    constructor(response: PokemonDetailResponse) : this(
        id = response.id,
        name = response.name.replaceFirstChar { it.uppercase() },
        imageUrl = response.sprites.other?.officialArtwork?.frontDefault
            ?: response.sprites.frontDefault,
        height = response.height / 10f,
        weight = response.weight / 10f,
        types = response.types
            .sortedBy { it.slot }
            .map { PokemonTypeModel(it) },
        stats = response.stats.map { StatModel(it) },
        abilities = response.abilities.map {
            it.ability.name.replaceFirstChar { c -> c.uppercase() }
        }
    )
}

data class PokemonTypeModel(
    val name: String,
    val slot: Int
) {
    constructor(response: PokemonTypeSlot) : this(
        name = response.type.name.replaceFirstChar { it.uppercase() },
        slot = response.slot
    )
}

data class StatModel(
    val name: String,
    val value: Int
) {
    constructor(response: PokemonStatSlot) : this(
        name = when (response.stat.name) {
            "hp" -> "HP"
            "attack" -> "Attack"
            "defense" -> "Defense"
            "special-attack" -> "Sp. Atk"
            "special-defense" -> "Sp. Def"
            "speed" -> "Speed"
            else -> response.stat.name.replaceFirstChar { it.uppercase() }
        },
        value = response.baseStat
    )
}