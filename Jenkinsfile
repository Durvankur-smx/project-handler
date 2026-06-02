pipeline {
    agent any

    stages {

        stage('Build JAR') {
            steps {
                sh 'chmod +x mvnw'
                sh './mvnw clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t projecttool .'
            }
        }

        stage('Deploy') {
            steps {
                sh 'docker stop projecttool-app || true'
                sh 'docker rm projecttool-app || true'

                sh '''
                docker run -d \
                --name projecttool-app \
                -p 8082:8081 \
                -e SPRING_DATASOURCE_URL=jdbc:mysql://host.docker.internal:3307/projectdb \
                -e SPRING_DATASOURCE_USERNAME=user \
                -e SPRING_DATASOURCE_PASSWORD=password \
                projecttool
                '''
            }
        }

        stage('Push to Docker Hub') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'dockerhub-creds',
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {

                    sh '''
                    echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin

                    docker tag projecttool:latest druv29/projecttool:latest

                    docker push druv29/projecttool:latest
                    '''
                }
            }
        }
    }
}