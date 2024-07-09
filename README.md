Edge Computing Music Synchronization

This project implements an edge computing solution for real-time audio synchronization between two clients using edge servers and a central server.
Folder Structure

    client/ - Contains the source code and Dockerfile for the client.
    edgeserver/ - Contains the source code and Dockerfile for the edge server.
    centralserver/ - Contains the source code and Dockerfile for the central server.
    kubernetes/ - Contains YAML files for deployment on Kubernetes.

Configuration and Execution
Prerequisites

    Docker
    Kubernetes
    kubectl configured for your Kubernetes cluster

Building Docker Images

    Client:
    Bash

        cd client
        docker build -t your-docker-repo/client:latest .


    Edge Server:
    Bash

        cd ../edgeserver
        docker build -t your-docker-repo/edgeserver:latest .


    Central Server:
    Bash

        cd ../centralserver
        docker build -t your-docker-repo/centralserver:latest .


Deployment on Kubernetes

    Deploy Client:
    Bash

    kubectl apply -f kubernetes/client-deployment.yaml
    kubectl apply -f kubernetes/client-service.yaml


    Deploy Edge Server:
    Bash

    kubectl apply -f kubernetes/edge-server-deployment.yaml
    kubectl apply -f kubernetes/edge-server-service.yaml


    Deploy Central Server:
    Bash

    kubectl apply -f kubernetes/central-server-deployment.yaml
    kubectl apply -f kubernetes/central-server-service.yaml


    Deploy Ingress:
    Bash

    kubectl apply -f kubernetes/ingress.yaml

    Use code with caution.

Apply Network Policies:
Bash

   kubectl apply -f kubernetes/network-policies.yaml


Description of Configuration Files

    Client Deployment YAML: The client-deployment.yaml file defines a deployment for the client, creating two replicas of the client. Each pod listens on port 5000.
    Edge Server Deployment YAML: The edge-server-deployment.yaml file defines a deployment for the edge server, creating two replicas of the edge server. Each pod listens on port 5000.
    Central Server Deployment YAML: The central-server-deployment.yaml file defines a deployment for the central server, creating a single replica of the central server. The pod listens on port 5000.
    Ingress YAML: The ingress.yaml file defines the ingress rules for the client, edge server, and central server services, allowing access through a specific domain.
    Network Policies YAML: The network-policies.yaml file defines the network policies to allow traffic between clients and edge servers, and between edge servers and the central server.
