# Edge Computing Music Synchronization

Questo progetto implementa una soluzione di edge computing per la sincronizzazione in tempo reale di audio tra due client tramite server edge e un server centrale.

## Struttura delle Cartelle

- `client/` - Contiene il codice sorgente e il Dockerfile per il client.
- `edgeserver/` - Contiene il codice sorgente e il Dockerfile per il server edge.
- `centralserver/` - Contiene il codice sorgente e il Dockerfile per il server centrale.
- `kubernetes/` - Contiene i file YAML per il deployment su Kubernetes.

## Configurazione e Esecuzione

### Prerequisiti

- Docker
- Kubernetes
- kubectl configurato per il tuo cluster Kubernetes

### Creazione delle Immagini Docker

1. Client:
   ```sh
   cd client
   docker build -t your-docker-repo/client:latest .

2. Edge Server:
   ```sh
   cd ../edgeserver
   docker build -t your-docker-repo/edgeserver:latest .

3. Central Server:
   ```sh
   cd ../centralserver
   docker build -t your-docker-repo/centralserver:latest .

### Deployment su Kubernetes

Deploy Client:

```sh

    kubectl apply -f kubernetes/client-deployment.yaml
    kubectl apply -f kubernetes/client-service.yaml

Deploy Edge Server:

```sh

    kubectl apply -f kubernetes/edge-server-deployment.yaml
    kubectl apply -f kubernetes/edge-server-service.yaml

Deploy Central Server:

```sh

    kubectl apply -f kubernetes/central-server-deployment.yaml
    kubectl apply -f kubernetes/central-server-service.yaml

Deploy Ingress:

```sh

    kubectl apply -f kubernetes/ingress.yaml

Apply Network Policies:

```sh

    kubectl apply -f kubernetes/network-policies.yaml

Descrizione dei File di Configurazione

    Client Deployment YAML

        Il file client-deployment.yaml definisce un deployment per il client, creando due repliche del client. Ogni pod ascolta sulla porta 5000.
    Edge Server Deployment YAML

        Il file edge-server-deployment.yaml definisce un deployment per il server edge, creando due repliche del server edge. Ogni pod ascolta sulla porta 5000.
    Central Server Deployment YAML

        Il file central-server-deployment.yaml definisce un deployment per il server centrale, creando una singola replica del server centrale. Il pod ascolta sulla porta 5000.
    Ingress YAML

        Il file ingress.yaml definisce le regole di ingresso per i servizi client, edge server e central server, permettendo l'accesso tramite un dominio specifico.
    Network Policies YAML

        Il file network-policies.yaml definisce le politiche di rete per permettere il traffico tra i client e i server edge, e tra i server edge e il server centrale.