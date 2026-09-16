package com.myapp.teambuilder

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myapp.teambuilder.builder.Player
import com.myapp.teambuilder.ui.theme.TeamBuilderTheme

/**
 * Entry-point activity for the app. Presents the user with a form
 * to select the number of players and input each player's name
 * and skill rating. When the user clicks "Next", the collected
 * list of players is passed to [TeamsActivity] for partitioning.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Enable edge-to-edge display (draw behind status/navigation bars)
        enableEdgeToEdge()

        setContent {
            TeamBuilderTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    StartScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        // Callback invoked when the user confirms the player list
                        onDoneClicked = { players ->
                            val playersArray = players.toTypedArray()
                            // Build the Intent to navigate to TeamsActivity
                            val intent = Intent(this, TeamsActivity::class.java).apply {
                                putExtra("players", playersArray)
                            }
                            startActivity(intent)
                        }
                    )
                }
            }
        }
    }

}

/**
 * Top-level composable for the player input screen.
 * Manages the number of players and the list of Player objects,
 * coordinating between the dropdown selector, the input fields,
 * and the confirmation button.
 * @param modifier      Layout modifiers applied to the root container
 * @param onDoneClicked Callback triggered with the completed player list
 */
@Composable
fun StartScreen(modifier: Modifier = Modifier, onDoneClicked : (List<Player>) -> Unit) {
    // Selected number of players (persisted across config changes)
    var count by rememberSaveable {mutableIntStateOf(0)}
    // Mutable list of Player objects, state-saved so it survives rotation
    val players = rememberSaveable(
        saver = listSaver(
            save = { it.toList() },
            restore = { it.toMutableStateList() }
        )
    ) {
        mutableStateListOf<Player>()
    }

    // Keep the players list in sync with the selected count.
    // Whenever count changes, add empty placeholder players or
    // remove excess ones so the list size always equals count.
    LaunchedEffect(count) {
        while (players.size < count) players.add(Player("", 0))
        while (players.size > count) players.removeAt(players.size - 1)
    }

    // Root vertical layout: dropdown (top), input list (middle), button (bottom)
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Dropdown menu to choose the number of players
        MinimalDropdownMenu(modifier = Modifier
            .weight(1f)
            .fillMaxWidth(),
            onCountChanged = { choose ->
                count = choose
            }
        )

        // Scrollable list of (name + rating) text fields for each player
        NameInput(
            modifier = Modifier
                .weight(8f)
                .fillMaxWidth(),
            count = players.size,
            players = players
        )

        // Confirmation button: enabled only when count > 0 and the list is fully populated
        Button(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(16.dp),
            onClick = { onDoneClicked(players) },
            enabled = count > 0 && count == players.size
        ) {
            Text(text = stringResource(R.string.next_screen))
        }
    }
}

/**
 * A minimal row containing a label and an icon button that opens a
 * dropdown menu. The menu lets the user pick how many players will
 * participate (values are even numbers from 4 to 22: (i+2)*2 for i in 0..9).
 * @param modifier       Layout modifiers
 * @param onCountChanged Callback receiving the chosen player count
 */
@Composable
fun MinimalDropdownMenu(modifier: Modifier = Modifier, onCountChanged: (Int) -> Unit) {
    // Whether the dropdown menu is currently expanded
    var expanded by remember { mutableStateOf(false) }
    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Number of players:",
            modifier = Modifier
                .weight(3f)
                .padding(16.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp)
        ) {
            // Toggles the dropdown open/closed
            IconButton(onClick = { expanded = !expanded }) {
                Icon(Icons.Default.Menu, contentDescription = "More options")
            }
            // Dropdown listing the available even player counts (4 .. 22)
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                repeat(10) {
                    val num = (it + 2) * 2
                    DropdownMenuItem(
                        text = { Text("$num Players") },
                        onClick = {
                            expanded = false
                            onCountChanged(num)
                        }
                    )
                }
            }
        }
    }
}

/**
 * Renders a scrollable list of rows, one per player.
 * Each row contains two TextFields: one for the player's name
 * and one for their numeric skill rating.
 * 
 * Note: updating a field replaces the Player object in the list
 * (rather than mutating it in place) so Compose detects the state
 * change and recomposes the affected row.
 * 
 * @param modifier Layout modifiers
 * @param count    Number of input rows to render
 * @param players  Mutable list of Player objects to bind to the inputs
 */
@Composable
fun NameInput(modifier: Modifier = Modifier, count: Int, players: MutableList<Player>) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(count) { index ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Player name input (text keyboard, Next IME action)
                TextField(
                    value = players[index].name,
                    onValueChange = { newName ->
                        // Replace the object in the list to trigger recomposition
                        players[index] = Player(newName, players[index].rating)
                    },
                    maxLines = 1,
                    placeholder = { Text("Name") },
                    textStyle = TextStyle(
                        color = if (isSystemInDarkTheme()) Color.White else Color.Black,
                        fontWeight = FontWeight.Bold),
                    label = { Text("Player Name") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
                    modifier = Modifier
                        .weight(3f)
                        .padding(4.dp)
                )
                // Player rating input (numeric keyboard, Done IME action)
                TextField(
                    value = if (players[index].rating == 0) "" else players[index].rating.toString(),
                    onValueChange = { newRatingStr ->
                        val rating = newRatingStr.toIntOrNull() ?: 0
                        // Replace the object in the list to trigger recomposition
                        players[index] = Player(players[index].name, rating)
                    },
                    maxLines = 1,
                    placeholder = { Text("0") },
                    textStyle = TextStyle(
                        color = if (isSystemInDarkTheme()) Color.White else Color.Black,
                        fontWeight = FontWeight.Bold),
                    label = { Text("Rating") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
                    modifier = Modifier
                        .weight(1.2f)
                        .padding(4.dp)
                )
            }
        }
    }
}

/**
 * Preview of the StartScreen composable for Android Studio design view.
 */
@Preview(showBackground = true)
@Composable
fun StartScreenPreview() {
    StartScreen(onDoneClicked = {})
}
