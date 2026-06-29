# 🎮 PokémonKMP

Um aplicativo **Kotlin Multiplatform** que consome a [PokéAPI](https://pokeapi.co/), rodando nativamente em **Android**, **iOS** e **Desktop** com uma única base de código compartilhada.

---

## 📱 Screenshots

| Lista de Pokémons | Detalhes |
|---|---|
| Grid paginado com imagens oficiais | Stats, tipos, altura e peso |

---

## 🏗️ Stack Técnica

| Tecnologia | Versão | Uso |
|---|---|---|
| **Kotlin Multiplatform** | 2.4.0 | Base multiplataforma |
| **Compose Multiplatform** | 1.11.1 | UI compartilhada (Android, iOS, Desktop) |
| **Ktor Client** | 3.1.3 | HTTP client multiplataforma |
| **Navigation Compose** | 2.9.2 | Navegação type-safe (JetBrains fork) |
| **Koin** | 4.0.0 | Injeção de dependência |
| **Coil 3** | 3.1.0 | Carregamento de imagens |
| **Kotlinx Serialization** | 1.8.1 | Serialização JSON |
| **Kotlinx Coroutines** | 1.11.0 | Programação assíncrona |

---

## 🏛️ Arquitetura

O projeto segue **Clean Architecture** com separação clara entre as camadas:

```
shared/
└── src/
    └── commonMain/
        └── kotlin/
            └── br/giovannimontanari/pokemonkmp/
                ├── data/
                │   ├── model/          # DTOs da API + modelos de domínio
                │   ├── remote/         # Ktor HTTP client + API
                │   ├── repository/     # Repositório de dados
                │   └── utils/          # NetworkResult sealed class
                ├── di/                 # Módulos Koin
                └── presentation/
                    ├── home/           # Lista de Pokémons
                    ├── detail/         # Detalhes do Pokémon
                    └── navigation/     # Navegação com Navigation Compose
```

### Camadas

**Data** — Responsável por buscar e transformar dados da API. O `PokemonRepository` chama o `PokemonApi` (Ktor) e retorna os dados crus. O mapeamento para modelos de domínio acontece na camada de presentation.

**Presentation** — ViewModels expõem `StateFlow<NetworkResult<T>>` para as Screens. O mapeamento de DTOs para modelos de UI acontece aqui, mantendo o repositório limpo.

**Navigation** — Navegação type-safe com `NavHost` e rotas serializáveis.

---

## 🌊 Fluxo de Dados

```
PokéAPI
   ↓ Ktor Client
PokemonApi (HTTP)
   ↓
PokemonRepository (suspend fun)
   ↓
ViewModel (Flow + map + catch)
   ↓ StateFlow<NetworkResult<T>>
Screen (collectAsStateWithLifecycle)
   ↓
UI (Loading / Success / Error)
```

---

## 🔁 NetworkResult

Padrão usado para encapsular estados de rede de forma expressiva:

```kotlin
sealed class NetworkResult<out T> {
    data object Loading : NetworkResult<Nothing>()
    data class Success<T>(val data: T) : NetworkResult<T>()
    data class Error(val message: String) : NetworkResult<Nothing>()
}
```

Nas Screens:

```kotlin
when (val result = pokemons) {
    is NetworkResult.Loading -> CircularProgressIndicator()
    is NetworkResult.Success -> PokemonGrid(result.data)
    is NetworkResult.Error   -> ErrorView(result.message)
}
```

---

## 🌐 API

Consumindo a [PokéAPI](https://pokeapi.co/) — uma API REST pública e gratuita com dados de todos os Pokémons.

```kotlin
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
```

---

## 🧭 Navegação

Usando o fork multiplataforma do **Navigation Compose** do JetBrains com rotas type-safe via `@Serializable`:

```kotlin
@Serializable data object Home
@Serializable data class Detail(val pokemonName: String, val pokemonId: Int)

NavHost(navController, startDestination = Home) {
    composable<Home> { PokemonHomeScreen(...) }
    composable<Detail> { PokemonDetailScreen(...) }
}
```

---

## 💉 Injeção de Dependência

Koin com módulos separados por responsabilidade:

```kotlin
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
```

---

## 🚀 Como Rodar

### Pré-requisitos
- Android Studio Meerkat ou superior
- JDK 11+
- Para iOS: macOS com Xcode 15+

### Android
```bash
./gradlew :androidApp:installDebug
```

### Desktop
```bash
./gradlew :desktopApp:run
```

### iOS
Abra `iosApp/iosApp.xcodeproj` no Xcode e rode no simulador.

---

## 📦 Módulos do Projeto

```
pokemonkmp/
├── androidApp/     # Entry point Android (Activity)
├── desktopApp/     # Entry point Desktop (main.kt)
├── iosApp/         # Entry point iOS (SwiftUI wrapper)
└── shared/         # Código compartilhado (UI + lógica)
```

---

## ✨ Features

- [x] Lista de Pokémons com paginação infinita (20 por página)
- [x] Imagens oficiais de cada Pokémon
- [x] Tela de detalhes com stats, tipos, altura e peso
- [x] Suporte a Android, iOS e Desktop
- [x] Tratamento de erros com retry
- [x] Loading states

---

## 👨‍💻 Autor

**Giovanni Montanari** — [giovannimontanari](https://github.com/giovannimontanari)

---

*Feito com ❤️ e muito Kotlin*
