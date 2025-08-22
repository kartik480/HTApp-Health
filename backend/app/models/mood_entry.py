from sqlalchemy import Column, Integer, String, DateTime, Text, ForeignKey, Float
from sqlalchemy.orm import relationship
from sqlalchemy.sql import func
from app.database import Base

class MoodEntry(Base):
    __tablename__ = "mood_entries"
    
    id = Column(Integer, primary_key=True, index=True)
    user_id = Column(Integer, ForeignKey("users.id"), nullable=False)
    mood_rating = Column(Float, nullable=False)  # 1-10 scale
    mood_emoji = Column(String(10))  # Emoji representation
    notes = Column(Text)
    activities = Column(Text)  # JSON string of activities that day
    sleep_hours = Column(Float)
    stress_level = Column(Float)  # 1-10 scale
    energy_level = Column(Float)  # 1-10 scale
    recorded_at = Column(DateTime(timezone=True), server_default=func.now())
    
    # Relationships
    user = relationship("User", back_populates="mood_entries")
    
    def __repr__(self):
        return f"<MoodEntry(id={self.id}, user_id={self.user_id}, mood_rating={self.mood_rating})>"
