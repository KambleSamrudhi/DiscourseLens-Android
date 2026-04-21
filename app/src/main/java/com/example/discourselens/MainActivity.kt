package com.example.discourselens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.discourselens.ui.theme.DiscourseLensTheme
import kotlinx.coroutines.delay
import kotlin.math.cos
import kotlin.math.sin

data class AppColors(
    val background: Color,
    val surface: Color,
    val surfaceAlt: Color,
    val border: Color,
    val divider: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val textMuted: Color,
    val accent: Color,
    val accentSecondary: Color,
    val accentSoft: Color,
    val success: Color,
    val warning: Color,
    val danger: Color,
    val progressTrack: Color,
    val loadingRing: Color
)

fun discourseColors(isDarkMode: Boolean): AppColors {
    return if (isDarkMode) {
        AppColors(
            background = Color.Black,
            surface = Color(0xFF12141B),
            surfaceAlt = Color(0xFF1A1C24),
            border = Color(0xFF242632),
            divider = Color(0xFF1F1F1F),
            textPrimary = Color.White,
            textSecondary = Color(0xFFB8A9E8),
            textMuted = Color(0xFF8F96A8),
            accent = Color(0xFF6D28FF),
            accentSecondary = Color(0xFF9B7BFF),
            accentSoft = Color(0xFF2B165A),
            success = Color(0xFF10C987),
            warning = Color(0xFFF2B705),
            danger = Color(0xFFFF4D67),
            progressTrack = Color(0xFF2B3040),
            loadingRing = Color(0xFF2A1E4D)
        )
    } else {
        AppColors(
            background = Color(0xFFF7F7FB),
            surface = Color.White,
            surfaceAlt = Color(0xFFF2F3FA),
            border = Color(0xFFE1E4F2),
            divider = Color(0xFFE7EAF4),
            textPrimary = Color(0xFF16181D),
            textSecondary = Color(0xFF6E5AA8),
            textMuted = Color(0xFF6B7280),
            accent = Color(0xFF6D28FF),
            accentSecondary = Color(0xFF8C6BFF),
            accentSoft = Color(0xFFF1EAFF),
            success = Color(0xFF0FA968),
            warning = Color(0xFFD89B00),
            danger = Color(0xFFE5485E),
            progressTrack = Color(0xFFE4E7F4),
            loadingRing = Color(0xFFDCCEFF)
        )
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            var isDarkMode by remember { mutableStateOf(true) }
            val c = discourseColors(isDarkMode)

            DiscourseLensTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = c.background
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "home"
                    ) {
                        composable("home") {
                            HomeScreen(
                                navController = navController,
                                isDarkMode = isDarkMode,
                                onThemeToggle = { isDarkMode = it }
                            )
                        }
                        composable("second") {
                            SecondScreen(
                                navController = navController,
                                isDarkMode = isDarkMode,
                                onThemeToggle = { isDarkMode = it }
                            )
                        }
                        composable("upload") {
                            UploadScreen(
                                navController = navController,
                                isDarkMode = isDarkMode,
                                onThemeToggle = { isDarkMode = it }
                            )
                        }
                        composable("loading") {
                            LoadingScreen(
                                navController = navController,
                                isDarkMode = isDarkMode,
                                onThemeToggle = { isDarkMode = it }
                            )
                        }
                        composable("results") {
                            ResultsScreen(
                                navController = navController,
                                isDarkMode = isDarkMode,
                                onThemeToggle = { isDarkMode = it }
                            )
                        }
                        composable(
                            route = "feedback/{paragraphId}",
                            arguments = listOf(
                                navArgument("paragraphId") { type = NavType.IntType }
                            )
                        ) { backStackEntry ->
                            val paragraphId = backStackEntry.arguments?.getInt("paragraphId") ?: 1
                            ParagraphFeedbackScreen(
                                navController = navController,
                                paragraphId = paragraphId,
                                isDarkMode = isDarkMode,
                                onThemeToggle = { isDarkMode = it }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HomeScreen(
    navController: NavController,
    isDarkMode: Boolean,
    onThemeToggle: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val c = discourseColors(isDarkMode)

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(c.background)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(top = 8.dp, end = 16.dp),
            horizontalArrangement = Arrangement.End
        ) {
            ThemeToggleChip(
                isDarkMode = isDarkMode,
                onThemeToggle = onThemeToggle
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(24.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(c.accentSecondary, c.accent)
                        ),
                        shape = RoundedCornerShape(24.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "✦",
                    color = Color.White,
                    fontSize = 30.sp
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "DiscourseLens",
                color = c.textPrimary,
                fontSize = 26.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "AI-powered writing analysis",
                color = c.textMuted,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(32.dp))

            Card(
                onClick = { navController.navigate("second") },
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = c.surface),
                border = BorderStroke(1.dp, c.border),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(18.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .background(
                                c.surfaceAlt,
                                shape = RoundedCornerShape(12.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("📊")
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {
                        Text(
                            text = "Text Analysis",
                            color = c.textPrimary,
                            fontWeight = FontWeight.Medium
                        )

                        Text(
                            text = "Get detailed discourse insights",
                            color = c.textMuted,
                            fontSize = 12.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            Row {
                Text("About", color = c.textMuted, fontSize = 12.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Text("•", color = c.textMuted)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Help", color = c.textMuted, fontSize = 12.sp)
                Spacer(modifier = Modifier.width(8.dp))
                Text("•", color = c.textMuted)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Terms", color = c.textMuted, fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun ThemeToggleChip(
    isDarkMode: Boolean,
    onThemeToggle: (Boolean) -> Unit
) {
    val c = discourseColors(isDarkMode)
    val pillColor by animateColorAsState(
        targetValue = if (isDarkMode) c.surface else c.surface,
        label = "pillColor"
    )
    val knobOffset by animateFloatAsState(
        targetValue = if (isDarkMode) 28f else 0f,
        animationSpec = spring(dampingRatio = 0.75f, stiffness = 500f),
        label = "knobOffset"
    )
    val iconRotation by animateFloatAsState(
        targetValue = if (isDarkMode) 180f else 0f,
        animationSpec = tween(450, easing = FastOutSlowInEasing),
        label = "iconRotation"
    )
    val trackColor by animateColorAsState(
        targetValue = if (isDarkMode) c.accentSoft else Color(0xFFFFF0B3),
        label = "trackColor"
    )

    Box(
        modifier = Modifier
            .width(86.dp)
            .height(40.dp)
            .clip(RoundedCornerShape(22.dp))
            .background(pillColor)
            .border(1.dp, c.border, RoundedCornerShape(22.dp))
            .clickable { onThemeToggle(!isDarkMode) }
            .padding(horizontal = 5.dp, vertical = 5.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(18.dp))
                .background(trackColor)
        )

        Box(
            modifier = Modifier
                .offset(x = knobOffset.dp)
                .size(30.dp)
                .clip(CircleShape)
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            SunMoonIcon(
                isDarkMode = isDarkMode,
                rotation = iconRotation,
                accent = c.accent,
                muted = Color(0xFFF4B400)
            )
        }
    }
}

@Composable
fun SunMoonIcon(
    isDarkMode: Boolean,
    rotation: Float,
    accent: Color,
    muted: Color
) {
    Canvas(
        modifier = Modifier
            .size(18.dp)
            .graphicsLayer {
                rotationZ = rotation
            }
    ) {
        if (isDarkMode) {
            drawCircle(
                color = accent,
                radius = size.minDimension * 0.33f,
                center = center
            )
            drawCircle(
                color = Color.White,
                radius = size.minDimension * 0.28f,
                center = Offset(center.x + size.minDimension * 0.12f, center.y - size.minDimension * 0.08f)
            )
        } else {
            drawCircle(
                color = muted,
                radius = size.minDimension * 0.28f,
                center = center
            )
            repeat(8) { index ->
                rotate(index * 45f, center) {
                    drawLine(
                        color = muted,
                        start = Offset(center.x, center.y - size.minDimension * 0.45f),
                        end = Offset(center.x, center.y - size.minDimension * 0.36f),
                        strokeWidth = 2f,
                        cap = StrokeCap.Round
                    )
                }
            }
        }
    }
}

@Composable
fun SecondScreen(
    navController: NavController,
    isDarkMode: Boolean,
    onThemeToggle: (Boolean) -> Unit
) {
    val c = discourseColors(isDarkMode)
    var textState by remember { mutableStateOf(TextFieldValue("")) }

    val wordCount = if (textState.text.isBlank()) 0
    else textState.text.trim().split(Regex("\\s+")).size

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(c.background)
            .verticalScroll(rememberScrollState())
    ) {
        TopHeader(
            navController = navController,
            title = "DiscourseLens",
            subtitle = "Analyze Your Writing",
            isDarkMode = isDarkMode,
            onThemeToggle = onThemeToggle
        )

        HorizontalDivider(
            thickness = 0.6.dp,
            color = c.divider
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 18.dp)
        ) {
            Text(
                text = "Enter or paste your text",
                color = c.textPrimary,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(14.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(365.dp)
                    .clip(RoundedCornerShape(22.dp))
                    .background(c.surface)
                    .border(
                        width = 1.dp,
                        color = c.border,
                        shape = RoundedCornerShape(22.dp)
                    )
                    .padding(20.dp)
            ) {
                BasicTextField(
                    value = textState,
                    onValueChange = { textState = it },
                    modifier = Modifier.fillMaxSize(),
                    textStyle = TextStyle(
                        color = c.textPrimary,
                        fontSize = 20.sp,
                        lineHeight = 28.sp
                    ),
                    decorationBox = { innerTextField ->
                        if (textState.text.isEmpty()) {
                            Text(
                                text = "Type or paste your text here for analysis...",
                                color = c.textMuted,
                                fontSize = 16.sp,
                                lineHeight = 24.sp
                            )
                        }
                        innerTextField()
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "$wordCount words",
                color = c.textMuted,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(26.dp))

            Button(
                onClick = { navController.navigate("loading") },
                enabled = textState.text.isNotBlank(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(68.dp),
                shape = RoundedCornerShape(22.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = c.accent,
                    disabledContainerColor = c.accent.copy(alpha = 0.4f)
                )
            ) {
                Text(
                    text = "✧  Analyze Text",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            OutlinedButton(
                onClick = { navController.navigate("upload") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(72.dp),
                shape = RoundedCornerShape(22.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = c.surface,
                    contentColor = c.textPrimary
                ),
                border = BorderStroke(1.dp, c.border)
            ) {
                Icon(
                    imageVector = Icons.Filled.Upload,
                    contentDescription = "Upload"
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Upload File",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun UploadScreen(
    navController: NavController,
    isDarkMode: Boolean,
    onThemeToggle: (Boolean) -> Unit
) {
    val c = discourseColors(isDarkMode)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(c.background)
            .verticalScroll(rememberScrollState())
    ) {
        TopHeader(
            navController = navController,
            title = "Upload File",
            subtitle = "PDF, DOCX, or TXT",
            isDarkMode = isDarkMode,
            onThemeToggle = onThemeToggle
        )

        HorizontalDivider(
            thickness = 0.6.dp,
            color = c.divider
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(275.dp)
                    .border(
                        width = 1.dp,
                        color = c.border,
                        shape = RoundedCornerShape(24.dp)
                    )
                    .padding(20.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(22.dp))
                            .background(c.surfaceAlt),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Upload,
                            contentDescription = "Upload",
                            tint = c.accentSecondary,
                            modifier = Modifier.size(36.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "Tap to upload a file",
                        color = c.textPrimary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "or drag and drop",
                        color = c.textMuted,
                        fontSize = 16.sp
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "Supports PDF, DOCX, TXT",
                        color = c.textMuted,
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            Button(
                onClick = { navController.navigate("loading") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(68.dp),
                shape = RoundedCornerShape(22.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = c.accent
                )
            ) {
                Text(
                    text = "Analyze File",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            Card(
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = c.surface),
                border = BorderStroke(1.dp, c.border),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "What we analyze",
                        color = c.textPrimary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    AnalyzeItem("•", c.accentSecondary, "Coherence and logical flow", isDarkMode)
                    Spacer(modifier = Modifier.height(14.dp))
                    AnalyzeItem("•", c.accentSecondary, "Cohesion and connectivity", isDarkMode)
                    Spacer(modifier = Modifier.height(14.dp))
                    AnalyzeItem("•", c.success, "Overall discourse quality", isDarkMode)
                }
            }
        }
    }
}

@Composable
fun LoadingScreen(
    navController: NavController,
    isDarkMode: Boolean,
    onThemeToggle: (Boolean) -> Unit
) {
    val c = discourseColors(isDarkMode)

    val infinite = rememberInfiniteTransition(label = "loading")

    val ring1Scale by infinite.animateFloat(
        initialValue = 1f,
        targetValue = 1.04f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "ring1"
    )

    val ring2Scale by infinite.animateFloat(
        initialValue = 1f,
        targetValue = 1.04f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, delayMillis = 500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "ring2"
    )

    val ring3Scale by infinite.animateFloat(
        initialValue = 1f,
        targetValue = 1.04f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, delayMillis = 1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "ring3"
    )

    val orbScale by infinite.animateFloat(
        initialValue = 1f,
        targetValue = 1.07f,
        animationSpec = infiniteRepeatable(
            animation = tween(2400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "orbScale"
    )

    val orbitAngle by infinite.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(2800, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "orbitAngle"
    )

    val scan1 by infinite.animateFloat(
        initialValue = 0.2f,
        targetValue = 0.8f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 3000
                0.2f at 0
                0.8f at 3000
            },
            repeatMode = RepeatMode.Restart
        ),
        label = "scan1"
    )

    val scan2 by infinite.animateFloat(
        initialValue = 0.2f,
        targetValue = 0.8f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 3000
                0.2f at 1000
                0.8f at 3000
            },
            repeatMode = RepeatMode.Restart
        ),
        label = "scan2"
    )

    val scan3 by infinite.animateFloat(
        initialValue = 0.2f,
        targetValue = 0.8f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 3000
                0.2f at 2000
                0.8f at 3000
            },
            repeatMode = RepeatMode.Restart
        ),
        label = "scan3"
    )

    var currentStep by remember { mutableStateOf(1) }

    val progressTarget = when (currentStep) {
        0 -> 0.18f
        1 -> 0.42f
        2 -> 0.68f
        else -> 0.91f
    }

    val animatedProgress by animateFloatAsState(
        targetValue = progressTarget,
        animationSpec = tween(700, easing = FastOutSlowInEasing),
        label = "progress"
    )

    LaunchedEffect(Unit) {
        val steps = listOf(1, 2, 3)
        for (step in steps) {
            currentStep = step
            delay(2200)
        }
        navController.navigate("results") {
            popUpTo("loading") { inclusive = true }
        }
    }

    val progressLabel = when (currentStep) {
        0 -> "Analysing paragraph 1 of 5"
        1 -> "Analysing paragraph 2 of 5"
        2 -> "Analysing paragraph 3 of 5"
        else -> "Almost done…"
    }

    val progressPct = when (currentStep) {
        0 -> "18%"
        1 -> "42%"
        2 -> "68%"
        else -> "91%"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(c.background)
    ) {
        TopHeader(
            navController = navController,
            title = "DiscourseLens",
            subtitle = "Loading analysis",
            isDarkMode = isDarkMode,
            onThemeToggle = onThemeToggle
        )

        HorizontalDivider(
            thickness = 0.6.dp,
            color = c.divider
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    ScanLine(yFraction = scan1, color = c.loadingRing)
                    ScanLine(yFraction = scan2, color = c.loadingRing)
                    ScanLine(yFraction = scan3, color = c.loadingRing)
                }

                Ring(size = 160.dp, scale = ring1Scale, color = c.loadingRing.copy(alpha = 0.80f))
                Ring(size = 230.dp, scale = ring2Scale, color = c.loadingRing.copy(alpha = 0.45f))
                Ring(size = 300.dp, scale = ring3Scale, color = c.loadingRing.copy(alpha = 0.22f))

                Box(
                    modifier = Modifier.size(130.dp),
                    contentAlignment = Alignment.Center
                ) {
                    OrbitingDot(
                        angleDeg = orbitAngle,
                        radius = 60.dp,
                        size = 9.dp,
                        color = c.accentSecondary
                    )
                    OrbitingDot(
                        angleDeg = orbitAngle + 180f,
                        radius = 60.dp,
                        size = 6.dp,
                        color = c.accent
                    )

                    Box(
                        modifier = Modifier
                            .size(90.dp)
                            .scale(orbScale)
                            .background(c.accent, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(Color.White.copy(alpha = 0.12f), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(20.dp)
                                    .background(Color.White, CircleShape)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Analysing your writing",
                color = c.textPrimary,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Running discourse checks across\nall paragraphs",
                color = c.textSecondary,
                fontSize = 14.sp,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(28.dp))

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                LoadingStep(
                    label = "Parsing structure",
                    state = if (currentStep > 0) StepState.Done else StepState.Active,
                    accent = c.accent,
                    waiting = false,
                    isDarkMode = isDarkMode
                )
                LoadingStep(
                    label = "Scoring coherence",
                    state = when {
                        currentStep > 1 -> StepState.Done
                        currentStep == 1 -> StepState.Active
                        else -> StepState.Waiting
                    },
                    accent = c.accent,
                    waiting = currentStep == 1,
                    isDarkMode = isDarkMode
                )
                LoadingStep(
                    label = "Evaluating cohesion",
                    state = when {
                        currentStep > 2 -> StepState.Done
                        currentStep == 2 -> StepState.Active
                        else -> StepState.Waiting
                    },
                    accent = c.accent,
                    waiting = currentStep == 2,
                    isDarkMode = isDarkMode
                )
                LoadingStep(
                    label = "Generating insights",
                    state = if (currentStep == 3) StepState.Active else StepState.Waiting,
                    accent = c.accent,
                    waiting = currentStep == 3,
                    isDarkMode = isDarkMode
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .background(c.progressTrack, RoundedCornerShape(20.dp))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(animatedProgress)
                            .height(4.dp)
                            .background(c.accent, RoundedCornerShape(20.dp))
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = progressLabel,
                        color = c.textSecondary,
                        fontSize = 11.sp
                    )
                    Text(
                        text = progressPct,
                        color = c.textSecondary,
                        fontSize = 11.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))
        }
    }
}

@Composable
fun ResultsScreen(
    navController: NavController,
    isDarkMode: Boolean,
    onThemeToggle: (Boolean) -> Unit
) {
    val c = discourseColors(isDarkMode)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(c.background)
    ) {
        TopHeader(
            navController = navController,
            title = "Analysis Results",
            subtitle = "Your writing metrics",
            isDarkMode = isDarkMode,
            onThemeToggle = onThemeToggle
        )

        HorizontalDivider(
            thickness = 0.6.dp,
            color = c.divider
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(c.background),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(22.dp)
        ) {
            item { DqiCard(isDarkMode = isDarkMode) }
            item { ParagraphScoresCard(isDarkMode = isDarkMode) }
            item {
                ParagraphAnalysisDropdown(
                    navController = navController,
                    isDarkMode = isDarkMode
                )
            }
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    StatCard(
                        modifier = Modifier.weight(1f),
                        title = "Avg Paragraph",
                        value = "80.4",
                        isDarkMode = isDarkMode
                    )
                    StatCard(
                        modifier = Modifier.weight(1f),
                        title = "Total Paragraphs",
                        value = "5",
                        isDarkMode = isDarkMode
                    )
                }
            }
            item { Spacer(modifier = Modifier.height(8.dp)) }
        }
    }
}

@Composable
fun ParagraphFeedbackScreen(
    navController: NavController,
    paragraphId: Int,
    isDarkMode: Boolean,
    onThemeToggle: (Boolean) -> Unit
) {
    val c = discourseColors(isDarkMode)

    val title = "Feedback - P$paragraphId"
    val insightCount = "1 insight"

    val score = when (paragraphId) {
        1 -> 88
        2 -> 75
        3 -> 92
        4 -> 62
        else -> 85
    }

    val heading = when (paragraphId) {
        1 -> "Excellent opening with clear topic introduction"
        2 -> "Good transition, but ethical concern point needs tighter focus"
        3 -> "Strong supporting example with effective continuation"
        4 -> "Main idea is valuable, but the development feels weaker"
        else -> "Strong conclusion with clear closing message"
    }

    val body = when (paragraphId) {
        1 -> "Your opening paragraph demonstrates strong coherence with a clear claim followed by supporting evidence. The logical flow from general statement to specific details is well-executed."
        2 -> "This paragraph introduces an important contrast, but the supporting idea can be connected more directly to the main theme. Sharper internal transitions would improve clarity."
        3 -> "This section maintains topic consistency and adds meaningful evidence. The progression from the prior paragraph is smooth and easy to follow."
        4 -> "The paragraph presents an important idea, but its structure is less stable than the others. Stronger sentence linking and a clearer final point would improve cohesion."
        else -> "The conclusion effectively summarizes the discussion and leaves a strong closing impression. It connects back to the central theme in a coherent way."
    }

    val example = when (paragraphId) {
        1 -> "The structure 'AI has revolutionized, algorithms can now process...' effectively sets up your argument."
        2 -> "Try linking the ethical concern sentence more directly to the earlier claim to improve continuity."
        3 -> "The phrase introducing AI in education works well because it extends the discussion without breaking flow."
        4 -> "A stronger concluding sentence here could make the paragraph feel more complete and logically connected."
        else -> "Your final summary works well because it reinforces the essay’s central message in a concise way."
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(c.background)
    ) {
        TopHeader(
            navController = navController,
            title = title,
            subtitle = insightCount,
            isDarkMode = isDarkMode,
            onThemeToggle = onThemeToggle
        )

        HorizontalDivider(
            thickness = 0.6.dp,
            color = c.divider
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(22.dp)
        ) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = c.accentSoft
                    ),
                    border = BorderStroke(1.dp, c.accentSecondary)
                ) {
                    Row(
                        modifier = Modifier.padding(20.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Box(
                            modifier = Modifier
                                .size(46.dp)
                                .clip(RoundedCornerShape(14.dp))
                                .background(c.accentSecondary),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Lightbulb,
                                contentDescription = "Insight",
                                tint = Color.White
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Column {
                            Text(
                                text = "Paragraph $paragraphId Analysis",
                                color = c.textPrimary,
                                fontSize = 17.sp,
                                fontWeight = FontWeight.SemiBold
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "Detailed feedback for paragraph $paragraphId. Review the suggestions below to improve this section.",
                                color = c.textSecondary,
                                fontSize = 14.sp,
                                lineHeight = 26.sp
                            )
                        }
                    }
                }
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = c.surface
                    ),
                    border = BorderStroke(1.dp, c.border)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.Top
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(42.dp)
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(c.success.copy(alpha = 0.16f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.CheckCircle,
                                    contentDescription = "Strength",
                                    tint = c.success
                                )
                            }

                            Spacer(modifier = Modifier.width(16.dp))

                            Column {
                                Text(
                                    text = "Strength",
                                    color = c.textMuted,
                                    fontSize = 14.sp
                                )

                                Spacer(modifier = Modifier.height(6.dp))

                                Text(
                                    text = "$heading (Score: $score)",
                                    color = c.textPrimary,
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    lineHeight = 30.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Text(
                            text = body,
                            color = c.textPrimary,
                            fontSize = 15.sp,
                            lineHeight = 34.sp
                        )

                        Spacer(modifier = Modifier.height(18.dp))

                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = c.surfaceAlt
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    text = "Example:",
                                    color = c.textMuted,
                                    fontSize = 14.sp
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = example,
                                    color = c.textPrimary,
                                    fontSize = 14.sp,
                                    lineHeight = 28.sp
                                )
                            }
                        }
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Button(
                        onClick = {
                            navController.navigate("second") {
                                popUpTo("home")
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(64.dp),
                        shape = RoundedCornerShape(18.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = c.accent
                        )
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {

                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Analyse New Text",
                                color = Color.White,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    OutlinedButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier
                            .weight(1f)
                            .height(64.dp),
                        shape = RoundedCornerShape(18.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = c.surface,
                            contentColor = c.textPrimary
                        ),
                        border = BorderStroke(1.dp, c.border)
                    ) {
                        Text(
                            text = "Back",
                            color = c.textPrimary,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(8.dp)) }
        }
    }
}

@Composable
fun TopHeader(
    navController: NavController,
    title: String,
    subtitle: String,
    isDarkMode: Boolean,
    onThemeToggle: (Boolean) -> Unit
) {
    val c = discourseColors(isDarkMode)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(start = 8.dp, end = 16.dp, top = 6.dp, bottom = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {
                if (!navController.popBackStack()) {
                    navController.navigate("home")
                }
            }
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = c.textPrimary
            )
        }

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                color = c.textPrimary,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = subtitle,
                color = c.textMuted,
                fontSize = 14.sp
            )
        }

        ThemeToggleChip(
            isDarkMode = isDarkMode,
            onThemeToggle = onThemeToggle
        )
    }
}

@Composable
fun AnalyzeItem(dot: String, dotColor: Color, text: String, isDarkMode: Boolean) {
    val c = discourseColors(isDarkMode)

    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = dot,
            color = dotColor,
            fontSize = 22.sp
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = text,
            color = c.textPrimary,
            fontSize = 16.sp
        )
    }
}

@Composable
fun DqiCard(isDarkMode: Boolean) {
    val c = discourseColors(isDarkMode)
    val targetScore = 82

    val animatedProgress by animateFloatAsState(
        targetValue = targetScore / 100f,
        animationSpec = tween(durationMillis = 1400, easing = FastOutSlowInEasing),
        label = "dqi_progress"
    )

    val animatedScore by animateIntAsState(
        targetValue = targetScore,
        animationSpec = tween(durationMillis = 1400, easing = FastOutSlowInEasing),
        label = "dqi_score"
    )

    val infiniteTransition = rememberInfiniteTransition(label = "glow")

    val pulse by infiniteTransition.animateFloat(
        initialValue = 0.92f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(
            containerColor = c.accentSoft
        ),
        border = BorderStroke(1.dp, c.accentSecondary)
    ) {
        Column(
            modifier = Modifier.padding(22.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "✦",
                    fontSize = 18.sp,
                    color = c.accentSecondary
                )

                Spacer(modifier = Modifier.width(10.dp))

                Column {
                    Text(
                        text = "Discourse Quality Index",
                        color = c.textPrimary,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Overall discourse performance",
                        color = c.textSecondary,
                        fontSize = 13.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.size(156.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .scale(pulse)
                            .alpha(0.16f)
                            .background(c.accentSecondary, CircleShape)
                    )

                    CircularProgressIndicator(
                        progress = { animatedProgress },
                        modifier = Modifier.fillMaxSize(),
                        strokeWidth = 10.dp,
                        color = c.accentSecondary,
                        trackColor = c.loadingRing
                    )

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "$animatedScore",
                            color = c.textPrimary,
                            fontSize = 34.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "DQI",
                            color = c.textSecondary,
                            fontSize = 13.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.width(22.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    MetricBar(
                        label = "Coherence",
                        value = 78,
                        progress = 0.78f,
                        accent = c.success,
                        isDarkMode = isDarkMode
                    )

                    Spacer(modifier = Modifier.height(22.dp))

                    MetricBar(
                        label = "Cohesion",
                        value = 85,
                        progress = 0.85f,
                        accent = Color(0xFF5A9CFF),
                        isDarkMode = isDarkMode
                    )
                }
            }
        }
    }
}

@Composable
fun MetricBar(
    label: String,
    value: Int,
    progress: Float,
    accent: Color,
    isDarkMode: Boolean
) {
    val c = discourseColors(isDarkMode)

    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 1200, easing = FastOutSlowInEasing),
        label = "${label}_progress"
    )

    val animatedValue by animateIntAsState(
        targetValue = value,
        animationSpec = tween(durationMillis = 1200, easing = FastOutSlowInEasing),
        label = "${label}_value"
    )

    val glowAlpha by animateFloatAsState(
        targetValue = if (value >= 80) 0.22f else 0.10f,
        animationSpec = tween(1000),
        label = "${label}_glow"
    )

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (label == "Coherence") "↗" else "🔗",
                color = accent,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = label,
                color = c.textPrimary,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = animatedValue.toString(),
                color = c.textPrimary,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Box {
            Box(
                modifier = Modifier
                    .fillMaxWidth(animatedProgress)
                    .height(8.dp)
                    .alpha(glowAlpha)
                    .clip(RoundedCornerShape(20.dp))
                    .background(accent)
            )

            LinearProgressIndicator(
                progress = { animatedProgress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(20.dp)),
                color = accent,
                trackColor = c.progressTrack
            )
        }
    }
}

@Composable
fun ParagraphScoresCard(isDarkMode: Boolean) {
    val c = discourseColors(isDarkMode)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = c.surface
        ),
        border = BorderStroke(1.dp, c.border)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Paragraph",
                        color = c.textPrimary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "Scores",
                        color = c.textPrimary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                LegendDot(c.success, "Strong", isDarkMode)
                Spacer(modifier = Modifier.width(14.dp))
                LegendDot(c.warning, "Medium", isDarkMode)
                Spacer(modifier = Modifier.width(14.dp))
                LegendDot(c.danger, "Weak", isDarkMode)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ParagraphScoreItem("88", "P1", c.success, isDarkMode)
                ParagraphScoreItem("75", "P2", c.warning, isDarkMode)
                ParagraphScoreItem("92", "P3", c.success, isDarkMode)
                ParagraphScoreItem("62", "P4", c.danger, isDarkMode)
                ParagraphScoreItem("85", "P5", c.success, isDarkMode)
            }
        }
    }
}

@Composable
fun LegendDot(color: Color, text: String, isDarkMode: Boolean) {
    val c = discourseColors(isDarkMode)

    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .background(color, shape = CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            color = c.textMuted,
            fontSize = 13.sp
        )
    }
}

@Composable
fun ParagraphScoreItem(score: String, label: String, color: Color, isDarkMode: Boolean) {
    val c = discourseColors(isDarkMode)
    val target = score.toIntOrNull() ?: 0
    val animatedScore by animateIntAsState(
        targetValue = target,
        animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
        label = "${label}_score"
    )

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(width = 62.dp, height = 64.dp)
                .background(
                    color = color.copy(alpha = 0.92f),
                    shape = RoundedCornerShape(16.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = animatedScore.toString(),
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = label,
            color = c.textMuted,
            fontSize = 15.sp
        )
    }
}

@Composable
fun ParagraphAnalysisDropdown(
    navController: NavController,
    isDarkMode: Boolean
) {
    val c = discourseColors(isDarkMode)
    var expanded by remember { mutableStateOf(true) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = c.surface
        ),
        border = BorderStroke(1.dp, c.border)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(animationSpec = tween(450, easing = FastOutSlowInEasing))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded }
                    .padding(horizontal = 20.dp, vertical = 22.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Paragraph Analysis",
                    color = c.textPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.weight(1f))

                AnimatedContent(targetState = expanded, label = "dropdown_arrow") { isExpanded ->
                    Text(
                        text = if (isExpanded) "⌃" else "⌄",
                        color = c.textMuted,
                        fontSize = 22.sp
                    )
                }
            }

            HorizontalDivider(
                thickness = 1.dp,
                color = c.border
            )

            AnimatedVisibility(
                visible = expanded,
                enter = fadeIn(animationSpec = tween(250)) + expandVertically(animationSpec = tween(350)),
                exit = fadeOut(animationSpec = tween(200)) + shrinkVertically(animationSpec = tween(300))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 18.dp, vertical = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    ParagraphPreviewCard(
                        paragraphNumber = 1,
                        previewText = "Artificial intelligence has revolutionized the way we approach problem-solvin...",
                        borderColor = Color(0xFF00A88A),
                        scoreLabel = "Strong",
                        scoreColor = c.success,
                        isDarkMode = isDarkMode,
                        onClick = { navController.navigate("feedback/1") }
                    )

                    ParagraphPreviewCard(
                        paragraphNumber = 2,
                        previewText = "However, the implementation of AI systems raises important ethical...",
                        borderColor = Color(0xFF8A6A00),
                        scoreLabel = "Medium",
                        scoreColor = c.warning,
                        isDarkMode = isDarkMode,
                        onClick = { navController.navigate("feedback/2") }
                    )

                    ParagraphPreviewCard(
                        paragraphNumber = 3,
                        previewText = "Furthermore, the integration of AI in education has shown promising resul...",
                        borderColor = Color(0xFF007F73),
                        scoreLabel = "Strong",
                        scoreColor = c.success,
                        isDarkMode = isDarkMode,
                        onClick = { navController.navigate("feedback/3") }
                    )

                    ParagraphPreviewCard(
                        paragraphNumber = 4,
                        previewText = "The future of AI depends on responsible development and...",
                        borderColor = Color(0xFF8A1F32),
                        scoreLabel = "Weak",
                        scoreColor = c.danger,
                        isDarkMode = isDarkMode,
                        onClick = { navController.navigate("feedback/4") }
                    )

                    ParagraphPreviewCard(
                        paragraphNumber = 5,
                        previewText = "In conclusion, artificial intelligence represents both tremendous...",
                        borderColor = Color(0xFF006F67),
                        scoreLabel = "Strong",
                        scoreColor = c.success,
                        isDarkMode = isDarkMode,
                        onClick = { navController.navigate("feedback/5") }
                    )
                }
            }
        }
    }
}

@Composable
fun ParagraphPreviewCard(
    paragraphNumber: Int,
    previewText: String,
    borderColor: Color,
    scoreLabel: String,
    scoreColor: Color,
    isDarkMode: Boolean,
    onClick: () -> Unit
) {
    val c = discourseColors(isDarkMode)
    val interactionSource = remember { MutableInteractionSource() }
    val pressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.985f else 1f,
        animationSpec = tween(140),
        label = "card_scale"
    )

    val glowAlpha by animateFloatAsState(
        targetValue = if (pressed) 0.18f else 0.08f,
        animationSpec = tween(180),
        label = "card_glow"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            ),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = c.surfaceAlt
        ),
        border = BorderStroke(1.dp, borderColor.copy(alpha = 0.85f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp, vertical = 18.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = borderColor.copy(alpha = glowAlpha),
                        shape = RoundedCornerShape(18.dp)
                    )
            ) {
                Column(
                    modifier = Modifier.padding(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Top
                    ) {
                        Column {
                            Text(
                                text = "Paragraph $paragraphNumber",
                                color = c.textMuted,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .background(scoreColor, CircleShape)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = scoreLabel,
                                    color = scoreColor,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        Text(
                            text = "›",
                            color = c.textMuted,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Light
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = previewText,
                        color = c.textPrimary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        lineHeight = 22.sp,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Composable
fun StatCard(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    isDarkMode: Boolean
) {
    val c = discourseColors(isDarkMode)

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = c.surface
        ),
        border = BorderStroke(1.dp, c.border)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = title,
                color = c.textMuted,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = value,
                color = c.textPrimary,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

enum class StepState {
    Waiting, Active, Done
}

@Composable
fun Ring(
    size: Dp,
    scale: Float,
    color: Color
) {
    Box(
        modifier = Modifier
            .size(size)
            .scale(scale)
            .border(
                width = 1.dp,
                color = color,
                shape = CircleShape
            )
    )
}

@Composable
fun ScanLine(
    yFraction: Float,
    color: Color
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .offset(y = (480 * yFraction).dp)
            .height(1.dp)
            .background(color.copy(alpha = 0.85f))
    )
}

@Composable
fun OrbitingDot(
    angleDeg: Float,
    radius: Dp,
    size: Dp,
    color: Color
) {
    val angleRad = Math.toRadians(angleDeg.toDouble())
    val x = (cos(angleRad) * radius.value).dp
    val y = (sin(angleRad) * radius.value).dp

    Box(
        modifier = Modifier
            .offset(x = x, y = y)
            .size(size)
            .background(color, CircleShape)
    )
}

@Composable
fun LoadingStep(
    label: String,
    state: StepState,
    accent: Color,
    waiting: Boolean,
    isDarkMode: Boolean
) {
    val c = discourseColors(isDarkMode)

    val rowAlpha = when (state) {
        StepState.Active -> 1f
        StepState.Done -> 0.65f
        StepState.Waiting -> 0.4f
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(rowAlpha)
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(28.dp)
                .clip(CircleShape)
                .background(
                    when (state) {
                        StepState.Active -> c.progressTrack
                        StepState.Done -> accent
                        StepState.Waiting -> c.surfaceAlt
                    }
                )
                .border(
                    1.dp,
                    when (state) {
                        StepState.Active -> accent
                        StepState.Done -> accent
                        StepState.Waiting -> c.loadingRing
                    },
                    CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            when (state) {
                StepState.Done -> Text("✓", color = Color.White, fontSize = 12.sp)
                StepState.Active -> Text("○", color = c.accentSecondary, fontSize = 10.sp)
                StepState.Waiting -> {}
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = label,
            color = c.textPrimary,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = when {
                state == StepState.Done -> "Done"
                waiting -> "..."
                else -> "Waiting"
            },
            color = when {
                state == StepState.Done -> c.textSecondary
                waiting -> c.accentSecondary
                else -> c.textMuted
            },
            fontSize = 11.sp
        )
    }
}