# Simple Blockchain Implementation with Spring Boot and WebSocket

## Overview
This repository contains a basic blockchain implementation developed using Spring Boot and WebSocket technologies. The project leverages the robustness and flexibility of Spring Boot to create a backend service, and integrates WebSocket for real-time data communication, crucial for node synchronization in blockchain networks.

## Key Features
- **Spring Boot Framework**: Utilizes Spring Boot for efficient backend service development.
- **WebSocket Integration**: Employs WebSocket for real-time, bidirectional communication between nodes.
- **Blockchain Basics**: Implements fundamental blockchain concepts like block creation and chain validation.

This blockchain implementation is particularly suited for applications that require distributed ledger technology for secure and verifiable data storage, but do not necessitate currency or asset transaction capabilities. It is an excellent choice for educational purposes, data verification projects, or any application where the integrity of stored information is paramount.

## Getting Started

### Prerequisites
- Java Development Kit (JDK)
- Maven (for dependency management)

### Installation
1. Clone this project:
git clone https://github.com/ChaofanHu/BlockChain
2. Navigate to the project directory:

### Running the Application
1. Set the port and the nodes you want to connect in the application settings.
2. Start the application:


### Using the Blockchain
- **Initialize the Genesis Block**:
localhost:8080/genesis
- **Mine New Blocks**:
localhost:8080/mine?data=[Your Data Here]
- **Get the Existing Blockchain**:
localhost:8080/getBlockchain


## Contributions
Contributions are welcome! Please feel free to submit a pull request.

## License
This project is licensed under the [Your License] - see the LICENSE file for details.

