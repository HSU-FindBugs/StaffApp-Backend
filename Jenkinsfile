pipeline {
    agent any
    tools {
        jdk "java"
        gradle "gradle"
    }
    environment {
            AWS_S3_BUCKET = "${AWS_S3_BUCKET}"
            AWS_STACK_AUTO = "${AWS_STACK_AUTO}"
            AWS_REGION = "${AWS_REGION}"
            AWS_ACCESS_KEY = "${AWS_ACCESS_KEY}"
            AWS_SECRET_KEY = "${AWS_SECRET_KEY}"
            ELASTIC_CACHE_URI = "${ELASTIC_CACHE_URI}"
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

        stage('Build And Deploy') {
            steps {
                script {
                    sh """
                    java -jar \\
                    -DAWS_ACCESS_KEY=\${AWS_ACCESS_KEY} \\
                    -DAWS_SECRET_KEY=\${AWS_SECRET_KEY} \\
                    -DAWS_REGION=\${AWS_REGION} \\
                    -DAWS_STACK_AUTO=\${AWS_STACK_AUTO} \\
                    -DAWS_S3_BUCKET=\${AWS_S3_BUCKET} \\
                    -DAWS_ELASTIC_CACHE_URI=\${ELASTIC_CACHE_URI} \\
                    ./build/libs/StaffApp-Backend-0.0.1-SNAPSHOT.jar
                    """
                }
            }
        }
    }
}
