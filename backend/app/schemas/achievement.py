from pydantic import BaseModel
from typing import Optional
from datetime import datetime

class AchievementResponse(BaseModel):
    id: int
    user_id: int
    title: str
    description: Optional[str] = None
    icon: Optional[str] = None
    points: int
    is_unlocked: bool
    unlocked_at: Optional[datetime] = None
    created_at: datetime
    
    class Config:
        from_attributes = True
