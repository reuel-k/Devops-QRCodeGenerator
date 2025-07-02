pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                sh 'mvn clean package'
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
                sh 'docker run -d -p 1111:1111 --name qr-app --network="host" qr-code-generator'
            }
        }
    }
}
