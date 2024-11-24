# Project Setup

## Prerequisites
- Java 17
- Maven
- Docker
- Kubernetes CLI (kubectl)

## Build the Project

To build the project, run the following command:

```sh
mvn clean package
```

## Build the Docker Image

To build the Docker image, use the following command:
```sh
docker build -t producer:latest .
```

## Deploy to Kubernetes

To deploy the application to Kubernetes, apply the following configurations:

### RabbitMQ
```sh
./rabbit-mq/start.sh 
kubectl apply -f k8s/rabbit-mq/deployment.yaml
kubectl apply -f k8s/rabbit-mq/service.yaml
```

### Producer
```sh
kubectl apply -f k8s/producer/deployment.yaml
kubectl apply -f k8s/producer/service.yaml
```

## View logs
```sh
kubectl get pods | grep producer
```

```sh
kubectl logs -f <pod-name>
```

## Test the Application
It is recommended to use Postman to execute the request.
```sh
curl --location --request POST 'localhost:8080/produce?message=Lima'
```

## Clean up
```sh
kubectl delete -f k8s/producer/deployment.yaml
kubectl delete -f k8s/producer/service.yaml
```

```sh
kubectl delete -f k8s/rabbit-mq/deployment.yaml
kubectl delete -f k8s/rabbit-mq/service.yaml
```

# Delete the Docker Image
```sh
docker rmi producer:latest
```


# GitHub Actions Workflow for Building, Pushing, and Deploying to AKS

This repository contains a GitHub Actions workflow for building a Docker image, pushing it to Azure Container Registry (ACR), and deploying it to Azure Kubernetes Service (AKS).

## Add Federated Credential to Azure App Registration

1. Go to the Azure Portal.
2. Select "Azure Active Directory" in the left sidebar.
3. Click on "App registrations" and select your app registration.
4. In the left sidebar, select "Certificates & secrets".
5. Click on the "Federated credentials" tab.
6. Click the "Add credential" button.
7. Configure the federated credential:
   - **Issuer**: Select GitHub.
   - **Subject Identifier**: Use the format repo:<GitHub-organization>/<GitHub-repository>:ref:refs/heads/<branch-name>.
   - **Name**: Provide a name for the federated credential.
   - **Description**: Optionally, provide a description.
8. Click the "Add" button to save the federated credential.

## Workflow Configuration

The workflow is defined in the `.github/workflows/build-deploy.yml` file. It consists of three jobs:

1. **Build**: Builds the Docker image and pushes it to ACR.
2. **Deploy**: Deploys the Docker image to AKS.

### Environment Variables

The following environment variables are used in the workflow and should be set as secrets in your GitHub repository:

- `AZURE_CLIENT_ID`
- `AZURE_CLIENT_SECRET`
- `AZURE_SUBSCRIPTION_ID`
- `AZURE_TENANT_ID`
- `ACR_NAME`
- `AZURE_RESOURCE_GROUP`
- `AKS_NAME`

### Workflow Dispatch

The workflow can be triggered manually using the `workflow_dispatch` event. This allows you to run the workflow on demand.

## Manual Execution

To manually execute the workflow, follow these steps:

1. Go to the "Actions" tab in your GitHub repository.
2. Select the workflow you want to run (e.g., "Build, Push image to ACR, Deploy to AKS").
3. Click the "Run workflow" button.
4. (Optional) For the rollback job, provide the input `rollback` set to `true`.

## File Structure

- `.github/workflows/build-deploy.yml`: The GitHub Actions workflow file.
- `k8s/producer/deployment.yaml`: The Kubernetes deployment file for Producer Microservice.
- `k8s/rabbit-mq/deployment.yaml`: The Kubernetes deployment file for RabbitMQ.

## Additional Information

For more details on configuring GitHub Actions and using Azure services, refer to the following documentation:

- [GitHub Actions Documentation](https://docs.github.com/en/actions)
- [Azure Container Registry Documentation](https://docs.microsoft.com/en-us/azure/container-registry/)
- [Azure Kubernetes Service Documentation](https://docs.microsoft.com/en-us/azure/aks/)