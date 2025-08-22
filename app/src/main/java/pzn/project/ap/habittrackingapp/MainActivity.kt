package pzn.project.ap.habittrackingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pzn.project.ap.habittrackingapp.ui.theme.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HabitTrackingAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HabitTrackingApp()
                }
            }
        }
    }
}

@Composable
fun HabitTrackingApp() {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Welcome) }
    
    when (currentScreen) {
        Screen.Welcome -> WelcomeScreen(
            onGetStarted = { currentScreen = Screen.Auth }
        )
        Screen.Auth -> AuthScreen(
            onBackToWelcome = { currentScreen = Screen.Welcome },
            onLoginSuccess = { currentScreen = Screen.Home }
        )
        Screen.Home -> HomeScreen(
            onLogout = { currentScreen = Screen.Welcome }
        )
    }
}

sealed class Screen {
    object Welcome : Screen()
    object Auth : Screen()
    object Home : Screen()
}

@Composable
fun WelcomeScreen(onGetStarted: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                        MaterialTheme.colorScheme.background
                    )
                )
            )
    ) {
        // Content with clean background
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // App Icon/Logo Placeholder
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primary,
                                MaterialTheme.colorScheme.secondary
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "✓",
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
            
            Spacer(modifier = Modifier.height(48.dp))
            
            // Main Heading
            Text(
                text = "Habit Tracking",
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Subtitle
            Text(
                text = "Transform your life, one habit at a time",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(64.dp))
            
            // Get Started Button
            Button(
                onClick = onGetStarted,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 8.dp,
                    pressedElevation = 4.dp
                )
            ) {
                Text(
                    text = "Get Started",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Secondary action
            TextButton(
                onClick = { /* TODO: Show app info or settings */ }
            ) {
                Text(
                    text = "Learn More",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
        
        // Bottom decorative element
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(200.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.05f),
                            MaterialTheme.colorScheme.background.copy(alpha = 0f)
                        )
                    )
                )
        )
    }
}

@Composable
fun AuthScreen(onBackToWelcome: () -> Unit, onLoginSuccess: () -> Unit) {
    var isLogin by remember { mutableStateOf(true) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header with back button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackToWelcome) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            
            Spacer(modifier = Modifier.weight(1f))
            
            Text(
                text = if (isLogin) "Login" else "Register",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            
            Spacer(modifier = Modifier.weight(1f))
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // App Icon
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.secondary
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "✓",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Form Fields
        if (isLogin) {
            LoginForm(onLoginSuccess = onLoginSuccess)
        } else {
            RegisterForm()
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Toggle between Login and Register
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = if (isLogin) "Don't have an account? " else "Already have an account? ",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
            )
            
            TextButton(
                onClick = { isLogin = !isLogin }
            ) {
                Text(
                    text = if (isLogin) "Register" else "Login",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun LoginForm(onLoginSuccess: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Email Field
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            singleLine = true,
            shape = RoundedCornerShape(12.dp)
        )
        
        // Password Field
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            shape = RoundedCornerShape(12.dp)
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Login Button
        Button(
            onClick = onLoginSuccess,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text(
                text = "Login",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold
            )
        }
        
        // Forgot Password
        TextButton(
            onClick = { /* TODO: Handle forgot password */ },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = "Forgot Password?",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun RegisterForm() {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Name Field
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Full Name") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            singleLine = true,
            shape = RoundedCornerShape(12.dp)
        )
        
        // Email Field
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            singleLine = true,
            shape = RoundedCornerShape(12.dp)
        )
        
        // Password Field
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            ),
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            shape = RoundedCornerShape(12.dp)
        )
        
        // Confirm Password Field
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            shape = RoundedCornerShape(12.dp)
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Register Button
        Button(
            onClick = { /* TODO: Handle registration */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text(
                text = "Create Account",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onLogout: () -> Unit) {
    var selectedTab by remember { mutableStateOf(0) }
    
    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFFFF8A65), // Light orange
                                Color(0xFFFF7043), // Medium orange
                                Color(0xFFFF5722)  // Deep orange
                            )
                        )
                    )
            ) {
                TopAppBar(
                    title = { }, // Empty title to maintain structure
                    navigationIcon = {
                        IconButton(onClick = { /* TODO: Open menu */ }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menu",
                                tint = Color.White
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { /* TODO: Show notifications */ }) {
                            Icon(
                                imageVector = Icons.Default.Notifications,
                                contentDescription = "Notifications",
                                tint = Color.White
                            )
                        }
                        IconButton(onClick = { /* TODO: Show account options */ }) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Account",
                                tint = Color.White
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent
                    )
                )
            }
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.List, contentDescription = "Habits") },
                    label = { Text("Habits") },
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Info, contentDescription = "Progress") },
                    label = { Text("Progress") },
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
                    label = { Text("Settings") },
                    selected = selectedTab == 3,
                    onClick = { selectedTab = 3 }
                )
            }
        }
    ) { paddingValues ->
        // Main content area
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            when (selectedTab) {
                0 -> HomeTab()
                1 -> HabitsTab()
                2 -> ProgressTab()
                3 -> SettingsTab(onLogout = onLogout)
            }
        }
    }
}

@Composable
fun HomeTab() {
    var showAddHabitDialog by remember { mutableStateOf(false) }
    
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Background gradient
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFF8FAFC),
                            Color(0xFFE2E8F0),
                            Color(0xFFCBD5E1)
                        )
                    )
                )
        )
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Enhanced Header Section
            EnhancedHeaderSection()
            
            // Quick Stats Row
            QuickStatsRow()
            
            // Motivational Quote Card with gradient
            GradientMotivationalCard()
            
            // Today's Habits with modern design
            ModernHabitsSection()
            
            // Progress Overview with charts
            EnhancedProgressSection()
            
            // Weekly Calendar with modern design
            ModernWeeklyCalendar()
            
            // Streaks with achievement badges
            AchievementStreaksSection()
            
            // Personal Records with gradient cards
            GradientRecordsSection()
        }
        
        // Enhanced Floating Action Button
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp)
        ) {
            FloatingActionButton(
                onClick = { showAddHabitDialog = true },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 8.dp,
                    pressedElevation = 12.dp
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Habit",
                    modifier = Modifier.size(28.dp)
                )
            }
            
            // Subtle glow effect
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                                Color.Transparent
                            )
                        ),
                        shape = CircleShape
                    )
            )
        }
        
        // Enhanced Add Habit Dialog
        if (showAddHabitDialog) {
            EnhancedAddHabitDialog(
                onDismiss = { showAddHabitDialog = false },
                onHabitAdded = { showAddHabitDialog = false }
            )
        }
    }
}

@Composable
fun EnhancedHeaderSection() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF667EEA),
                            Color(0xFF764BA2)
                        )
                    )
                )
                .padding(24.dp)
        ) {
            Column {
                Text(
                    text = "Today, ${getCurrentDate()}",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "Let's make today count! 💪",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White.copy(alpha = 0.9f)
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Progress indicator
                LinearProgressIndicator(
                    progress = 0.6f,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    color = Color.White,
                    trackColor = Color.White.copy(alpha = 0.3f)
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "60% of daily goals completed",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }
        }
    }
}

@Composable
fun QuickStatsRow() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        QuickStatCard(
            value = "5",
            label = "Habits",
            icon = Icons.Default.List,
            gradient = Brush.linearGradient(
                colors = listOf(Color(0xFF4F46E5), Color(0xFF7C3AED))
            ),
            modifier = Modifier.weight(1f)
        )
        
        QuickStatCard(
            value = "3",
            label = "Completed",
            icon = Icons.Default.CheckCircle,
            gradient = Brush.linearGradient(
                colors = listOf(Color(0xFF059669), Color(0xFF10B981))
            ),
            modifier = Modifier.weight(1f)
        )
        
        QuickStatCard(
            value = "12",
            label = "Streak",
            icon = Icons.Default.Star,
            gradient = Brush.linearGradient(
                colors = listOf(Color(0xFFDC2626), Color(0xFFEF4444))
            ),
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun QuickStatCard(
    value: String,
    label: String,
    icon: ImageVector,
    gradient: Brush,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(gradient)
                .padding(20.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(28.dp),
                    tint = Color.White
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Text(
                    text = value,
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                )
                
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.9f)
                )
            }
        }
    }
}

@Composable
fun GradientMotivationalCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFFF59E0B),
                            Color(0xFFF97316)
                        )
                    )
                )
                .padding(24.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    modifier = Modifier.size(36.dp),
                    tint = Color.White
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "Small steps every day lead to big changes.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Medium
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "— Daily Motivation",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.8f),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun ModernHabitsSection() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Today's Habits",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color(0xFF1F2937),
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                )
                
                Text(
                    text = "3/5 completed",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF6B7280)
                )
            }
            
            Spacer(modifier = Modifier.height(20.dp))
            
            // Enhanced habit items
            ModernHabitItem(
                name = "Drink Water",
                progress = "3/8 glasses",
                isCompleted = false,
                streak = 7,
                color = Color(0xFF3B82F6)
            )
            
            ModernHabitItem(
                name = "Morning Walk",
                progress = "30/30 minutes",
                isCompleted = true,
                streak = 12,
                color = Color(0xFF10B981)
            )
            
            ModernHabitItem(
                name = "Read Books",
                progress = "15/30 minutes",
                isCompleted = false,
                streak = 5,
                color = Color(0xFF8B5CF6)
            )
            
            ModernHabitItem(
                name = "Meditation",
                progress = "10/10 minutes",
                isCompleted = true,
                streak = 8,
                color = Color(0xFFF59E0B)
            )
            
            ModernHabitItem(
                name = "Exercise",
                progress = "0/45 minutes",
                isCompleted = false,
                streak = 3,
                color = Color(0xFFEF4444)
            )
        }
    }
}

@Composable
fun ModernHabitItem(
    name: String,
    progress: String,
    isCompleted: Boolean,
    streak: Int,
    color: Color
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isCompleted) color.copy(alpha = 0.1f) else Color(0xFFF9FAFB)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Enhanced checkbox
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(
                        color = if (isCompleted) color else Color(0xFFE5E7EB),
                        shape = RoundedCornerShape(6.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (isCompleted) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = Color.White
                    )
                }
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // Habit details
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color(0xFF1F2937),
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Medium
                )
                
                Text(
                    text = progress,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF6B7280)
                )
            }
            
            // Enhanced streak indicator
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = color.copy(alpha = 0.1f)
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = color
                    )
                    
                    Spacer(modifier = Modifier.width(4.dp))
                    
                    Text(
                        text = "$streak",
                        style = MaterialTheme.typography.bodySmall,
                        color = color,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun EnhancedProgressSection() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            Text(
                text = "Progress Overview",
                style = MaterialTheme.typography.titleLarge,
                color = Color(0xFF1F2937),
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(20.dp))
            
            // Progress bars
            ProgressBarItem("Habits Completed", 0.6f, Color(0xFF10B981))
            ProgressBarItem("Weekly Goal", 0.8f, Color(0xFF3B82F6))
            ProgressBarItem("Monthly Streak", 0.9f, Color(0xFF8B5CF6))
        }
    }
}

@Composable
fun ProgressBarItem(label: String, progress: Float, color: Color) {
    Column(
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF374151)
            )
            
            Text(
                text = "${(progress * 100).toInt()}%",
                style = MaterialTheme.typography.bodyMedium,
                color = color,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            )
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = color,
            trackColor = color.copy(alpha = 0.2f)
        )
    }
}

@Composable
fun ModernWeeklyCalendar() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            Text(
                text = "This Week",
                style = MaterialTheme.typography.titleLarge,
                color = Color(0xFF1F2937),
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(20.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                listOf("M", "T", "W", "T", "F", "S", "S").forEachIndexed { index, day ->
                    ModernCalendarDay(
                        day = day,
                        isCompleted = index < 5,
                        isToday = index == 2
                    )
                }
            }
        }
    }
}

@Composable
fun ModernCalendarDay(
    day: String,
    isCompleted: Boolean,
    isToday: Boolean
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = day,
            style = MaterialTheme.typography.bodySmall,
            color = Color(0xFF6B7280),
            fontWeight = androidx.compose.ui.text.font.FontWeight.Medium
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(
                    color = when {
                        isToday -> Color(0xFF3B82F6)
                        isCompleted -> Color(0xFF10B981)
                        else -> Color(0xFFF3F4F6)
                    },
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            if (isCompleted || isToday) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
fun AchievementStreaksSection() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            Text(
                text = "🔥 Achievement Streaks",
                style = MaterialTheme.typography.titleLarge,
                color = Color(0xFF1F2937),
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(20.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ModernStreakItem("Morning Walk", 12, Color(0xFF10B981))
                ModernStreakItem("Meditation", 8, Color(0xFF8B5CF6))
                ModernStreakItem("Reading", 5, Color(0xFFF59E0B))
            }
        }
    }
}

@Composable
fun ModernStreakItem(name: String, days: Int, color: Color) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(
                    color = color.copy(alpha = 0.1f),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = color
            )
        }
        
        Spacer(modifier = Modifier.height(12.dp))
        
        Text(
            text = "$days days",
            style = MaterialTheme.typography.titleMedium,
            color = color,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
        )
        
        Text(
            text = name,
            style = MaterialTheme.typography.bodySmall,
            color = Color(0xFF6B7280),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun GradientRecordsSection() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            Text(
                text = "🏆 Personal Records",
                style = MaterialTheme.typography.titleLarge,
                color = Color(0xFF1F2937),
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(20.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    GradientRecordItem("Best Streak", "15 days", Color(0xFFDC2626))
                }
                Box(modifier = Modifier.weight(1f)) {
                    GradientRecordItem("This Month", "85%", Color(0xFF059669))
                }
                Box(modifier = Modifier.weight(1f)) {
                    GradientRecordItem("Total Habits", "12", Color(0xFF7C3AED))
                }
            }
        }
    }
}

@Composable
fun GradientRecordItem(label: String, value: String, color: Color) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            color.copy(alpha = 0.1f),
                            color.copy(alpha = 0.05f)
                        )
                    )
                )
                .padding(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    modifier = Modifier.size(28.dp),
                    tint = color
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = value,
                    style = MaterialTheme.typography.titleMedium,
                    color = color,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                )
                
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF6B7280),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun EnhancedAddHabitDialog(onDismiss: () -> Unit, onHabitAdded: () -> Unit) {
    var habitName by remember { mutableStateOf("") }
    var habitGoal by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Health") }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                "Add New Habit",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = habitName,
                    onValueChange = { habitName = it },
                    label = { Text("Habit Name") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
                
                OutlinedTextField(
                    value = habitGoal,
                    onValueChange = { habitGoal = it },
                    label = { Text("Daily Goal (e.g., 8 glasses, 30 minutes)") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
                
                // Category selector
                Text(
                    text = "Category",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF374151)
                )
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf("Health", "Fitness", "Learning", "Mindfulness").forEach { category ->
                        FilterChip(
                            selected = selectedCategory == category,
                            onClick = { selectedCategory = category },
                            label = { Text(category) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primary,
                                selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                            )
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (habitName.isNotBlank()) {
                        // TODO: Save habit to database
                        onHabitAdded()
                    }
                },
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Add Habit")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Cancel")
            }
        },
        containerColor = Color.White,
        shape = RoundedCornerShape(20.dp)
    )
}

// Helper function to get current date
fun getCurrentDate(): String {
    val dateFormat = java.text.SimpleDateFormat("MMM dd, yyyy", java.util.Locale.getDefault())
    return dateFormat.format(java.util.Date())
}

@Composable
fun HabitsTab() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            text = "Your Habits",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "No habits added yet. Start by adding your first habit!",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
        )
    }
}

@Composable
fun ProgressTab() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            text = "Progress",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "Track your habit progress here",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
        )
    }
}

@Composable
fun SettingsTab(onLogout: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            text = "Settings",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Button(
            onClick = onLogout,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error
            )
        ) {
            Icon(
                imageVector = Icons.Default.ExitToApp,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Logout")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    HabitTrackingAppTheme {
        WelcomeScreen(onGetStarted = {})
    }
}

@Preview(showBackground = true)
@Composable
fun AuthScreenPreview() {
    HabitTrackingAppTheme {
        AuthScreen(onBackToWelcome = {}, onLoginSuccess = {})
    }
}