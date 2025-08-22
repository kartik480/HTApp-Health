# 🚀 Kultivate Backend Setup Guide

This guide will help you set up the complete backend API for the Kultivate habit tracking app, replacing all the sample/hardcoded data with a real database and API.

## 📋 What We've Built

### ✅ **Complete Backend API**
- **FastAPI Framework**: Modern, fast Python web framework
- **PostgreSQL Database**: Robust relational database
- **SQLAlchemy ORM**: Python database toolkit
- **JWT Authentication**: Secure user authentication
- **Comprehensive Models**: All data structures for the app
- **RESTful API**: Complete CRUD operations
- **Analytics Engine**: Performance tracking and insights

### 🏗️ **Database Models**
- **Users**: Authentication and profiles
- **Habits**: Habit definitions and settings
- **HabitLogs**: Daily completion tracking
- **Categories**: Habit organization
- **Streaks**: Consistency tracking
- **Achievements**: Gamification system
- **MoodEntries**: Wellness tracking
- **Goals**: Personal goal management
- **Notifications**: User alerts

### 🔐 **API Endpoints**
- **Authentication**: Register, Login, Token refresh
- **Users**: Profile management
- **Habits**: Full CRUD operations
- **Categories**: Organization system
- **Analytics**: Performance insights
- **Health Checks**: System monitoring

## 🛠️ Installation Steps

### 1. **Install Python Dependencies**
```bash
cd backend
pip install -r requirements.txt
```

### 2. **Set Up PostgreSQL Database**
```sql
-- Connect to PostgreSQL
psql -U postgres

-- Create database and user
CREATE DATABASE kultivate_db;
CREATE USER kultivate_user WITH PASSWORD 'your_secure_password';
GRANT ALL PRIVILEGES ON DATABASE kultivate_db TO kultivate_user;
\q
```

### 3. **Configure Environment Variables**
```bash
cd backend
cp env_example.txt .env
```

Edit `.env` file:
```env
DATABASE_URL=postgresql://kultivate_user:your_secure_password@localhost:5432/kultivate_db
SECRET_KEY=your-super-secret-key-here
DEBUG=True
ENVIRONMENT=development
```

### 4. **Run Database Migrations**
```bash
cd backend
alembic upgrade head
```

### 5. **Start the API Server**
```bash
cd backend
python run.py
```

## 🐳 Docker Setup (Alternative)

### **Quick Start with Docker**
```bash
cd backend
docker-compose up -d
```

This will start:
- **API Server**: http://localhost:8000
- **PostgreSQL**: localhost:5432
- **Redis**: localhost:6379

## 📱 Connecting Android App to Backend

### **Update Android App Configuration**
Replace the hardcoded data in your Android app with API calls:

```kotlin
// Example: Replace hardcoded habits with API call
class HabitRepository {
    private val apiService = RetrofitClient.apiService
    
    suspend fun getHabits(): List<Habit> {
        return apiService.getHabits("Bearer $token")
    }
    
    suspend fun createHabit(habit: HabitCreate): Habit {
        return apiService.createHabit("Bearer $token", habit)
    }
}
```

### **API Base URL**
```kotlin
object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:8000/api/v1/" // For Android Emulator
    // Use "http://localhost:8000/api/v1/" for physical device on same network
}
```

## 🧪 Testing the API

### **1. Health Check**
```bash
curl http://localhost:8000/api/v1/health
```

### **2. User Registration**
```bash
curl -X POST http://localhost:8000/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@example.com",
    "username": "testuser",
    "password": "testpassword123",
    "first_name": "Test",
    "last_name": "User"
  }'
```

### **3. User Login**
```bash
curl -X POST http://localhost:8000/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@example.com",
    "password": "testpassword123"
  }'
```

### **4. Create Habit (with token)**
```bash
curl -X POST http://localhost:8000/api/v1/habits/ \
  -H "Authorization: Bearer YOUR_TOKEN_HERE" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Morning Exercise",
    "description": "30 minutes of cardio",
    "frequency": "daily",
    "priority": "high"
  }'
```

## 📊 API Documentation

Once running, visit:
- **Interactive Docs**: http://localhost:8000/docs
- **ReDoc**: http://localhost:8000/redoc

## 🔄 Data Flow

### **Before (Hardcoded)**
```kotlin
// Old way - hardcoded data
val habits = listOf(
    Habit("Exercise", "Daily workout"),
    Habit("Read", "30 minutes reading")
)
```

### **After (API-Driven)**
```kotlin
// New way - real API data
val habits = habitRepository.getHabits()
val newHabit = habitRepository.createHabit(HabitCreate("Meditation", "Daily mindfulness"))
```

## 🚀 Benefits of the New Backend

### ✅ **Real Data Persistence**
- User accounts are saved
- Habits persist between app sessions
- Progress tracking is accurate
- Data syncs across devices

### ✅ **Scalability**
- Handle thousands of users
- Efficient database queries
- Caching for performance
- Easy to add new features

### ✅ **Security**
- Password encryption
- JWT token authentication
- User data isolation
- API rate limiting

### ✅ **Analytics**
- Real performance metrics
- User behavior insights
- Progress tracking
- Custom reporting

## 🐛 Troubleshooting

### **Database Connection Issues**
```bash
# Check PostgreSQL status
sudo systemctl status postgresql

# Test connection
psql -U kultivate_user -d kultivate_db -h localhost
```

### **Port Already in Use**
```bash
# Find process using port 8000
lsof -i :8000

# Kill process
kill -9 <PID>
```

### **Migration Errors**
```bash
# Reset migrations
alembic downgrade base
alembic upgrade head
```

## 🔮 Next Steps

### **Immediate**
1. Test all API endpoints
2. Connect Android app to backend
3. Replace hardcoded data with API calls
4. Test user registration and login

### **Future Enhancements**
- **Real-time Updates**: WebSocket support
- **Push Notifications**: FCM integration
- **Social Features**: Friend connections
- **AI Insights**: ML-powered recommendations
- **Mobile App**: React Native or Flutter
- **Web Dashboard**: Admin panel

## 📞 Support

- **API Issues**: Check the logs in your terminal
- **Database Problems**: Verify PostgreSQL connection
- **Android Integration**: Use the interactive API docs
- **General Questions**: Review the backend README.md

---

🎉 **Congratulations!** You now have a production-ready backend API for your Kultivate habit tracking app. The hardcoded sample data is history - welcome to real, scalable data management! 🚀✨
