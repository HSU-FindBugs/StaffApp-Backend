pipeline {
    agent any
    tools {
        jdk "java"
        gradle "gradle"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Set Permissions') {
            steps {
                script {
                    sh 'chmod +x ./gradlew'
                }
            }
        }
        stage('Install Dependencies') {
            steps {
                script {
                    sh './gradlew build'
                }
            }
        }
        stage('Build And Deploy') {
            steps {
                script {
                    sh """
                    java -jar \\
                    ./build/libs/StaffApp-Backend-0.0.1-SNAPSHOT.jar
                    """
                }
            }
        }
    }
}
