package br.giovannimontanari.pokemonkmp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import br.giovannimontanari.pokemonkmp.presentation.detail.PokemonDetailScreen
import br.giovannimontanari.pokemonkmp.presentation.home.PokemonHomeScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Home
    ) {
        composable<Home> {
            PokemonHomeScreen(
                onPokemonClick = { pokemon ->
                    navController.navigate(Detail(pokemon.name, pokemon.id))
                }
            )
        }
        composable<Detail> { backStackEntry ->
            val route = backStackEntry.toRoute<Detail>()
            PokemonDetailScreen(
                pokemonName = route.pokemonName,
                pokemonId = route.pokemonId,
                onBack = { navController.popBackStack() }
            )
        }
    }
}