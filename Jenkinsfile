pipeline {
    agent any

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

        stage('Build JAR') {
            steps {
                bat '''
                mvn clean package -DskipTests
                '''
            }
        }

        stage('Build Docker Image') {
            steps {
                bat '''
                docker build -t %DOCKER_USER%/%IMAGE_NAME%:latest .
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
                    bat '''
                    echo %DOCKER_PASS% | docker login -u %DOCKER_USER_VAR% --password-stdin
                    '''
                }
            }
        }

        stage('Push Image') {
            steps {
                bat '''
                docker push %DOCKER_USER%/%IMAGE_NAME%:latest
                '''
            }
        }
    }
}