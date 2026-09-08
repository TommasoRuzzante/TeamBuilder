package com.myapp.teambuilder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.myapp.teambuilder.builder.Player
import com.myapp.teambuilder.builder.Roster
import com.myapp.teambuilder.ui.theme.TeamBuilderTheme

class TeamsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val players = intent.getParcelableArrayExtra("players") as Array<Player>?

        setContent {
            TeamBuilderTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TeamsScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        players = players
                    )
                }
            }
        }
    }

}

@Composable
fun TeamsScreen(modifier: Modifier = Modifier, players: Array<Player>?) {
    if(players == null) return

    val roster = Roster(players.size)
    for(player in players)
        roster.insert(player)

    Text(
        text = roster.getTeams(),
        modifier = modifier.padding(16.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun TeamsScreenPreview() {
    TeamsScreen(players = arrayOf(
        Player("Giocatore 1", 10),
        Player("Giocatore 2", 20),
        Player("Giocatore 3", 30),
        Player("Giocatore 4", 40),
        Player("Giocatore 5", 50),
        Player("Giocatore 6", 60)
    ))
}