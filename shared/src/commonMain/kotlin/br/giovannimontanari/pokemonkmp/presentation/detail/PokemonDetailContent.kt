package br.giovannimontanari.pokemonkmp.presentation.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.giovannimontanari.pokemonkmp.data.model.PokemonDetailModel
import br.giovannimontanari.pokemonkmp.presentation.detail.widget.InfoItem
import br.giovannimontanari.pokemonkmp.presentation.detail.widget.StatRow
import coil3.compose.AsyncImage
import org.jetbrains.compose.resources.stringResource
import pokemonkmp.shared.generated.resources.Res
import pokemonkmp.shared.generated.resources.base_stats
import pokemonkmp.shared.generated.resources.height_poke
import pokemonkmp.shared.generated.resources.weight_poke

@Composable
fun PokemonDetailContent(pokemon: PokemonDetailModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "#${pokemon.id.toString().padStart(3, '0')}",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        AsyncImage(
            model = pokemon.imageUrl,
            contentDescription = pokemon.name,
            modifier = Modifier.size(220.dp),
            contentScale = ContentScale.Fit
        )

        Text(
            text = pokemon.name,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            pokemon.types.forEach { type ->
                SuggestionChip(
                    onClick = {},
                    label = { Text(type.name) }
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            InfoItem(label = stringResource(Res.string.height_poke), value = "${pokemon.height} m")
            InfoItem(label = stringResource(Res.string.weight_poke), value = "${pokemon.weight} kg")
        }

        Spacer(Modifier.height(24.dp))

        Text(
            text = stringResource(Res.string.base_stats),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        pokemon.stats.forEach { stat ->
            StatRow(stat = stat)
            Spacer(Modifier.height(6.dp))
        }
    }
}