from pydantic import BaseModel
from typing import Optional, List
from datetime import datetime, time
from app.models.habit import HabitFrequency, HabitPriority
from .habit_log import HabitLogResponse

class HabitBase(BaseModel):
    title: str
    description: Optional[str] = None
    frequency: HabitFrequency = HabitFrequency.DAILY
    priority: HabitPriority = HabitPriority.MEDIUM
    target_count: int = 1
    reminder_time: Optional[time] = None
    color: Optional[str] = "#3B82F6"
    icon: Optional[str] = "star"

class HabitCreate(HabitBase):
    category_id: Optional[int] = None

class HabitUpdate(BaseModel):
    title: Optional[str] = None
    description: Optional[str] = None
    frequency: Optional[HabitFrequency] = None
    priority: Optional[HabitPriority] = None
    target_count: Optional[int] = None
    reminder_time: Optional[time] = None
    color: Optional[str] = None
    icon: Optional[str] = None
    category_id: Optional[int] = None
    is_active: Optional[bool] = None

class HabitResponse(HabitBase):
    id: int
    user_id: int
    category_id: Optional[int] = None
    current_streak: int
    longest_streak: int
    is_active: bool
    created_at: datetime
    updated_at: Optional[datetime] = None
    
    class Config:
        from_attributes = True

class HabitWithLogs(HabitResponse):
    habit_logs: List[HabitLogResponse] = []
