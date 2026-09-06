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
import com.myapp.teambuilder.ui.theme.TeamBuilderTheme

class TeamsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val players = intent.getStringArrayExtra("players")

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
fun TeamsScreen(modifier: Modifier = Modifier, players: Array<String>?) {
    LazyColumn(modifier = modifier
        .fillMaxSize()
        .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        items(players!!.size) { index ->
            Text(
                modifier = Modifier.padding(16.dp),
                text = players[index]
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TeamsScreenPreview() {
    TeamsScreen(players = arrayOf("Player 1", "Player 2", "Player 3"))
}