# Team Name

- Team Rocket

# Planetarium

The Planetarium is a web service that allows users to add planets and associated moons to a central database to map the night sky. Users must register an account to participate, and those who do will be able to associate themselves with the planets and moons they add to the database. In the previous Sprint, you turned the Planetarium into a Spring Boot application, created a docker image of the project, set up a deployment using a docker compose file that included log aggregation via Promtail and Loki, added metric over time tracking with Prometheus, and used Grafana as part of the deployment to provide a single interface for viewing the aggregated logs and metrics.

In this sprint you will add on the infrastructure to deploy your Planetarium app and monitoring tools into a Kubernetes cluster. Once your cluster is set up you will have two more goals: implement a DevOps pipeline facilitated by Jenkins, and set up a terraform file to create a Postgres RDS instance for your Planetarium.

## Key Terminology

- **Project**
  - A software application built for some company/entity
  - Examples
    - Employee paid time off Scheduler
    - Helicopter Navigation System
    - To-Do task manager
- **Sprint**
  - a term used to describe a short period of development work, typically no more than a few weeks of time
- **Minimum Viable Product**
  - a phrase used to describe a project that has the minimum number of features and functionality applied to make the sprint considered successful

## SRE Requirements

- Kubernetes Requirements
  - Planetarium should be deployed to a Kubernetes Cluster
    - Promtail should be sidecar deployed alongside the Planetarium
  - Loki should be deployed in the cluster for log aggregation
  - Prometheus should be deployed in the cluster for metric tracking over time
  - Grafana should be deployed in the cluster to allow viewing/interacting with logs and metrics
  - Jenkins should be deployed in the cluster to manage your DevOps pipeline
- Terraform Requirements
  - Terraform should be used to manage a new Postgres RDS instance for your Planetarium application
    - make sure that it is free tier eligible when you create it
- Github Requirements

  - all team members should contribute to the github repository
  - git polling should be set up to allow for Jenkins to automate the DevOps pipeline you set up
  - the main branch of the project repository should be protected from direct pushes
    - all Sprint work should be done on secondary branches
    - secondary branch names should reflect what work is being done

- DevOps Requirements

  - all team members should make Continuous Integration a central part of their Sprint work
  - Some level of Continuous Delivery should be implemented by the end of the Sprint
    - Jenkins should manage your DevOps pipeline

- Team Requirements
  - team members should pair program during this sprint
    - "pairs" can be larger than groups of two

### Service Level Objects

- 99.8% of requests should complete successfully within 200 milliseconds

### Service Level Indicators

- Latency
  - you should track how long it takes for all Planetarium apps to handle requests made to the system
- Error Rate
  - you should track the percentage of how many http requests return a non-500 status code
