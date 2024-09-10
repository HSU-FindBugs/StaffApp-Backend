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
                    sh 'pwd' // 현재 작업 디렉토리 확인
                    sh './gradlew build'
                }
            }
        }

        sstage('Build And Deploy') {
             steps {
                 script {
                     sh """
                     java -jar \\
                     /var/lib/jenkins/workspace/bugFindProject/build/libs/StaffApp-Backend-0.0.1-SNAPSHOT.jar
                     """
                 }
             }
         }

    }
}
