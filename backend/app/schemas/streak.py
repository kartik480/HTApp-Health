from pydantic import BaseModel
from datetime import date, datetime
from typing import Optional

class StreakResponse(BaseModel):
    id: int
    user_id: int
    habit_id: int
    current_streak: int
    longest_streak: int
    start_date: date
    last_completion_date: date
    is_active: int
    created_at: datetime
    updated_at: Optional[datetime] = None
    
    class Config:
        from_attributes = True
