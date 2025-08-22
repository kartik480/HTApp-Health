from sqlalchemy import Column, Integer, String, DateTime, Boolean, Text, Enum, ForeignKey, Time
from sqlalchemy.orm import relationship
from sqlalchemy.sql import func
from app.database import Base
import enum

class HabitFrequency(str, enum.Enum):
    DAILY = "daily"
    WEEKLY = "weekly"
    MONTHLY = "monthly"
    CUSTOM = "custom"

class HabitPriority(str, enum.Enum):
    LOW = "low"
    MEDIUM = "medium"
    HIGH = "high"

class Habit(Base):
    __tablename__ = "habits"
    
    id = Column(Integer, primary_key=True, index=True)
    user_id = Column(Integer, ForeignKey("users.id"), nullable=False)
    category_id = Column(Integer, ForeignKey("categories.id"))
    title = Column(String(200), nullable=False)
    description = Column(Text)
    frequency = Column(Enum(HabitFrequency), default=HabitFrequency.DAILY)
    priority = Column(Enum(HabitPriority), default=HabitPriority.MEDIUM)
    target_count = Column(Integer, default=1)
    current_streak = Column(Integer, default=0)
    longest_streak = Column(Integer, default=0)
    reminder_time = Column(Time)
    is_active = Column(Boolean, default=True)
    color = Column(String(7), default="#3B82F6")  # Hex color
    icon = Column(String(50), default="star")
    created_at = Column(DateTime(timezone=True), server_default=func.now())
    updated_at = Column(DateTime(timezone=True), onupdate=func.now())
    
    # Relationships
    user = relationship("User", back_populates="habits")
    category = relationship("Category", back_populates="habits")
    habit_logs = relationship("HabitLog", back_populates="habit", cascade="all, delete-orphan")
    streaks = relationship("Streak", back_populates="habit", cascade="all, delete-orphan")
    
    def __repr__(self):
        return f"<Habit(id={self.id}, title='{self.title}', user_id={self.user_id})>"
