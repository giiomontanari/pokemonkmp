package br.giovannimontanari.pokemonkmp

import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import br.giovannimontanari.pokemonkmp.di.appModules
import br.giovannimontanari.pokemonkmp.presentation.home.PokemonHomeScreen
import br.giovannimontanari.pokemonkmp.presentation.navigation.AppNavHost
import org.koin.compose.KoinApplication


@Composable
@Preview
fun App() {
    KoinApplication(application = {
        modules(appModules)
    }) {
        AppNavHost()
    }
}