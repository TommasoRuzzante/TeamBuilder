package com.myapp.teambuilder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myapp.teambuilder.builder.Player
import com.myapp.teambuilder.builder.Roster
import com.myapp.teambuilder.ui.theme.TeamBuilderTheme

/**
 * Second screen of the app. Receives the list of players from
 * [MainActivity] via the Intent, runs the balanced team
 * partitioning algorithm (via [Roster]), and displays the
 * resulting two teams. Tapping the text copies the team
 * rosters to the system clipboard.
 */
class TeamsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Enable edge-to-edge display (draw behind status/navigation bars)
        enableEdgeToEdge()

        // Retrieve the player array passed from MainActivity.
        // getSerializableExtra is used because Player implements Serializable.
        val players = intent.getSerializableExtra("players") as? Array<Player>

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

/**
 * Composable that displays the generated team rosters.
 * If no players are available (e.g. intent extra was missing),
 * shows an error message instead.
 * 
 * The displayed text is clickable: tapping it copies the
 * formatted team rosters (without the "Click to copy" hint)
 * to the device clipboard.
 * 
 * @param modifier Layout modifiers
 * @param players  Array of players received from the previous screen,
 *                 or null if extraction from the Intent failed.
 */
@Composable
fun TeamsScreen(modifier: Modifier = Modifier, players: Array<Player>?) {
    // Guard clause: no players received -> show error
    if(players == null) {
        Text(text = "No players found", modifier = modifier.padding(16.dp))
        return
    }

    // Build a Roster, insert all players (sorted by rating),
    // then partition into two balanced teams and format as text.
    val roster = Roster(players.size)
    for(player in players)
        roster.insert(player)
    val textTeams = roster.getTeams()

    // Access the system clipboard service to enable copy-on-tap
    val clipboardManager = LocalClipboardManager.current

    Text(
        // Display hint text followed by the formatted team lists
        text = "Click the text to copy\n\n${textTeams}",
        modifier = modifier
            .padding(16.dp)
            .clickable(onClick = {
                // Copy only the team rosters (without the helper hint)
                clipboardManager.setText(AnnotatedString(textTeams))
            }),
        fontSize = 22.sp,
        fontWeight = FontWeight.Medium
    )
}

/**
 * Preview of TeamsScreen with 6 sample players, for Android Studio design view.
 */
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
