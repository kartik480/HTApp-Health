from sqlalchemy import Column, Integer, String, DateTime, ForeignKey, Date
from sqlalchemy.orm import relationship
from sqlalchemy.sql import func
from app.database import Base

class Streak(Base):
    __tablename__ = "streaks"
    
    id = Column(Integer, primary_key=True, index=True)
    user_id = Column(Integer, ForeignKey("users.id"), nullable=False)
    habit_id = Column(Integer, ForeignKey("habits.id"), nullable=False)
    current_streak = Column(Integer, default=0)
    longest_streak = Column(Integer, default=0)
    start_date = Column(Date, nullable=False)
    last_completion_date = Column(Date)
    is_active = Column(Integer, default=True)  # 1 for active, 0 for broken
    created_at = Column(DateTime(timezone=True), server_default=func.now())
    updated_at = Column(DateTime(timezone=True), onupdate=func.now())
    
    # Relationships
    user = relationship("User", back_populates="streaks")
    habit = relationship("Habit", back_populates="streaks")
    
    def __repr__(self):
        return f"<Streak(id={self.id}, habit_id={self.habit_id}, current_streak={self.current_streak})>"
