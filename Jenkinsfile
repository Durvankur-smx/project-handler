pipeline {
agent any

```
environment {
    DOCKER_USER = "springsam"
    IMAGE_NAME = "projecttool"
}

options {
    disableConcurrentBuilds()
}

stages {

    stage('Checkout') {
        steps {
            checkout scm
        }
    }

    stage('Build JAR (Maven Container)') {
        steps {
            sh '''
            docker run --rm -v $WORKSPACE:/app -w /app maven:3.9.6-eclipse-temurin-17 mvn clean package -DskipTests --no-transfer-progress
            '''
        }
    }

    stage('Build Docker Image') {
        steps {
            sh '''
            docker build -t $DOCKER_USER/$IMAGE_NAME:latest .
            '''
        }
    }

    stage('Login Docker Hub') {
        steps {
            withCredentials([usernamePassword(
                credentialsId: 'docker-pass',
                usernameVariable: 'DOCKER_USER_VAR',
                passwordVariable: 'DOCKER_PASS'
            )]) {
                sh '''
                echo $DOCKER_PASS | docker login -u $DOCKER_USER_VAR --password-stdin
                '''
            }
        }
    }

    stage('Push Image') {
        steps {
            sh '''
            docker push $DOCKER_USER/$IMAGE_NAME:latest
            '''
        }
    }
}
```

}
