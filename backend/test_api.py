#!/usr/bin/env python3
"""
Simple test script for Kultivate API
"""

import requests
import json

BASE_URL = "http://localhost:8000"

def test_health_check():
    """Test the health check endpoint"""
    try:
        response = requests.get(f"{BASE_URL}/api/v1/health")
        print(f"✅ Health Check: {response.status_code}")
        if response.status_code == 200:
            print(f"   Response: {response.json()}")
        return response.status_code == 200
    except requests.exceptions.ConnectionError:
        print("❌ Health Check: Connection failed - Is the server running?")
        return False

def test_root_endpoint():
    """Test the root endpoint"""
    try:
        response = requests.get(f"{BASE_URL}/")
        print(f"✅ Root Endpoint: {response.status_code}")
        if response.status_code == 200:
            print(f"   Response: {response.json()}")
        return response.status_code == 200
    except requests.exceptions.ConnectionError:
        print("❌ Root Endpoint: Connection failed")
        return False

def test_user_registration():
    """Test user registration"""
    user_data = {
        "email": "test@example.com",
        "username": "testuser",
        "password": "testpassword123",
        "first_name": "Test",
        "last_name": "User"
    }
    
    try:
        response = requests.post(
            f"{BASE_URL}/api/v1/auth/register",
            json=user_data
        )
        print(f"✅ User Registration: {response.status_code}")
        if response.status_code == 200:
            print(f"   User created: {response.json()['username']}")
            return True
        else:
            print(f"   Error: {response.json()}")
            return False
    except requests.exceptions.ConnectionError:
        print("❌ User Registration: Connection failed")
        return False

def test_user_login():
    """Test user login"""
    login_data = {
        "email": "test@example.com",
        "password": "testpassword123"
    }
    
    try:
        response = requests.post(
            f"{BASE_URL}/api/v1/auth/login",
            json=login_data
        )
        print(f"✅ User Login: {response.status_code}")
        if response.status_code == 200:
            token = response.json()['access_token']
            print(f"   Token received: {token[:20]}...")
            return token
        else:
            print(f"   Error: {response.json()}")
            return None
    except requests.exceptions.ConnectionError:
        print("❌ User Login: Connection failed")
        return None

def test_protected_endpoint(token):
    """Test a protected endpoint"""
    if not token:
        print("❌ Protected Endpoint: No token available")
        return False
    
    headers = {"Authorization": f"Bearer {token}"}
    
    try:
        response = requests.get(
            f"{BASE_URL}/api/v1/users/me",
            headers=headers
        )
        print(f"✅ Protected Endpoint: {response.status_code}")
        if response.status_code == 200:
            print(f"   User info: {response.json()['username']}")
            return True
        else:
            print(f"   Error: {response.json()}")
            return False
    except requests.exceptions.ConnectionError:
        print("❌ Protected Endpoint: Connection failed")
        return False

def main():
    """Run all tests"""
    print("🚀 Testing Kultivate API...")
    print("=" * 50)
    
    # Test basic endpoints
    health_ok = test_health_check()
    root_ok = test_root_endpoint()
    
    if not health_ok or not root_ok:
        print("\n❌ Basic endpoints failed. Make sure the server is running.")
        return
    
    print("\n✅ Basic endpoints working!")
    print("\n🔐 Testing authentication...")
    
    # Test authentication
    registration_ok = test_user_registration()
    if not registration_ok:
        print("\n❌ User registration failed.")
        return
    
    token = test_user_login()
    if not token:
        print("\n❌ User login failed.")
        return
    
    print("\n✅ Authentication working!")
    print("\n🔒 Testing protected endpoints...")
    
    # Test protected endpoints
    protected_ok = test_protected_endpoint(token)
    
    print("\n" + "=" * 50)
    if protected_ok:
        print("🎉 All tests passed! API is working correctly.")
    else:
        print("⚠️  Some tests failed. Check the output above.")

if __name__ == "__main__":
    main()
