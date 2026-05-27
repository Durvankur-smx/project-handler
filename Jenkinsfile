pipeline {
    agent any

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main',
                url: 'https://github.com/Durvankur-smx/project-handler.git'
            }
        }

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

    }

    post {
        success {
            echo 'Build Successful 🚀'
        }

        failure {
            echo 'Build Failed ❌'
        }
    }
}