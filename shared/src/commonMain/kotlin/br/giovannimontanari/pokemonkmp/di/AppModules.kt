package br.giovannimontanari.pokemonkmp.di

import br.giovannimontanari.pokemonkmp.data.remote.HttpClientFactory
import br.giovannimontanari.pokemonkmp.data.remote.PokemonApi
import br.giovannimontanari.pokemonkmp.data.repository.PokemonRepository
import br.giovannimontanari.pokemonkmp.presentation.detail.PokemonDetailViewModel
import br.giovannimontanari.pokemonkmp.presentation.home.PokemonHomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val networkModule = module {
    single { HttpClientFactory.create() }
    single { PokemonApi(get()) }
}

val repositoryModule = module {
    single { PokemonRepository(get()) }
}

val viewModelModule = module {
    viewModel { PokemonHomeViewModel(get()) }
    viewModel { PokemonDetailViewModel(get()) }
}

val appModules = listOf(
    networkModule,
    repositoryModule,
    viewModelModule
)