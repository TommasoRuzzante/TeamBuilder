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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            TeamBuilderTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    StartScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        onDoneClicked = { players ->
                            val playersArray = players.toTypedArray()
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

@Composable
fun StartScreen(modifier: Modifier = Modifier, onDoneClicked : (List<Player>) -> Unit) {
    var count by rememberSaveable {mutableIntStateOf(0)}
    val players = rememberSaveable(
        saver = listSaver(
            save = { it.toList() },
            restore = { it.toMutableStateList() }
        )
    ) {
        mutableStateListOf<Player>()
    }

    LaunchedEffect(count) {
        while (players.size < count) players.add(Player("", 0))
        while (players.size > count) players.removeAt(players.size - 1)
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MinimalDropdownMenu(modifier = Modifier
            .weight(1f)
            .fillMaxWidth(),
            onCountChanged = { choose ->
                count = choose
            }
        )

        NameInput(
            modifier = Modifier
                .weight(8f)
                .fillMaxWidth(),
            count = players.size,
            players = players
        )

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

@Composable
fun MinimalDropdownMenu(modifier: Modifier = Modifier, onCountChanged: (Int) -> Unit) {
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
            IconButton(onClick = { expanded = !expanded }) {
                Icon(Icons.Default.Menu, contentDescription = "More options")
            }
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

@Preview(showBackground = true)
@Composable
fun StartScreenPreview() {
    StartScreen(onDoneClicked = {})
}
