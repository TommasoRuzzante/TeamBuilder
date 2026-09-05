package com.myapp.teambuilder

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StartScreen() {
    var count by rememberSaveable {mutableIntStateOf(0)}
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = "$count",
            onValueChange = { count = it.toIntOrNull() ?: 0 },
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            textStyle = TextStyle(fontSize = 20.sp),
            placeholder = { Text(text = stringResource(R.string.count)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true
        )

        NameInput(
            modifier = Modifier
                .weight(8f)
                .fillMaxWidth(),
            count = count
        )

        Button(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            onClick = {}
        ) {
            Text(text = stringResource(R.string.next_screen))
        }
    }
}

@Composable
fun NameInput(modifier: Modifier = Modifier, count: Int) {
    val title = stringResource(R.string.insert)
    val players = rememberSaveable {mutableListOf<String>()}

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(count) { index->
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                )

                players.add(index, "")
                TextField(
                    value = players[index],
                    onValueChange = { players[index] = it },
                    modifier = Modifier
                        .weight(2f)
                        .fillMaxWidth(),
                    textStyle = TextStyle(fontSize = 20.sp),
                    placeholder = { Text(text = stringResource(R.string.placeholder)) },
                    isError = players[index].isEmpty(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    singleLine = true
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StartScreenPreview() {
    StartScreen()
}