Dockerizing SpringBoot Application 
- [How to Push and Pull a Docker Image from Docker Hub](https://www.youtube.com/watch?v=f2sDOaOzKPM)
- [How to push docker image to AWS ECR](https://www.youtube.com/watch?v=OBDiaKHK75c)


## Step1. Prepare Your Spring Boot Application
````bash
./mvnw package
````
This generates a JAR file in the `target/` directory (e.g., target/ecs-app-0.0.1-SNAPSHOT.jar).

## Step2. Create a Dockerfile
`Dockerfile`:
````dockerfile
# Use a lightweight base image with Java
#FROM openjdk:21-jdk-slim
FROM openjdk:21-jre-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file into the container
COPY target/ecs-app-0.0.1-SNAPSHOT.jar app.jar

# Expose the application's port
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]

````

## Step3. Build the Docker Image

Run the following commands to build and tag the Docker image:
````dockerfile
# Build the Docker image
docker build -t my-springboot-app .

# Verify the image is created
docker images
````

## Step4. Run the Docker Container
````bash
docker run -d -p 8081:8080 my-springboot-app --server.address=0.0.0.0
docker run -d -p 8081:8080 --name my-container my-springboot-app
````
Check log:
````bash
# log the container
docker logs my-container  

# Remove containers
docker rm -f $(docker ps -aq)

# Remove images
docker rmi -f $(docker images -aq) 

# Check the Java Version in the Docker Image:
docker run --rm openjdk:21-jdk-slim java -version
````

## Step5. Publish the Docker image to DockerHub
### Step5.1. Log in to DockerHub
First, ensure you are logged into your DockerHub account from the command line.
````bash
docker login
````

### Step5.2. Tag Your Docker Image
Before pushing your image to DockerHub, you need to tag it with your DockerHub username and repository name. The tag format is:
````
<username>/<repository>:<tag>
````
For example, if your DockerHub username is yourusername and the repository is your-app, tag your Docker image as follows:
````bash
docker tag <LOCAL_IMAGE_NAME> yourusername/your-app:latest
````
### Step5.3. Push the Image to DockerHub
````bash
docker push yourusername/your-app:latest
````

### Step5.4. Verify the Image on DockerHub
Once the image is successfully pushed, go to your DockerHub account and navigate to your repository (e.g., yourusername/your-app). You should see the image listed there.

### Example Process:
Assuming your local image is `your-app` and your DockerHub username is yourusername, the entire process would look like this:
````bash
# Log in to DockerHub
docker login

# Tag the image
docker tag your-app yourusername/your-app:latest
docker tag my-springboot-app arpangroup/spring-app:v1.0

# Push the image to DockerHub
docker push yourusername/your-app:latest
docker push arpangroup/spring-app:v1.0

````

## Step6. Publish the Docker image to ECR

### Step6.1. Create an ECR Repository
- [ECR](https://console.aws.amazon.com/ecr) > Create repository > ... 
- Once the repository is created, you’ll be provided with the URI of the repository (e.g., `123456789012.dkr.ecr.us-west-1.amazonaws.com/my-repository`)
### Step6.2. Authenticate Docker with ECR
Before pushing images to ECR, you need to authenticate Docker with your ECR registry.
Run the following command to authenticate Docker:
````bash
aws ecr get-login-password --region <your-region> | docker login --username AWS --password-stdin <aws_account_id>.dkr.ecr.<your-region>.amazonaws.com
````
### Step6.3. Tag Your Docker Image for ECR
````bash
docker tag your-image:latest 123456789012.dkr.ecr.us-west-1.amazonaws.com/my-repository:latest
````
### Step6.4. Push the Docker Image to ECR
````bash
docker push 123456789012.dkr.ecr.us-west-1.amazonaws.com/my-repository:latest
````
### Step6.5. Verify the Image in ECR
You can verify the image is successfully uploaded by going to the **ECR Console**, navigating to your repository, and checking if the image is listed under Images.






The major version number corresponds to the Java version:
- 45: Java 1.1
- 46: Java 1.2
- 47: Java 1.3
- 48: Java 1.4
- 49: Java 5
- 50: Java 6
- 51: Java 7
- 52: Java 8
- 53: Java 9
- 54: Java 10
- 55: Java 11
- 56: Java 12
- 57: Java 13
- 58: Java 14
- 59: Java 15
- 60: Java 16
- 61: Java 17
- 62: Java 18
- 63: Java 19
- 64: Java 20
- 65: Java 21

````bash
javap -verbose D:\java-projects\ecs-app\target\classes\com\arpan\ecs_app/EcsAppApplication.class | grep "major version"
````