# Kultivate API - Backend

A powerful and scalable backend API for the Kultivate habit tracking application, built with FastAPI, SQLAlchemy, and PostgreSQL.

## 🚀 Features

- **User Authentication**: JWT-based authentication with secure password hashing
- **Habit Management**: Complete CRUD operations for habits and habit tracking
- **Category System**: Organize habits into customizable categories
- **Streak Tracking**: Monitor and maintain habit streaks
- **Analytics**: Comprehensive insights into habit performance and mood trends
- **Mood Tracking**: Record and analyze daily mood and wellness metrics
- **Goal Setting**: Set and track progress towards personal goals
- **Achievement System**: Gamified rewards for consistent habit building
- **Real-time Notifications**: Stay motivated with timely reminders

## 🏗️ Architecture

- **FastAPI**: Modern, fast web framework for building APIs
- **SQLAlchemy**: SQL toolkit and Object-Relational Mapping (ORM)
- **PostgreSQL**: Robust, open-source relational database
- **Alembic**: Database migration tool
- **JWT**: Secure token-based authentication
- **Pydantic**: Data validation using Python type annotations

## 📋 Prerequisites

- Python 3.8+
- PostgreSQL 12+
- Redis (optional, for caching)

## 🛠️ Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd backend
   ```

2. **Create virtual environment**
   ```bash
   python -m venv venv
   source venv/bin/activate  # On Windows: venv\Scripts\activate
   ```

3. **Install dependencies**
   ```bash
   pip install -r requirements.txt
   ```

4. **Set up environment variables**
   ```bash
   cp env_example.txt .env
   # Edit .env with your database credentials
   ```

5. **Set up PostgreSQL database**
   ```sql
   CREATE DATABASE kultivate_db;
   CREATE USER kultivate_user WITH PASSWORD 'your_password';
   GRANT ALL PRIVILEGES ON DATABASE kultivate_db TO kultivate_user;
   ```

6. **Run database migrations**
   ```bash
   alembic upgrade head
   ```

## 🚀 Running the Application

### Development Mode
```bash
python run.py
```

### Production Mode
```bash
uvicorn app.main:app --host 0.0.0.0 --port 8000
```

The API will be available at `http://localhost:8000`

## 📚 API Documentation

Once the server is running, you can access:

- **Interactive API Docs**: `http://localhost:8000/docs`
- **ReDoc Documentation**: `http://localhost:8000/redoc`
- **OpenAPI Schema**: `http://localhost:8000/openapi.json`

## 🔐 Authentication

The API uses JWT tokens for authentication. To access protected endpoints:

1. **Register**: `POST /api/v1/auth/register`
2. **Login**: `POST /api/v1/auth/login`
3. **Include token**: Add `Authorization: Bearer <token>` header

## 📊 Database Models

### Core Entities
- **Users**: User accounts and profiles
- **Habits**: Habit definitions and settings
- **HabitLogs**: Daily habit completion records
- **Categories**: Habit organization system
- **Streaks**: Habit consistency tracking
- **Achievements**: Gamification rewards
- **MoodEntries**: Wellness and mood tracking
- **Goals**: Personal goal setting and tracking
- **Notifications**: User alerts and reminders

## 🔄 API Endpoints

### Authentication
- `POST /api/v1/auth/register` - User registration
- `POST /api/v1/auth/login` - User login
- `POST /api/v1/auth/refresh` - Token refresh

### Users
- `GET /api/v1/users/me` - Get current user info
- `PUT /api/v1/users/me` - Update user profile
- `DELETE /api/v1/users/me` - Delete user account

### Habits
- `GET /api/v1/habits/` - List user habits
- `POST /api/v1/habits/` - Create new habit
- `GET /api/v1/habits/{id}` - Get specific habit
- `PUT /api/v1/habits/{id}` - Update habit
- `DELETE /api/v1/habits/{id}` - Delete habit

### Categories
- `GET /api/v1/categories/` - List categories
- `POST /api/v1/categories/` - Create category
- `PUT /api/v1/categories/{id}` - Update category
- `DELETE /api/v1/categories/{id}` - Delete category

### Analytics
- `GET /api/v1/analytics/dashboard` - Dashboard statistics
- `GET /api/v1/analytics/habits/performance` - Habit performance data
- `GET /api/v1/analytics/mood/trends` - Mood tracking trends
- `GET /api/v1/analytics/streaks/leaderboard` - Streak leaderboard

## 🧪 Testing

Run the test suite:
```bash
pytest
```

## 📦 Deployment

### Docker (Recommended)
```bash
docker build -t kultivate-api .
docker run -p 8000:8000 kultivate-api
```

### Environment Variables
- `DATABASE_URL`: PostgreSQL connection string
- `SECRET_KEY`: JWT secret key
- `DEBUG`: Enable/disable debug mode
- `ENVIRONMENT`: Production/development environment

## 🔒 Security Features

- **Password Hashing**: Bcrypt encryption
- **JWT Tokens**: Secure authentication
- **CORS Protection**: Cross-origin request handling
- **Input Validation**: Pydantic schema validation
- **SQL Injection Protection**: SQLAlchemy ORM

## 📈 Performance

- **Async/Await**: Non-blocking I/O operations
- **Connection Pooling**: Database connection optimization
- **Caching**: Redis-based caching (optional)
- **Lazy Loading**: Efficient relationship loading

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests
5. Submit a pull request

## 📄 License

This project is licensed under the MIT License.

## 🆘 Support

For support and questions:
- Create an issue in the repository
- Check the API documentation
- Review the code examples

## 🔮 Future Enhancements

- **Real-time Updates**: WebSocket support
- **Mobile Push Notifications**: FCM integration
- **Social Features**: Friend connections and sharing
- **AI Insights**: Machine learning for habit recommendations
- **Export/Import**: Data portability features
- **Multi-language**: Internationalization support
