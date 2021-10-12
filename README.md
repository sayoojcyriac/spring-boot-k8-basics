# spring-boot-k8-basics

This module contains two Java Spring boot services, Hello-World and Roger-Hello. This is a simple demo project to demostrate how the two services communicate in a K8s cluster. 

**Hello-World pod => POD1** <br />
**Roger-Hello pod => POD2** <br />
---
  <ul>
  <li>POD1 sends the message "Hello World" to POD2 in regular intervals</li>
  <li>POD2 prints the message from POD1</li>
  <li>POD2 send the message "I got it, roger" to POD1</li>
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
