#!/bin/bash

# Define variables for network and container names
PROJECT_NETWORK='project2-network'
CLIENT_IMAGE='project2-image'

# Run the client container with the specified environment variable
echo "----------Running the client container----------"
docker run -it --rm --network $PROJECT_NETWORK -e SERVICE_TYPE=client $CLIENT_IMAGE
