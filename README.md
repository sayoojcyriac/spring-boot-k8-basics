# spring-boot-k8-basics

This module contains two Java Spring boot services, Hello-World and Roger-Hello. This is a simple demo project which shows how the two services communicate in a K8s cluster. 

**Hello-World pod => POD1** <br />
**Roger-Hello pod => POD2** <br />
---
  <ul>
  <li>POD1 continuoulsy sends the message "Hello World" to POD2 in a configured interval</li>
  <li>POD2 prints the message from POD1</li>
  <li>POD2 continuoulsy sends the message "I got it, roger" to POD1 in a configured interval</li>
  <li>POD1 acknowledges and prints the message from POD2</li>
  </ul>

## Prerequisites
The following prerequisities are required in order to build and run the services:
  <ol>
    <li>JDK 1.8</li>
    <li>Apache Maven</li>
    <li>Docker</li>
    <li>Kubernetes 1.16+</li>
    <li>Helm 3.0+</li>
  </ol>
  
## Hello-World
Here are the key elements of Hello-World service:
  <ul>
    <li>The service hosts two APIs</li>
    <li>/hello/health -> Health check API</li>
    <li>/hello/post -> API to post message into Hello-World. Roger-Hello uses this API to post message</li>
    <li>HelloWordService - periodically sends the message "Hello World" to Roger-Hello service. A ScheduledExecutorService handles this using a configured interval         time </li>
  </ul>
 
## Roger-Hello
Here are the key elements of Roger-Hello service:
  <ul>
    <li>The service hosts two APIs</li>
    <li>/roger/health -> Health check API</li>
    <li>/roger/post -> API to post message into Roger-Hello. Hello-World uses this API to post message</li>
    <li>RogerService - periodically sends the message "I got it, Roger" to Hello-World service. A ScheduledExecutorService handles this using a configured interval         time </li>
  </ul>
  
 ## Building Module
 mvn clean install
 
 ## Building Docker Images
 DockerFile resides in the respective modules <br />
 Hello-World - https://github.com/sayoojcyriac/spring-boot-k8-basics/blob/main/hello-world/DockerFile <br />
 
 Execute below docker build command from - https://github.com/sayoojcyriac/spring-boot-k8-basics/tree/main/hello-world <br />
 docker build -t hello-world -f DockerFile . <br />
 
 Roger-Hello - https://github.com/sayoojcyriac/spring-boot-k8-basics/blob/main/roger-hello/DockerFile <br />
 
 Execute below docker build command from - https://github.com/sayoojcyriac/spring-boot-k8-basics/tree/main/roger-hello <br />
 docker build -t roger-hello -f DockerFile . <br />
 
 ## Deploying on K8s
 The Helm deployment manifests are in - https://github.com/sayoojcyriac/spring-boot-k8-basics/tree/main/deployment <br />
 The modules contains the required manifests of both the services: <br />
 <ul>
  <li>deployment.yaml</li>
  <li>service.yaml</li>
  <li>configMap.yaml</li>
 </ul>
 
 Helm Chart for Hello-World - https://github.com/sayoojcyriac/spring-boot-k8-basics/tree/main/deployment/hello-world <br />
 Helm Chart for Roger-Hello - https://github.com/sayoojcyriac/spring-boot-k8-basics/tree/main/deployment/roger-hello <br />
 
The pod containers communicate using its deployed service. Service in K8s acts as an abstration to expose application running on different Pods as a network service. The configurable message interval from configMap is consumed as environment variable on the containers. Respective deployment manifests of service helm charts define the environment variables. The value is also later injected as a Java Spring property into the services. <br />
 
 Run the below Helm commands from deployment directory (https://github.com/sayoojcyriac/spring-boot-k8-basics/tree/main/deployment) to deploy the services. <br />
 
 Hello-Word -> helm install hello-word hello-word/ <br />
 Roger-Hello -> helm install roger-hello roger-hello/ <br />
 ![image](https://user-images.githubusercontent.com/32276029/137017878-69c7a23b-b7c8-447d-91a2-6efa2240b490.png)
 
 The two PODs shall come up and they communicate each other. <br />
 ![image](https://user-images.githubusercontent.com/32276029/137011752-bb82929c-4aa3-4f6d-b486-9fba25e515b7.png)

PODs communicating <br />
![image](https://user-images.githubusercontent.com/32276029/137012027-c9e2c689-fcb9-453e-a699-ba0d73a7c425.png)
 
