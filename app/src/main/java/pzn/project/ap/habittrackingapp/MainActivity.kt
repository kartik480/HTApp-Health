package pzn.project.ap.habittrackingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.zIndex
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import pzn.project.ap.habittrackingapp.ui.theme.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items

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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun HabitTrackingApp() {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Welcome) }
    var isLogin by remember { mutableStateOf(false) }
    
    var userProfile by remember { 
        mutableStateOf(
            UserProfile(
                username = "",
                currentStreak = 0,
                longestStreak = 0,
                totalHabits = 0,
                completedHabits = 0
            )
        ) 
    }
    
    var aiCoach by remember { 
        mutableStateOf(
            AIHabitCoach(
                insights = emptyList(),
                predictions = emptyList(),
                recommendations = emptyList(),
                moodCorrelations = emptyList()
            )
        ) 
    }
    
    var gameProgress by remember { 
        mutableStateOf(
            GameProgress(
                level = 1,
                experience = 0,
                experienceToNextLevel = 100,
                coins = 0,
                achievements = emptyList(),
                virtualCharacter = VirtualCharacter(
                    name = "",
                    pets = emptyList()
                )
            )
        ) 
    }
    
    var socialChallenges by remember { 
        mutableStateOf(
            SocialChallenges(
                activeChallenges = emptyList(),
                friends = emptyList(),
                groupChallenges = emptyList()
            )
        ) 
    }

    when (currentScreen) {
        Screen.Welcome -> {
            WelcomeScreen(
                onGetStarted = { currentScreen = Screen.Auth }
            )
        }
        Screen.Auth -> {
            AuthScreen(
                onBackToWelcome = { currentScreen = Screen.Welcome },
                onLoginSuccess = { 
                    isLogin = true
                    currentScreen = Screen.Home
                }
            )
        }
        Screen.Home -> {
            HomeScreen(
                onLogout = { 
                    currentScreen = Screen.Welcome
                    isLogin = false
                },
                userProfile = userProfile,
                aiCoach = aiCoach,
                gameProgress = gameProgress,
                socialChallenges = socialChallenges,
                onUpdateProfile = { userProfile = it },
                onUpdateGameProgress = { gameProgress = it },
                onUpdateSocialChallenges = { socialChallenges = it }
            )
        }
    }
}

sealed class Screen {
    object Welcome : Screen()
    object Auth : Screen()
    object Home : Screen()
}

// AI Habit Coach Data Classes
data class AIHabitCoach(
    val insights: List<AIInsight> = emptyList(),
    val predictions: List<HabitPrediction> = emptyList(),
    val recommendations: List<HabitRecommendation> = emptyList(),
    val moodCorrelations: List<MoodCorrelation> = emptyList()
)

data class AIInsight(
    val id: String,
    val title: String,
    val description: String,
    val type: InsightType,
    val confidence: Float,
    val timestamp: Long = System.currentTimeMillis()
)

enum class InsightType {
    SUCCESS_PATTERN, FAILURE_PATTERN, MOOD_CORRELATION, OPTIMIZATION_TIP
}

data class HabitPrediction(
    val habitId: String,
    val habitName: String,
    val failureProbability: Float,
    val riskFactors: List<String>,
    val preventionStrategies: List<String>
)

data class HabitRecommendation(
    val habitName: String,
    val description: String,
    val difficulty: Difficulty,
    val expectedImpact: Float,
    val bestTime: String
)

enum class Difficulty {
    EASY, MEDIUM, HARD, EXPERT
}

data class MoodCorrelation(
    val habitName: String,
    val moodImprovement: Float,
    val correlationStrength: Float,
    val dataPoints: Int
)

// Gamification Data Classes
data class GameProgress(
    val level: Int = 1,
    val experience: Int = 0,
    val experienceToNextLevel: Int = 100,
    val achievements: List<Achievement> = emptyList(),
    val virtualCharacter: VirtualCharacter = VirtualCharacter(),
    val coins: Int = 0,
    val streakMultiplier: Float = 1.0f
)

data class Achievement(
    val id: String,
    val name: String,
    val description: String,
    val icon: String,
    val isUnlocked: Boolean = false,
    val unlockedAt: Long? = null,
    val reward: Reward
)

data class Reward(
    val type: RewardType,
    val value: Int,
    val description: String
)

enum class RewardType {
    COINS, XP, THEME, AVATAR, SOUND_EFFECT
}

data class VirtualCharacter(
    val name: String = "Habit Hero",
    val avatar: String = "default_avatar",
    val accessories: List<String> = emptyList(),
    val pets: List<VirtualPet> = emptyList(),
    val currentAdventure: Adventure? = null
)

data class VirtualPet(
    val name: String,
    val type: PetType,
    val level: Int,
    val happiness: Float,
    val evolutionStage: Int
)

enum class PetType {
    DOG, CAT, BIRD, DRAGON, UNICORN
}

data class Adventure(
    val id: String,
    val name: String,
    val description: String,
    val requiredLevel: Int,
    val rewards: List<Reward>,
    val isCompleted: Boolean = false
)

// Social Challenges Data Classes
data class SocialChallenges(
    val activeChallenges: List<Challenge> = emptyList(),
    val friends: List<Friend> = emptyList(),
    val leaderboards: List<Leaderboard> = emptyList(),
    val groupChallenges: List<GroupChallenge> = emptyList()
)

data class Challenge(
    val id: String,
    val name: String,
    val description: String,
    val type: ChallengeType,
    val participants: List<String>,
    val startDate: Long,
    val endDate: Long,
    val rewards: List<Reward>,
    val leaderboard: List<ChallengeParticipant>
)

enum class ChallengeType {
    DAILY_STREAK, WEEKLY_GOAL, MONTHLY_CHALLENGE, FRIEND_VS_FRIEND
}

data class ChallengeParticipant(
    val userId: String,
    val username: String,
    val avatar: String,
    val score: Int,
    val rank: Int
)

data class Friend(
    val id: String,
    val username: String,
    val avatar: String,
    val level: Int,
    val isOnline: Boolean,
    val lastSeen: Long
)

data class Leaderboard(
    val id: String,
    val name: String,
    val type: LeaderboardType,
    val participants: List<LeaderboardEntry>,
    val timeFrame: TimeFrame
)

enum class LeaderboardType {
    GLOBAL, FRIENDS, CHALLENGE, CATEGORY
}

enum class TimeFrame {
    DAILY, WEEKLY, MONTHLY, ALL_TIME
}

data class LeaderboardEntry(
    val userId: String,
    val username: String,
    val avatar: String,
    val score: Int,
    val rank: Int,
    val change: Int = 0
)

data class GroupChallenge(
    val id: String,
    val name: String,
    val description: String,
    val members: List<String>,
    val goal: String,
    val progress: Float,
    val deadline: Long,
    val rewards: List<Reward>
)

// User Profile Data Class
data class UserProfile(
    val id: String = "user_001",
    val username: String = "HabitHero",
    val email: String = "user@example.com",
    val avatar: String = "default_avatar",
    val joinDate: Long = System.currentTimeMillis(),
    val totalHabits: Int = 0,
    val completedHabits: Int = 0,
    val currentStreak: Int = 0,
    val longestStreak: Int = 0,
    val moodHistory: List<MoodEntry> = emptyList()
)

data class MoodEntry(
    val timestamp: Long,
    val mood: Mood,
    val note: String = ""
)

enum class Mood {
    EXCELLENT, GREAT, GOOD, OKAY, POOR, TERRIBLE
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
                text = "Kultivate....!",
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Subtitle
            Text(
                text = "Cultivate your best self through mindful habits",
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
                    imageVector = Icons.Default.KeyboardArrowLeft,
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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun HomeScreen(
    onLogout: () -> Unit,
    userProfile: UserProfile,
    aiCoach: AIHabitCoach,
    gameProgress: GameProgress,
    socialChallenges: SocialChallenges,
    onUpdateProfile: (UserProfile) -> Unit,
    onUpdateGameProgress: (GameProgress) -> Unit,
    onUpdateSocialChallenges: (SocialChallenges) -> Unit
) {
    var selectedTab by remember { mutableStateOf(0) }
    var showSideMenu by remember { mutableStateOf(false) }
    var showCalendarPanel by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf(java.time.LocalDate.now()) }

    Box(modifier = Modifier.fillMaxSize()) {
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
                            IconButton(onClick = { showSideMenu = true }) {
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
                NavigationBar(
                    containerColor = Color.White,
                    contentColor = MaterialTheme.colorScheme.primary
                ) {
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
                        icon = { Icon(Icons.Default.Star, contentDescription = "AI Coach") },
                        label = { Text("AI Coach") },
                        selected = selectedTab == 2,
                        onClick = { selectedTab = 2 }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Person, contentDescription = "Social") },
                        label = { Text("Social") },
                        selected = selectedTab == 3,
                        onClick = { selectedTab = 3 }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
                        label = { Text("Settings") },
                        selected = selectedTab == 4,
                        onClick = { selectedTab = 4 }
                    )
                }
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                AnimatedContent(
                    targetState = selectedTab,
                    transitionSpec = {
                        slideInHorizontally(
                            animationSpec = tween(300, easing = FastOutSlowInEasing),
                            initialOffsetX = { fullWidth -> fullWidth }
                        ) + fadeIn(
                            animationSpec = tween(300)
                        ) with slideOutHorizontally(
                            animationSpec = tween(300, easing = FastOutSlowInEasing),
                            targetOffsetX = { fullWidth -> -fullWidth }
                        ) + fadeOut(
                            animationSpec = tween(300)
                        )
                    }
                ) { targetTab ->
                    when (targetTab) {
                        0 -> HomeTab()
                        1 -> HabitsTab()
                        2 -> AICoachTab(aiCoach = aiCoach, gameProgress = gameProgress)
                        3 -> SocialTab(socialChallenges = socialChallenges, userProfile = userProfile)
                        4 -> SettingsTab(onLogout)
                    }
                }
            }
        }

        // Side Menu Panel
        AnimatedVisibility(
            visible = showSideMenu,
            enter = slideInHorizontally(
                animationSpec = tween(400, easing = FastOutSlowInEasing),
                initialOffsetX = { -it }
            ) + fadeIn(
                animationSpec = tween(400)
            ),
            exit = slideOutHorizontally(
                animationSpec = tween(400, easing = FastOutSlowInEasing),
                targetOffsetX = { -it }
            ) + fadeOut(
                animationSpec = tween(400)
            )
        ) {
            SideMenuPanel(
                onDismiss = { showSideMenu = false },
                onLogout = onLogout,
                onCalendarClick = { 
                    showCalendarPanel = true
                    showSideMenu = false
                }
            )
        }
        
        // Calendar Panel
        AnimatedVisibility(
            visible = showCalendarPanel,
            enter = slideInVertically(
                animationSpec = tween(400, easing = FastOutSlowInEasing),
                initialOffsetY = { -it }
            ) + fadeIn(
                animationSpec = tween(400)
            ),
            exit = slideOutVertically(
                animationSpec = tween(400, easing = FastOutSlowInEasing),
                targetOffsetY = { -it }
            ) + fadeOut(
                animationSpec = tween(400)
            )
        ) {
            CalendarPanel(
                selectedDate = selectedDate,
                onDateSelected = { selectedDate = it },
                onDismiss = { showCalendarPanel = false },
                userProfile = userProfile,
                gameProgress = gameProgress
            )
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

// Helper function to get current date
fun getCurrentDate(): String {
    val dateFormat = java.text.SimpleDateFormat("MMM dd, yyyy", java.util.Locale.getDefault())
    return dateFormat.format(java.util.Date())
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
                    progress = 0.0f,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    color = Color.White,
                    trackColor = Color.White.copy(alpha = 0.3f)
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "0% of daily goals completed",
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
        AnimatedVisibility(
            visible = true,
            enter = fadeIn(
                animationSpec = tween(800, delayMillis = 100)
            ) + slideInVertically(
                animationSpec = tween(800, delayMillis = 100),
                initialOffsetY = { it }
            ),
            modifier = Modifier.weight(1f)
        ) {
            QuickStatCard(
                value = "0",
                label = "Habits",
                icon = Icons.Default.List,
                gradient = Brush.linearGradient(
                    colors = listOf(Color(0xFF87CEEB), Color(0xFF4682B4))
                )
            )
        }
        
        AnimatedVisibility(
            visible = true,
            enter = fadeIn(
                animationSpec = tween(800, delayMillis = 200)
            ) + slideInVertically(
                animationSpec = tween(800, delayMillis = 200),
                initialOffsetY = { it }
            ),
            modifier = Modifier.weight(1f)
        ) {
            QuickStatCard(
                value = "0",
                label = "Completed",
                icon = Icons.Default.CheckCircle,
                gradient = Brush.linearGradient(
                    colors = listOf(Color(0xFF87CEEB), Color(0xFF4682B4))
                )
            )
        }
        
        AnimatedVisibility(
            visible = true,
            enter = fadeIn(
                animationSpec = tween(800, delayMillis = 300)
            ) + slideInVertically(
                animationSpec = tween(800, delayMillis = 300),
                initialOffsetY = { it }
            ),
            modifier = Modifier.weight(1f)
        ) {
            QuickStatCard(
                                    value = "0",
                label = "Streak",
                icon = Icons.Default.Star,
                gradient = Brush.linearGradient(
                    colors = listOf(Color(0xFF87CEEB), Color(0xFF4682B4))
                )
            )
        }
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
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
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
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = label,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.9f),
                    textAlign = TextAlign.Center
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
                    text = "0/0 completed",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF6B7280)
                )
            }
            
            Spacer(modifier = Modifier.height(20.dp))
            
            // Sample habit items - will be populated from backend
            ModernHabitItem(
                name = "Add your first habit",
                progress = "0/0 minutes",
                isCompleted = false,
                streak = 0,
                color = Color(0xFF8B5CF6)
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
            ProgressBarItem("Habits Completed", 0.0f, Color(0xFF10B981))
            ProgressBarItem("Weekly Goal", 0.0f, Color(0xFF3B82F6))
            ProgressBarItem("Monthly Streak", 0.0f, Color(0xFF8B5CF6))
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
                // Sample streak items - will be populated from backend
                ModernStreakItem("Start building streaks", 0, Color(0xFF10B981))
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
                    GradientRecordItem("Best Streak", "0 days", Color(0xFFDC2626))
                }
                Box(modifier = Modifier.weight(1f)) {
                    GradientRecordItem("This Month", "0%", Color(0xFF059669))
                }
                Box(modifier = Modifier.weight(1f)) {
                    GradientRecordItem("Total Habits", "0", Color(0xFF7C3AED))
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

@Composable
fun SideMenuPanel(
    onDismiss: () -> Unit,
    onLogout: () -> Unit,
    onCalendarClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Animated Backdrop
        AnimatedVisibility(
            visible = true,
            enter = fadeIn(
                animationSpec = tween(300)
            ),
            exit = fadeOut(
                animationSpec = tween(300)
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .clickable { onDismiss() }
            )
        }
        
        // Side Menu Content with staggered animations
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(300.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF1E293B),
                            Color(0xFF334155),
                            Color(0xFF475569)
                        )
                    )
                )
                .padding(24.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // Header Section with fade-in animation
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn(
                        animationSpec = tween(600, delayMillis = 100)
                    ) + slideInVertically(
                        animationSpec = tween(600, delayMillis = 100),
                        initialOffsetY = { -it / 2 }
                    )
                ) {
                    SideMenuHeader()
                }
                
                Spacer(modifier = Modifier.height(32.dp))
                
                // Menu Items with staggered animations
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn(
                        animationSpec = tween(600, delayMillis = 200)
                    ) + slideInVertically(
                        animationSpec = tween(600, delayMillis = 200),
                        initialOffsetY = { -it / 2 }
                    )
                ) {
                    Column {
                        SideMenuItem(
                            icon = Icons.Default.Home,
                            title = "Dashboard",
                            subtitle = "View your progress",
                            onClick = { /* TODO: Navigate to dashboard */ }
                        )
                        
                        SideMenuItem(
                            icon = Icons.Default.List,
                            title = "My Habits",
                            subtitle = "Manage your habits",
                            onClick = { /* TODO: Navigate to habits */ }
                        )
                        
                        SideMenuItem(
                            icon = Icons.Default.Star,
                            title = "Achievements",
                            subtitle = "View your badges",
                            onClick = { /* TODO: Navigate to achievements */ }
                        )
                        
                        SideMenuItem(
                            icon = Icons.Default.Info,
                            title = "Analytics",
                            subtitle = "Detailed insights",
                            onClick = { /* TODO: Navigate to analytics */ }
                        )
                        
                        SideMenuItem(
                            icon = Icons.Default.DateRange,
                            title = "Calendar",
                            subtitle = "Track your schedule",
                            onClick = onCalendarClick
                        )
                        
                        SideMenuItem(
                            icon = Icons.Default.Notifications,
                            title = "Reminders",
                            subtitle = "Manage notifications",
                            onClick = { /* TODO: Navigate to reminders */ }
                        )
                    }
                }
                
                Spacer(modifier = Modifier.weight(1f))
                
                // Bottom Section with fade-in animation
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn(
                        animationSpec = tween(600, delayMillis = 400)
                    ) + slideInVertically(
                        animationSpec = tween(600, delayMillis = 400),
                        initialOffsetY = { it / 2 }
                    )
                ) {
                    SideMenuBottomSection(onLogout)
                }
            }
        }
    }
}

@Composable
fun SideMenuHeader() {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        // Profile Section
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFFFF8A65),
                                Color(0xFFFF5722)
                            )
                        ),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                    tint = Color.White
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column {
                Text(
                    text = "John Doe",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                )
                
                Text(
                    text = "Habit Tracker Pro",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Stats Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            SideMenuStat("12", "Habits")
            SideMenuStat("85%", "Success")
            SideMenuStat("15", "Streak")
        }
    }
}

@Composable
fun SideMenuStat(value: String, label: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge,
            color = Color.White,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
        )
        
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = Color.White.copy(alpha = 0.7f)
        )
    }
}

@Composable
fun SideMenuItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = Color.White.copy(alpha = 0.9f)
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Medium
                )
                
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.7f)
                )
            }
            
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = Color.White.copy(alpha = 0.6f)
            )
        }
    }
}

@Composable
fun SideMenuBottomSection(onLogout: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Divider(
            color = Color.White.copy(alpha = 0.2f),
            thickness = 1.dp
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Settings and Help
        SideMenuItem(
            icon = Icons.Default.Settings,
            title = "Settings",
            subtitle = "App preferences",
            onClick = { /* TODO: Navigate to settings */ }
        )
        
        SideMenuItem(
            icon = Icons.Default.Info,
            title = "Help & Support",
            subtitle = "Get assistance",
            onClick = { /* TODO: Navigate to help */ }
        )
        
        SideMenuItem(
            icon = Icons.Default.Info,
            title = "About",
            subtitle = "App information",
            onClick = { /* TODO: Navigate to about */ }
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Logout Button
        Button(
            onClick = onLogout,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFEF4444)
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ExitToApp,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            
            Spacer(modifier = Modifier.width(8.dp))
            
            Text(
                text = "Logout",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Medium
            )
        }
    }
}

@Composable
fun WelcomeScreenPreview() {
    HabitTrackingAppTheme {
        WelcomeScreen(onGetStarted = {})
    }
}

@Composable
fun AuthScreenPreview() {
    HabitTrackingAppTheme {
        AuthScreen(onBackToWelcome = {}, onLoginSuccess = {})
    }
}

@Composable
fun AICoachTab(aiCoach: AIHabitCoach, gameProgress: GameProgress) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF667EEA),
                        Color(0xFF764BA2)
                    )
                )
            )
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // AI Coach Header with Level Display
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Level and XP Display
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Level ${gameProgress.level}",
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                        )
                        Text(
                            text = "AI Habit Coach",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                    }
                    
                    // XP Progress
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "${gameProgress.experience}/${gameProgress.experienceToNextLevel} XP",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        LinearProgressIndicator(
                            progress = gameProgress.experience.toFloat() / gameProgress.experienceToNextLevel,
                            modifier = Modifier.width(80.dp),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Virtual Character Display
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFF0F8FF)
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .background(
                                    color = MaterialTheme.colorScheme.primary,
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                        
                        Spacer(modifier = Modifier.width(16.dp))
                        
                        Column {
                            Text(
                                text = gameProgress.virtualCharacter.name,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                            )
                            Text(
                                text = "Coins: ${gameProgress.coins}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFFFFD700)
                            )
                        }
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // AI Insights Section
        if (aiCoach.insights.isNotEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "🤖 AI Insights",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    aiCoach.insights.take(3).forEach { insight ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = when (insight.type) {
                                    InsightType.SUCCESS_PATTERN -> Color(0xFFE8F5E8)
                                    InsightType.FAILURE_PATTERN -> Color(0xFFFFF3E0)
                                    InsightType.MOOD_CORRELATION -> Color(0xFFE3F2FD)
                                    InsightType.OPTIMIZATION_TIP -> Color(0xFFF3E5F5)
                                }
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    text = insight.title,
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                                )
                                Text(
                                    text = insight.description,
                                    style = MaterialTheme.typography.bodySmall,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                                Text(
                                    text = "Confidence: ${(insight.confidence * 100).toInt()}%",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Habit Predictions
        if (aiCoach.predictions.isNotEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "🔮 Failure Predictions",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    aiCoach.predictions.take(2).forEach { prediction ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFFFF3E0)
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    text = prediction.habitName,
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                                )
                                Text(
                                    text = "Risk: ${(prediction.failureProbability * 100).toInt()}%",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color(0xFFE65100)
                                )
                                Text(
                                    text = "Prevention: ${prediction.preventionStrategies.firstOrNull() ?: "Stay consistent!"}",
                                    style = MaterialTheme.typography.bodySmall,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Achievements Section
        if (gameProgress.achievements.isNotEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "🏆 Recent Achievements",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(gameProgress.achievements.filter { it.isUnlocked }.take(5)) { achievement ->
                            Card(
                                modifier = Modifier.width(120.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color(0xFFE8F5E8)
                                )
                            ) {
                                Column(
                                    modifier = Modifier.padding(12.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = null,
                                        tint = Color(0xFFFFD700),
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Text(
                                        text = achievement.name,
                                        style = MaterialTheme.typography.bodySmall,
                                        textAlign = TextAlign.Center,
                                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                                    )
                                    Text(
                                        text = achievement.reward.description,
                                        style = MaterialTheme.typography.bodySmall,
                                        textAlign = TextAlign.Center,
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SocialTab(socialChallenges: SocialChallenges, userProfile: UserProfile) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF4CAF50),
                        Color(0xFF2E7D32)
                    )
                )
            )
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Social Header
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "🌟 Social Hub",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                )
                Text(
                    text = "Connect, Compete, Conquer!",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Quick Stats
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "${socialChallenges.friends.size}",
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                        )
                        Text(
                            text = "Friends",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "${socialChallenges.activeChallenges.size}",
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                        )
                        Text(
                            text = "Challenges",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "${userProfile.currentStreak}",
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                        )
                        Text(
                            text = "Day Streak",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Active Challenges
        if (socialChallenges.activeChallenges.isNotEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "🔥 Active Challenges",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    socialChallenges.activeChallenges.take(3).forEach { challenge ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFFFF3E0)
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = challenge.name,
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                                    )
                                    Text(
                                        text = "${challenge.participants.size} participants",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                    )
                                }
                                Text(
                                    text = challenge.description,
                                    style = MaterialTheme.typography.bodySmall,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                                Text(
                                    text = "Rewards: ${challenge.rewards.firstOrNull()?.description ?: "XP & Coins"}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFFFFD700),
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Friends Leaderboard
        if (socialChallenges.friends.isNotEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "👥 Friends Leaderboard",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    socialChallenges.friends.sortedByDescending { it.level }.take(5).forEachIndexed { index, friend ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 2.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = when (index) {
                                    0 -> Color(0xFFFFD700) // Gold
                                    1 -> Color(0xFFC0C0C0) // Silver
                                    2 -> Color(0xFFCD7F32) // Bronze
                                    else -> Color.White
                                }
                            )
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "#${index + 1}",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                                    modifier = Modifier.width(40.dp)
                                )
                                
                                Box(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .background(
                                            color = MaterialTheme.colorScheme.primary,
                                            shape = CircleShape
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Person,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                                
                                Spacer(modifier = Modifier.width(12.dp))
                                
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = friend.username,
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                                    )
                                    Text(
                                        text = "Level ${friend.level}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                                    )
                                }
                                
                                if (friend.isOnline) {
                                    Box(
                                        modifier = Modifier
                                            .size(12.dp)
                                            .background(
                                                color = Color.Green,
                                                shape = CircleShape
                                            )
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Group Challenges
        if (socialChallenges.groupChallenges.isNotEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "🚀 Group Challenges",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    socialChallenges.groupChallenges.take(2).forEach { groupChallenge ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFE3F2FD)
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Text(
                                    text = groupChallenge.name,
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                                )
                                Text(
                                    text = groupChallenge.description,
                                    style = MaterialTheme.typography.bodySmall,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                                
                                Spacer(modifier = Modifier.height(8.dp))
                                
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Progress: ${(groupChallenge.progress * 100).toInt()}%",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    Text(
                                        text = "${groupChallenge.members.size} members",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                                
                                LinearProgressIndicator(
                                    progress = groupChallenge.progress,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 8.dp),
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarPanel(
    selectedDate: java.time.LocalDate,
    onDateSelected: (java.time.LocalDate) -> Unit,
    onDismiss: () -> Unit,
    userProfile: UserProfile,
    gameProgress: GameProgress
) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Backdrop
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
                .clickable { onDismiss() }
        )
        
        // Calendar Content with Navigation Bars
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
                        title = { },
                        navigationIcon = {
                            IconButton(onClick = onDismiss) {
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowLeft,
                                    contentDescription = "Back",
                                    tint = Color.White
                                )
                            }
                        },
                        actions = {
                            Text(
                                text = "📅 Habit Calendar",
                                style = MaterialTheme.typography.titleLarge,
                                color = Color.White,
                                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                            )
                            
                            Spacer(modifier = Modifier.width(16.dp))
                            
                            IconButton(onClick = { /* TODO: Add new habit */ }) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Add Habit",
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
                NavigationBar(
                    containerColor = Color.White,
                    tonalElevation = 8.dp
                ) {
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                        label = { Text("Home") },
                        selected = false,
                        onClick = onDismiss
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.List, contentDescription = "Habits") },
                        label = { Text("Habits") },
                        selected = false,
                        onClick = onDismiss
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Star, contentDescription = "Progress") },
                        selected = true,
                        onClick = { },
                        label = { Text("Calendar") }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
                        label = { Text("Settings") },
                        selected = false,
                        onClick = onDismiss
                    )
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF667EEA),
                                Color(0xFF764BA2),
                                Color(0xFFF093FB)
                            )
                        )
                    )
                    .verticalScroll(rememberScrollState())
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Calendar Grid
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    // Month Navigation
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = {
                                onDateSelected(selectedDate.minusMonths(1))
                            }
                        ) {
                            Icon(Icons.Default.KeyboardArrowLeft, contentDescription = "Previous Month")
                        }
                        
                        Text(
                            text = selectedDate.format(java.time.format.DateTimeFormatter.ofPattern("MMMM yyyy")),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                        )
                        
                        IconButton(
                            onClick = {
                                onDateSelected(selectedDate.plusMonths(1))
                            }
                        ) {
                            Icon(Icons.Default.KeyboardArrowRight, contentDescription = "Next Month")
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    // Day Headers
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat").forEach { day ->
                            Text(
                                text = day,
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(6.dp))
                    
                    // Calendar Days
                    val firstDayOfMonth = selectedDate.withDayOfMonth(1)
                    val lastDayOfMonth = selectedDate.withDayOfMonth(selectedDate.lengthOfMonth())
                    val firstDayOfWeek = firstDayOfMonth.dayOfWeek.value % 7
                    
                    var currentWeek by remember { mutableStateOf(0) }
                    
                    repeat(6) { week ->
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            repeat(7) { dayOfWeek ->
                                val dayOffset = week * 7 + dayOfWeek - firstDayOfWeek
                                val currentDate = if (dayOffset >= 0 && dayOffset < selectedDate.lengthOfMonth()) {
                                    firstDayOfMonth.plusDays(dayOffset.toLong())
                                } else null
                                
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .aspectRatio(1f)
                                        .padding(1.dp)
                                        .background(
                                            color = when {
                                                currentDate == null -> Color.Transparent
                                                currentDate == java.time.LocalDate.now() -> Color(0xFFE3F2FD)
                                                currentDate == selectedDate -> Color(0xFFFFD700)
                                                else -> Color.Transparent
                                            },
                                            shape = RoundedCornerShape(6.dp)
                                        )
                                        .clickable(
                                            enabled = currentDate != null
                                        ) {
                                            currentDate?.let { onDateSelected(it) }
                                        }
                                        .border(
                                            width = if (currentDate == selectedDate) 2.dp else 0.dp,
                                            color = if (currentDate == selectedDate) Color(0xFFFF6B35) else Color.Transparent,
                                            shape = RoundedCornerShape(6.dp)
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (currentDate != null) {
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            Text(
                                                text = currentDate.dayOfMonth.toString(),
                                                style = MaterialTheme.typography.bodyMedium,
                                                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                                                color = when {
                                                    currentDate == java.time.LocalDate.now() -> MaterialTheme.colorScheme.primary
                                                    currentDate == selectedDate -> Color(0xFFFF6B35)
                                                    else -> MaterialTheme.colorScheme.onSurface
                                                }
                                            )
                                            
                                            // Habit completion indicator
                                            if (currentDate == java.time.LocalDate.now()) {
                                                Box(
                                                    modifier = Modifier
                                                        .size(4.dp)
                                                        .background(
                                                            color = Color.Green,
                                                            shape = CircleShape
                                                        )
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Selected Date Details
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "📋 ${selectedDate.format(java.time.format.DateTimeFormatter.ofPattern("EEEE, MMMM d"))}",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Daily Stats
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "0",
                                style = MaterialTheme.typography.headlineSmall,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                            )
                            Text(
                                text = "Habits",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "0",
                                style = MaterialTheme.typography.headlineSmall,
                                color = Color.Green,
                                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                            )
                            Text(
                                text = "Completed",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "0%",
                                style = MaterialTheme.typography.headlineSmall,
                                color = Color(0xFFFF9800),
                                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                            )
                            Text(
                                text = "Success",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    // Mood Tracker
                    Text(
                        text = "😊 How are you feeling today?",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                    )
                    
                    Spacer(modifier = Modifier.height(6.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        listOf("😢", "😐", "🙂", "😊", "🤩").forEach { emoji ->
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .background(
                                        color = Color(0xFFF0F0F0),
                                        shape = CircleShape
                                    )
                                    .clickable { /* TODO: Set mood */ },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = emoji,
                                    style = MaterialTheme.typography.titleLarge
                                )
                            }
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    // Quick Actions
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            onClick = { /* TODO: Add quick habit */ },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Icon(Icons.Default.Add, contentDescription = null)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Quick Habit")
                        }
                        
                        Button(
                            onClick = { /* TODO: View analytics */ },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF4CAF50)
                            )
                        ) {
                            Icon(Icons.Default.Info, contentDescription = null)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Analytics")
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Weekly Progress
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "📊 This Week's Progress",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                    )
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    // Weekly chart
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        listOf(0, 0, 0, 0, 0, 0, 0).forEachIndexed { index, percentage ->
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Box(
                                    modifier = Modifier
                                        .width(28.dp)
                                        .height((percentage * 1.8).dp)
                                        .background(
                                            color = when {
                                                percentage >= 80 -> Color.Green
                                                percentage >= 60 -> Color(0xFFFF9800)
                                                else -> Color.Red
                                            },
                                            shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)
                                        )
                                )
                                Text(
                                    text = listOf("M", "T", "W", "T", "F", "S", "S")[index],
                                    style = MaterialTheme.typography.bodySmall,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
        }
    }
}