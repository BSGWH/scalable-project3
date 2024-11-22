#!/bin/bash

# Define the ports to be used by the servers
ports=(1099 1100 1101 1102 1103)

# Function to kill processes using specific ports
kill_ports() {
    echo "Checking and killing processes on specified ports..."
    for port in "${ports[@]}"; do
        pid=$(lsof -t -i :"$port")
        if [ -n "$pid" ]; then
            echo "Port $port is in use by PID $pid. Killing process..."
            kill -9 "$pid" 2>/dev/null
            sleep 1
        else
            echo "Port $port is free."
        fi
    done
    echo "Final port check:"
    for port in "${ports[@]}"; do
        lsof -i :"$port" && echo "Port $port is still in use!" || echo "Port $port is free."
    done
}

# Kill processes using the specified ports
kill_ports

# Start the servers
echo "Starting servers..."
java server.ServerApp 1 1099 &
sleep 1
java server.ServerApp 2 1100 &
sleep 1
java server.ServerApp 3 1101 &
sleep 1
java server.ServerApp 4 1102 &
sleep 1
java server.ServerApp 5 1103 &
