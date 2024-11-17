#!/bin/bash

# Define variables for network and container names
PROJECT_NETWORK='project2-network'
CLIENT_IMAGE='project2-image'

# Run the multi-threaded client container
echo "----------Running the multi-threaded client container----------"
docker run -it --rm --network $PROJECT_NETWORK -e SERVICE_TYPE=client $CLIENT_IMAGE java client.MultiClientTest
