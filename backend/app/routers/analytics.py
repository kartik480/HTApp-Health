from fastapi import APIRouter, Depends, HTTPException, status
from sqlalchemy.orm import Session
from sqlalchemy import func, and_
from datetime import datetime, timedelta
from typing import List, Dict, Any
from app.database import get_db
from app.models.user import User
from app.models.habit import Habit
from app.models.habit_log import HabitLog
from app.models.streak import Streak
from app.models.mood_entry import MoodEntry
from app.auth import get_current_user

router = APIRouter()

@router.get("/dashboard")
async def get_dashboard_stats(
    current_user: User = Depends(get_current_user),
    db: Session = Depends(get_db)
):
    """Get dashboard statistics for the current user"""
    today = datetime.now().date()
    
    # Total habits
    total_habits = db.query(Habit).filter(
        Habit.user_id == current_user.id,
        Habit.is_active == True
    ).count()
    
    # Completed habits today
    completed_today = db.query(HabitLog).filter(
        HabitLog.user_id == current_user.id,
        func.date(HabitLog.completed_at) == today
    ).count()
    
    # Current streak
    current_streak = db.query(func.max(Streak.current_streak)).filter(
        Streak.user_id == current_user.id,
        Streak.is_active == True
    ).scalar() or 0
    
    # Longest streak
    longest_streak = db.query(func.max(Streak.longest_streak)).filter(
        Streak.user_id == current_user.id
    ).scalar() or 0
    
    # Weekly completion rate
    week_ago = today - timedelta(days=7)
    weekly_completions = db.query(HabitLog).filter(
        HabitLog.user_id == current_user.id,
        func.date(HabitLog.completed_at) >= week_ago
    ).count()
    
    weekly_rate = (weekly_completions / (total_habits * 7)) * 100 if total_habits > 0 else 0
    
    return {
        "total_habits": total_habits,
        "completed_today": completed_today,
        "current_streak": current_streak,
        "longest_streak": longest_streak,
        "weekly_completion_rate": round(weekly_rate, 2),
        "weekly_completions": weekly_completions
    }

@router.get("/habits/performance")
async def get_habits_performance(
    current_user: User = Depends(get_current_user),
    db: Session = Depends(get_db),
    days: int = 30
):
    """Get habits performance over the last N days"""
    end_date = datetime.now().date()
    start_date = end_date - timedelta(days=days)
    
    habits = db.query(Habit).filter(
        Habit.user_id == current_user.id,
        Habit.is_active == True
    ).all()
    
    performance_data = []
    
    for habit in habits:
        completions = db.query(HabitLog).filter(
            HabitLog.habit_id == habit.id,
            func.date(HabitLog.completed_at) >= start_date,
            func.date(HabitLog.completed_at) <= end_date
        ).count()
        
        completion_rate = (completions / days) * 100
        
        performance_data.append({
            "habit_id": habit.id,
            "title": habit.title,
            "completions": completions,
            "completion_rate": round(completion_rate, 2),
            "current_streak": habit.current_streak,
            "longest_streak": habit.longest_streak
        })
    
    return performance_data

@router.get("/mood/trends")
async def get_mood_trends(
    current_user: User = Depends(get_current_user),
    db: Session = Depends(get_db),
    days: int = 30
):
    """Get mood trends over the last N days"""
    end_date = datetime.now().date()
    start_date = end_date - timedelta(days=days)
    
    mood_entries = db.query(MoodEntry).filter(
        MoodEntry.user_id == current_user.id,
        func.date(MoodEntry.recorded_at) >= start_date,
        func.date(MoodEntry.recorded_at) <= end_date
    ).order_by(MoodEntry.recorded_at).all()
    
    mood_data = []
    for entry in mood_entries:
        mood_data.append({
            "date": entry.recorded_at.date().isoformat(),
            "mood_rating": entry.mood_rating,
            "mood_emoji": entry.mood_emoji,
            "stress_level": entry.stress_level,
            "energy_level": entry.energy_level
        })
    
    return mood_data

@router.get("/streaks/leaderboard")
async def get_streaks_leaderboard(
    current_user: User = Depends(get_current_user),
    db: Session = Depends(get_db)
):
    """Get streaks leaderboard for the current user"""
    streaks = db.query(Streak).filter(
        Streak.user_id == current_user.id,
        Streak.is_active == True
    ).join(Habit).order_by(Streak.current_streak.desc()).limit(10).all()
    
    leaderboard = []
    for i, streak in enumerate(streaks):
        leaderboard.append({
            "rank": i + 1,
            "habit_title": streak.habit.title,
            "current_streak": streak.current_streak,
            "longest_streak": streak.longest_streak,
            "start_date": streak.start_date.isoformat()
        })
    
    return leaderboard
