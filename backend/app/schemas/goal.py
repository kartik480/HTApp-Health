from pydantic import BaseModel
from typing import Optional
from datetime import datetime, date

class GoalBase(BaseModel):
    title: str
    description: Optional[str] = None
    target_value: Optional[float] = None
    current_value: Optional[float] = 0
    unit: Optional[str] = None
    deadline: Optional[date] = None

class GoalCreate(GoalBase):
    pass

class GoalUpdate(BaseModel):
    title: Optional[str] = None
    description: Optional[str] = None
    target_value: Optional[float] = None
    current_value: Optional[float] = None
    unit: Optional[str] = None
    deadline: Optional[date] = None
    is_completed: Optional[bool] = None

class GoalResponse(GoalBase):
    id: int
    user_id: int
    is_completed: bool
    completed_at: Optional[datetime] = None
    progress_percentage: float
    created_at: datetime
    updated_at: Optional[datetime] = None
    
    class Config:
        from_attributes = True
