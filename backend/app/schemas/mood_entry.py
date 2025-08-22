from pydantic import BaseModel
from typing import Optional
from datetime import datetime

class MoodEntryBase(BaseModel):
    mood_rating: float
    mood_emoji: Optional[str] = None
    notes: Optional[str] = None
    activities: Optional[str] = None
    sleep_hours: Optional[float] = None
    stress_level: Optional[float] = None
    energy_level: Optional[float] = None

class MoodEntryCreate(MoodEntryBase):
    pass

class MoodEntryUpdate(BaseModel):
    mood_rating: Optional[float] = None
    mood_emoji: Optional[str] = None
    notes: Optional[str] = None
    activities: Optional[str] = None
    sleep_hours: Optional[float] = None
    stress_level: Optional[float] = None
    energy_level: Optional[float] = None

class MoodEntryResponse(MoodEntryBase):
    id: int
    user_id: int
    recorded_at: datetime
    
    class Config:
        from_attributes = True
