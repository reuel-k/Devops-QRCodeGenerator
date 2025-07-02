pipeline {
    agent any

    tools {
        jdk 'Java 17' 
        maven 'Maven 3'
    }

    stages {
        stage('Build') {
            steps {
                dir('qr-code-generator') {
                    sh 'mvn clean package'
                }
            }
        }

        stage('Docker Build') {
            steps {
                sh 'docker build -t qr-code-generator .'
            }
        }

        stage('Run') {
            steps {
                sh 'docker rm -f qr-app || true'
                sh 'docker network rm qr-monitoring || true'
                sh 'docker network create qr-monitoring'
                sh 'docker network connect qr-monitoring graphite || true'
                sh 'docker run -d --name qr-app --network=qr-monitoring qr-code-generator'
            }
        }
    }
}

