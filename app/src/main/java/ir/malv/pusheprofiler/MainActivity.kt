package ir.malv.pusheprofiler

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.InteractionState
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.animation.FlingConfig
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.foundation.lazy.LazyColumnForIndexed
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.pushe.plus.AppManifest
import co.pushe.plus.Pushe
import co.pushe.plus.analytics.messages.upstream.SessionFragmentMessageWrapper
import co.pushe.plus.analytics.messages.upstream.SessionInfoMessage
import co.pushe.plus.dagger.CoreComponent
import co.pushe.plus.fcm.PusheFCM
import co.pushe.plus.inappmessaging.PusheInAppMessaging
import co.pushe.plus.internal.PusheInternals
import co.pushe.plus.internal.uiThread
import co.pushe.plus.messaging.SendPriority
import co.pushe.plus.messaging.SendableUpstreamMessage
import co.pushe.plus.utils.BaseManifest
import co.pushe.plus.utils.TimeUtils.now
import co.pushe.plus.utils.TimeUtils.nowMillis
import ir.malv.pusheprofiler.ui.PusheProfilerTheme
import ir.malv.pusheprofiler.ui.purple700
import ir.malv.pusheprofiler.ui.typography
import ir.malv.utils.Pulp
import ir.malv.utils.db.PulpItem
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var baseManifest: BaseManifest

    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val pulpLogs = Pulp.getSavedLogs(this)
        baseManifest = BaseManifest(this.applicationContext)
        val pusheToken = baseManifest.readString("pushe_token")
        val onClearClicked = {
            Pulp.clearLogs(this)
        }
        setContent {
            PusheProfilerTheme {
                val s = rememberScaffoldState()
                Scaffold(
                    scaffoldState = s,
                    topBar = { TopAppBar(title = { Text(text = "PusheProfiler") }) },
                    drawerContent = { DrawerUi(pulpLogs.observeAsState(), onClearClicked) },
                    bodyContent = {
                        Column(
                            modifier = Modifier.fillMaxSize().padding(4.dp),
                            verticalArrangement = Arrangement.SpaceEvenly
                        ) {
                            val registrationState = remember { mutableStateOf(false) }
                            onActive {
                                Pushe.setRegistrationCompleteListener {
                                    registrationState.value = true
                                }
                            }
                            RegistrationState(
                                modifier = Modifier.weight(2f),
                                isRegistered = registrationState,
                                pusheToken = pusheToken
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Divider()
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = "Process items",
                                textAlign = TextAlign.Center
                            )
                            ProcessesUi(modifier = Modifier.weight(8f), scaffoldState = s)
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun RegistrationState(
    modifier: Modifier = Modifier,
    isRegistered: MutableState<Boolean>,
    pusheToken: String
) {
    val registration = remember { isRegistered }
    Card(
        modifier = modifier.padding(16.dp),
        elevation = 2.dp
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                val text = if (registration.value) "✅" else "❌"
                Text(text = "Registration:")
                Text(text = text)
            }
            Divider()
            val s = with(AnnotatedString.Builder()) {
                append("Token: ")
                // push green text style so that any appended text will be green
                pushStyle(SpanStyle(color = Color.Blue))
                // append new text, this text will be rendered as green
                append(pusheToken)
                // pop the green style
                pop()
                toAnnotatedString()
            }
            Text(
                text = s,
                modifier = Modifier.padding(8.dp),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
    }
}

@ExperimentalMaterialApi
@Composable
private fun ProcessesUi(modifier: Modifier = Modifier, scaffoldState: ScaffoldState) =
    Box(modifier = modifier) {
        val scope = rememberCoroutineScope()
        Surface(color = MaterialTheme.colors.background) {
            ScrollableColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,

                ) {
                ProcessItem(
                    name = "Registration",
                    desc = "Attempt registration with Pushe using the valid courier",
                    onClick = {
                        PusheInternals.getComponent(CoreComponent::class.java)
                            ?.registrationManager()
                            ?.a("profiler")
                            ?.observeOn(uiThread())
                            ?.subscribe {
                                scope.launch { scaffoldState.applySnackBar("Registeration") }
                            }
                    }
                )
                ProcessItem(
                    name = "Send a large upstream",
                    desc = "Attempts to send a large item as upstream data.",
                    onClick = {
                        scope.launch { scaffoldState.applySnackBar("Sending a an app message") }
                        val list = mutableListOf(
                            SessionFragmentMessageWrapper(
                                "test#${nowMillis()}",
                                startTime = nowMillis(),
                                duration = 2000
                            )
                        )
                        for (i in 1..10) {
                            list.add(list[0])
                        }
                        PusheInternals.getComponent(CoreComponent::class.java)
                            ?.postOffice()
                            ?.sendMessage(
                                SessionInfoMessage(
                                    sessionId = "something",
                                    name = "Test by profiler",
                                    startTime = nowMillis(),
                                    duration = 2000L,
                                    fragmentFlows = mutableMapOf(
                                        "1" to list,
                                        "2" to list,
                                        "3" to list,
                                        "4" to list,
                                        "5" to list,
                                    )
                                ),
                                sendPriority = SendPriority.IMMEDIATE
                            )
                    }
                )
                ProcessItem(
                    name = "Publish InAppMessage",
                    desc = "Attempts to publish a big ass in app message",
                    onClick = {
                        Pushe.getPusheService(PusheInAppMessaging::class.java)?.let {
                            it.setInAppMessagingListener(onReceive = { inApp ->
                                scope.launch {
                                    scaffoldState.applySnackBar("InAppMessage received. Title: ${inApp.title}")
                                }
                            })
                            it.testInAppMessage(
                                "{\"type\":\"center\",\"title\":{\"text\":\"\\u0628\\u0631\\u06a9\\u062a \\u0686\\u0637\\u0648\\u0631\\u0647\\u061f\",\"dir\":\"center\"},\"content\":{\"text\":\"\\u0627\\u06af\\u0631 \\u0627\\u0632 \\u0628\\u0631\\u06a9\\u062a \\u0631\\u0627\\u0636\\u06cc \\u0628\\u0648\\u062f\\u06cc\\u062f \\u0646\\u0638\\u0631 \\u0634\\u0645\\u0627 \\u0628\\u0627\\u0639\\u062b \\u062e\\u0648\\u0634\\u062d\\u0627\\u0644\\u06cc \\u0645\\u0627 \\u062e\\u0648\\u0627\\u0647\\u062f \\u0628\\u0648\\u062f :)\",\"dir\":\"left\"},\"condition\":{\"event\":\"on_foreground\",\"count\":1,\"time_gap\":0},\"buttons\":[{\"action\":{\"action_type\":\"I\",\"market_package_name\":\"com.farsitel.bazaar\",\"action\":\"android.intent.action.VIEW\",\"uri\":\"bazaar:\\/\\/details?id=shop.barkat.app\"},\"text\":\"\\u0646\\u0638\\u0631 \\u0645\\u06cc\\u062f\\u0645\",\"color\":\"#ffffff\",\"bg\":\"#878787\",\"dir\":\"center\"},{\"action\":{\"action_type\":\"D\"},\"text\":\"\\u0628\\u0639\\u062f\\u0627\",\"color\":\"#FFFFFF\",\"bg\":\"#000000\",\"dir\":\"center\"}],\"action\":{\"action_type\":\"D\"},\"im_count\":0}",
                                instant = true
                            )
                        }
                    }
                )
                ProcessItem(
                    name = "Subscribe to topic",
                    desc = "Adds current token to a FCM topic (test#random)",
                    onClick = {
                        scope.launch {
                            val topic = "test${Random.nextInt()}"
                            scaffoldState.applySnackBar("Subscribing to $topic")
                            Pushe.subscribeToTopic(topic) {
                                scope.launch { scaffoldState.applySnackBar("Successfully subscribed to $topic") }
                            }
                        }
                    }
                )
            }

        }

    }

@Composable
private fun DrawerUi(logs: State<List<PulpItem>?>, onClearLogsClicked: () -> Unit) {
    val scope = rememberCoroutineScope()
    val logsState = remember { logs }
    val list = logsState.value
    if (list == null || list.isEmpty()) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Text(text = "No logs saved")
        }
    } else {
        Box(modifier = Modifier.fillMaxSize()) {
            val lazyState = rememberLazyListState()
            val buttonEnabled = remember { mutableStateOf(false) }
            Column(modifier = Modifier.fillMaxSize()) {
                buttonEnabled.value = !lazyState.isAnimationRunning
                Box(
                    modifier = Modifier.fillMaxWidth().padding(4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Logs")
                        OutlinedButton(
                            onClick = onClearLogsClicked,
                            colors = ButtonConstants.defaultButtonColors(
                                backgroundColor = purple700,
                            )
                        ) {
                            Text(text = "Clear", style = TextStyle(color = Color.White))
                        }
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                LazyColumnFor(
                    state = lazyState,
                    modifier = Modifier.fillMaxSize().weight(9f),
                    horizontalAlignment = Alignment.Start,
                    items = list,
                    contentPadding = PaddingValues(start = 4.dp, end = 4.dp, top = 4.dp, bottom = 64.dp)
                ) { item ->
                    DrawerLogItem(item = item)
                }
            }
            if (buttonEnabled.value) {
                FloatingActionButton(
                    modifier = Modifier.padding(4.dp).align(Alignment.BottomEnd),
                    onClick = {
                    scope.launch { lazyState.snapToItemIndex(list.lastIndex) }
                }) {
                    Icon(imageVector = Icons.Default.ArrowDropDown, tint = Color.White)
                }
            }
        }
    }
}

@Composable
private fun DrawerLogItem(item: PulpItem) {
    val color = when (item.level) {
        "D" -> Color(0xFF8a8f96)
        "I" -> Color(0xFF2791c6)
        "W" -> Color(0xFFdd7d00)
        "E" -> Color(0xFFc14015)
        else -> Color(0xFF8f15a8)
    }
    Card(
        modifier = Modifier.fillMaxWidth().padding(4.dp),
        elevation = 4.dp,
        backgroundColor = color
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(8.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = item.message, style = TextStyle(fontSize = 15.sp, color = Color.White))
            Divider(color = Color.White)
            Text(text = item.data, style = TextStyle(fontSize = 13.sp, color = Color.White))
            Spacer(modifier = Modifier.height(10.dp))
            Divider(color = Color.White)
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = item.time.toString(),
                    style = TextStyle(fontSize = 12.sp, color = Color.White)
                )
                Text(text = item.tags, style = TextStyle(fontSize = 13.sp, color = Color.White))
            }
        }
    }
}

@ExperimentalMaterialApi
private suspend fun ScaffoldState.applySnackBar(message: String) {
    snackbarHostState.apply {
        currentSnackbarData?.dismiss()
        showSnackbar(message = message)
    }
}

@Composable
private fun ProcessItem(
    modifier: Modifier = Modifier,
    name: String,
    desc: String,
    onClick: () -> Unit
) = Card(
    modifier = modifier.fillMaxWidth().height(100.dp).padding(4.dp),
    elevation = 4.dp
) {
    Column(modifier = Modifier.fillMaxSize().padding(4.dp).clickable(onClick = onClick)) {
        Text(text = name, style = typography.h6)
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = desc)
    }
}