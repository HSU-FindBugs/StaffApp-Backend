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
                    // 현재 작업 디렉토리 확인
                    sh 'pwd'
                    // 빌드 명령 실행
                    sh './gradlew build'
                    // 빌드된 JAR 파일 확인
                    sh 'ls -la ./build/libs/'
                }
            }
        }
        stage('Generate Firebase Config') {
            steps {
                // 자격증명을 안전하게 처리하는 블록 추가
                withCredentials([string(credentialsId: 'firebase-service-account', variable: 'FIREBASE_JSON_CONTENT')]) {
                    script {
                        // Firebase 서비스 계정 JSON 파일 생성
                        writeFile file: 'serviceAccountKey.json', text: FIREBASE_JSON_CONTENT
                    }
                }
            }
        }
        stage('Build And Deploy') {
            steps {
                script {
                    // 빌드된 JAR 파일을 실행하며 환경 변수 전달
                    sh """
                    java -jar \\
                    -DAWS_ACCESS_KEY=\${AWS_ACCESS_KEY} \\
                    -DAWS_SECRET_KEY=\${AWS_SECRET_KEY} \\
                    -DAWS_REGION=\${AWS_REGION} \\
                    -DAWS_STACK_AUTO=\${AWS_STACK_AUTO} \\
                    -DAWS_S3_BUCKET=\${AWS_S3_BUCKET} \\
                    -DAWS_ELASTIC_CACHE_URI=\${ELASTIC_CACHE_URI} \\
                    -Dfirebase.config=serviceAccountKey.json \\
                    ./build/libs/StaffApp-Backend-0.0.1-SNAPSHOT.jar
                    """
                }
            }
        }
        stage('Cleanup') {
            steps {
                script {
                    // Firebase JSON 파일 삭제 (옵션)
                    sh 'rm -f serviceAccountKey.json'
                }
            }
        }
    }
}

