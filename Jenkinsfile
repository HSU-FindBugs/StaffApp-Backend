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
        FIREBASE_JSON_CONTENT = credentials('firebase-service-account') // Firebase 서비스 계정 자격 증명 추가
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
        stage('Generate Firebase Config') {
            steps {
                script {
                    // Jenkins 환경 변수에서 가져온 Firebase JSON을 파일로 저장
                    writeFile file: 'serviceAccountKey.json', text: "${FIREBASE_JSON_CONTENT}"
                }
            }
        }
        stage('Build And Deploy') {
            steps {
                script {
                    sh """
                    java -Duser.timezone=Asia/Seoul -jar \\
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
                    // Firebase JSON 파일 삭제 (선택 사항)
                    sh 'rm -f serviceAccountKey.json'
                }
            }
        }
    }
}

