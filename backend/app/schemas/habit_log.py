from pydantic import BaseModel
from typing import Optional
from datetime import datetime

class HabitLogBase(BaseModel):
    notes: Optional[str] = None
    mood_rating: Optional[float] = None
    completion_time: Optional[int] = None
    quality_rating: Optional[float] = None

class HabitLogCreate(HabitLogBase):
    habit_id: int

class HabitLogUpdate(BaseModel):
    notes: Optional[str] = None
    mood_rating: Optional[float] = None
    completion_time: Optional[int] = None
    quality_rating: Optional[float] = None
    is_completed: Optional[bool] = None

class HabitLogResponse(HabitLogBase):
    id: int
    user_id: int
    habit_id: int
    completed_at: datetime
    is_completed: bool
    
    class Config:
        from_attributes = True
