package br.giovannimontanari.pokemonkmp.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
data object Home

@Serializable
data class Detail(val pokemonName: String, val pokemonId: Int)