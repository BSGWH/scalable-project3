#!/bin/bash

# Step 1: Stop and remove any running containers
echo "Stopping and removing existing containers..."
docker-compose down

# Step 2: Run and rebuild containers if needed
echo "Starting containers with auto-rebuild..."
docker-compose up --build
