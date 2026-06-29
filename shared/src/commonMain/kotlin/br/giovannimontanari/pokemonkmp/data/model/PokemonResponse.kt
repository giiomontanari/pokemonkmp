package br.giovannimontanari.pokemonkmp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Pokemon>
)

@Serializable
data class Pokemon(
    val name: String,
    val url: String
)

fun Pokemon. id(): Int =
    url.trimEnd('/').split("/").last().toInt()

fun Pokemon.imageUrl(): String =
    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${id()}.png"

@Serializable
data class PokemonDetailResponse(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val types: List<PokemonTypeSlot>,
    val stats: List<PokemonStatSlot>,
    val sprites: PokemonSprites,
    val abilities: List<PokemonAbilitySlot>
)

@Serializable
data class PokemonTypeSlot(
    val slot: Int,
    val type: NamedResource
)

@Serializable
data class PokemonStatSlot(
    @SerialName("base_stat") val baseStat: Int,
    val stat: NamedResource
)

@Serializable
data class PokemonSprites(
    @SerialName("front_default") val frontDefault: String?,
    val other: OtherSprites?
)

@Serializable
data class OtherSprites(
    @SerialName("official-artwork") val officialArtwork: OfficialArtwork?
)

@Serializable
data class OfficialArtwork(
    @SerialName("front_default") val frontDefault: String?
)

@Serializable
data class NamedResource(
    val name: String,
    val url: String
)

@Serializable
data class PokemonAbilitySlot(
    val ability: NamedResource,
    @SerialName("is_hidden") val isHidden: Boolean
)