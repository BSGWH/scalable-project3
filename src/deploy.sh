#!/bin/bash

# Define variables for network and container names
PROJECT_NETWORK='project2-network'
SERVER_IMAGE='project2-image'
SERVER_CONTAINER='server-container'
CLIENT_IMAGE='project2-image'

# Clean up existing resources
echo "----------Cleaning up existing resources----------"
docker container stop $SERVER_CONTAINER 2> /dev/null && docker container rm $SERVER_CONTAINER 2> /dev/null
docker network rm $PROJECT_NETWORK 2> /dev/null

# Only cleanup if argument is cleanup-only
if [ "$1" == "cleanup-only" ]; then
  echo "Cleanup completed."
  exit
fi

# Create a custom virtual network
echo "----------Creating a virtual network----------"
docker network create $PROJECT_NETWORK

# Build the image from Dockerfile
echo "----------Building the Docker image----------"
docker build -t $SERVER_IMAGE .

# Run the server container with the specified environment variable
echo "----------Running the server container----------"
docker run -d -p 1099:1099 --name $SERVER_CONTAINER --network $PROJECT_NETWORK -e SERVICE_TYPE=server $SERVER_IMAGE

# Watch server logs
if [ "$1" == "watch-logs" ]; then
  echo "----------Watching server logs----------"
  docker logs -f $SERVER_CONTAINER
fi
