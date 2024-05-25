package com.example.fitgo.ui.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import com.example.fitgo.domain.chatmodel.Conversation
import com.example.fitgo.domain.chatmodel.Message
import com.example.fitgo.domain.chatmodel.MessageStatus
import kotlinx.coroutines.launch
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fitgo.R
import com.example.fitgo.ui.dietas.DietasViewModel

@OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun PantallaChat(
    viewModel: ChatViewModel = hiltViewModel(),
    ){
    val state = viewModel.uistate.collectAsStateWithLifecycle()
        val coroutineScope = rememberCoroutineScope()
        val listState = rememberLazyListState()
         var inputValue by remember { mutableStateOf("") }


        fun sendMessage() {
            viewModel.event(ChatContract.Event.onSendMessage(inputValue))
            inputValue = ""
            coroutineScope.launch {
                listState.animateScrollToItem(state.value.conversation?.list?.size ?: 0)
            }
        }
    Scaffold(
    //    snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues = paddingValues)
                .padding(horizontal = 16.dp)
                .padding(vertical = 16.dp)
        ) {
            Box(
                modifier = Modifier.weight(1f)
            ) {
                state.value.conversation?.let {
                    MessageList(
                        messagesList = it.list,
                        listState = listState,
                        onResendMessage ={viewModel.event(ChatContract.Event.onResendMessage(it))}
                    )
                }
            }
            Row {
                TextField(
                    value = inputValue,
                    onValueChange = { inputValue = it },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                    keyboardActions = KeyboardActions {
                        sendMessage()
                    },
                    modifier = Modifier.weight(1f),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    )
                )
                HorizontalSpacer(8.dp)
                Button(
                    modifier = Modifier.height(56.dp),
                    onClick = { sendMessage() },
                    enabled = inputValue.isNotBlank() && state.value._isSendingMessage != true,
                ) {
                    if (state.value._isSendingMessage == true) {
                        Icon(
                            imageVector = Icons.Default.Sync,
                            contentDescription = "Sending"
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Send,
                            contentDescription = "Send"
                        )
                    }

                }
            }
        }
    }
    }
@Composable
fun MessageList(
    messagesList: List<Message>,
    listState: LazyListState,
    onResendMessage: (Message) -> Unit
) {
    LazyColumn(
        state = listState
    ) {
        items(messagesList) { message ->
            Row {
                if (message.isFromUser) {
                    HorizontalSpacer(width = 16.dp)
                    Box(
                        modifier = Modifier.weight(weight = 1f)
                    )
                }
                Text(
                    text = message.text,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.inverseSurface,
                    textAlign = if (message.isFromUser) { TextAlign.End } else { TextAlign.Start },
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            if (message.messageStatus == MessageStatus.Error) {
                                MaterialTheme.colorScheme.errorContainer
                            } else {
                                if (message.isFromUser) {
                                    MaterialTheme.colorScheme.secondaryContainer
                                } else {
                                    MaterialTheme.colorScheme.primaryContainer
                                }
                            }
                        )
                        .clickable(enabled = message.messageStatus == MessageStatus.Error) {
                            onResendMessage(message)
                        }
                        .padding(all = 8.dp)

                )
                if (!message.isFromUser) {
                    HorizontalSpacer(width = 16.dp)
                    Box(
                        modifier = Modifier.weight(weight = 1f)
                    )
                }
            }
            if (message.messageStatus == MessageStatus.Sending) {
                Text(
                    text = stringResource(R.string.chat_message_loading),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                HorizontalSpacer(width = 32.dp)
            }
            if (message.messageStatus == MessageStatus.Error) {
                Row(
                    modifier = Modifier
                        .clickable {
                            onResendMessage(message)
                        }
                ) {
                    Box(
                        modifier = Modifier.weight(weight = 1f)
                    )
                    Text(
                        text = stringResource(R.string.chat_message_error),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
            VerticalSpacer(height = 8.dp)
        }
    }
}

@Composable
fun VerticalSpacer(height: Dp) = Spacer(Modifier.height(height))

@Composable
fun HorizontalSpacer(width: Dp) = Spacer(Modifier.width(width))