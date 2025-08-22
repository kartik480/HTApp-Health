from sqlalchemy import Column, Integer, String, DateTime, Text, ForeignKey, Boolean, Date, Float
from sqlalchemy.orm import relationship
from sqlalchemy.sql import func
from app.database import Base

class Goal(Base):
    __tablename__ = "goals"
    
    id = Column(Integer, primary_key=True, index=True)
    user_id = Column(Integer, ForeignKey("users.id"), nullable=False)
    title = Column(String(200), nullable=False)
    description = Column(Text)
    target_value = Column(Float)
    current_value = Column(Float, default=0)
    unit = Column(String(50))  # e.g., "days", "times", "hours"
    deadline = Column(Date)
    is_completed = Column(Boolean, default=False)
    completed_at = Column(DateTime(timezone=True))
    progress_percentage = Column(Float, default=0)
    created_at = Column(DateTime(timezone=True), server_default=func.now())
    updated_at = Column(DateTime(timezone=True), onupdate=func.now())
    
    # Relationships
    user = relationship("User", back_populates="goals")
    
    def __repr__(self):
        return f"<Goal(id={self.id}, title='{self.title}', user_id={self.user_id})>"
