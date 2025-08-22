from sqlalchemy import Column, Integer, String, DateTime, Boolean, Text, ForeignKey, Float
from sqlalchemy.orm import relationship
from sqlalchemy.sql import func
from app.database import Base

class HabitLog(Base):
    __tablename__ = "habit_logs"
    
    id = Column(Integer, primary_key=True, index=True)
    user_id = Column(Integer, ForeignKey("users.id"), nullable=False)
    habit_id = Column(Integer, ForeignKey("habits.id"), nullable=False)
    completed_at = Column(DateTime(timezone=True), server_default=func.now())
    notes = Column(Text)
    mood_rating = Column(Float)  # 1-10 scale
    completion_time = Column(Integer)  # Time taken in minutes
    is_completed = Column(Boolean, default=True)
    quality_rating = Column(Float)  # 1-5 scale
    
    # Relationships
    user = relationship("User", back_populates="habit_logs")
    habit = relationship("Habit", back_populates="habit_logs")
    
    def __repr__(self):
        return f"<HabitLog(id={self.id}, habit_id={self.habit_id}, user_id={self.user_id}, completed_at={self.completed_at})>"
