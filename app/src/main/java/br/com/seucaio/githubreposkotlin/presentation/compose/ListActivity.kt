package br.com.seucaio.githubreposkotlin.presentation.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.seucaio.githubreposkotlin.presentation.compose.ui.theme.GithubreposkotlinTheme

class ListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                MyScreenContent()
            }
        }
    }

}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    GithubreposkotlinTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colors.background) {
            content()
        }
    }
}

@Composable
fun MyScreenContent(names: List<String> = listOf("Android", "iOS", "Windows")) {
    var counterState by remember { mutableStateOf<Int>(0) }
    Column {
        for (name in names) {
            Greeting(name = name)
            Divider()
        }
        Counter(
            count = counterState,
            updateCount = { newCount: Int -> counterState = newCount }
        )
        if(counterState > 5) {
            Text(text = "Exagerou na contagem")
        }
     }
}

@Composable
fun Counter(count: Int, updateCount: ((Int) -> Unit) ) {
    Button(onClick = { updateCount(count + 1) }) {
        Text(text = "Voce clicou $count vezes")
    }
}

@Composable
fun Greeting(name: String) {
    Text(
        text = "Hello $name!",
        modifier = Modifier.padding(16.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApp {
        MyScreenContent()
    }
}