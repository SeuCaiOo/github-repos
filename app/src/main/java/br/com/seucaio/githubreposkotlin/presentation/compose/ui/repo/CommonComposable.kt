package br.com.seucaio.githubreposkotlin.presentation.compose.ui.repo

import androidx.compose.foundation.layout.*
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import br.com.seucaio.githubreposkotlin.R

@Composable
fun ShowProgress(modifier: Modifier) {
    Column(modifier = modifier) {
        Spacer(modifier = Modifier.weight(1f))
        LinearProgressIndicator(modifier = Modifier
            .height(10.dp)
            .fillMaxWidth())
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun EmptyContent(modifier: Modifier) {
    Column(modifier = modifier) {
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = stringResource(id = R.string.msg_error_list_repositories),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}
