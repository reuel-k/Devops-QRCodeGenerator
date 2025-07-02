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
                sh 'docker network rm qr-monitoring || true'
		sh 'docker network create qr-monitoring'
		sh 'docker network connect qr-monitoring graphite'
		sh 'docker run -d --name qr-app --network=qr-monitoring qr-code-generator'
            }
        }
    }
}
