pipeline {
  agent any

  environment {
    DOCKER_REGISTRY_URL = "docker.io"
  }

  stages {
    stage('Build') {
        steps {
            sh 'mvn -B -DskipTests clean package'
        }
    }
    stage('Test') {
        steps {
            sh 'mvn test'
        }
        post {
            always {
                junit 'target/surefire-reports/*.xml'
            }
        }
    }
    
    stage('sonar-scanner') {
        steps {
            sh '''mvn clean verify sonar:sonar \
                  -Dsonar.projectKey=java \
                  -Dsonar.projectName='java' \
                  -Dsonar.host.url=http://sonarqube.local.com \
                  -Dsonar.token=sqp_3d81c7d23bb1085a377920c256246dbfd27abc62
                '''
        }
    }
    
    stage('Login repo') {
      steps {
        script {
          withCredentials([usernamePassword(credentialsId: 'docker-login-credentials', usernameVariable: 'USER_LOGIN', passwordVariable: 'TOKEN_LOGIN')]) {
            sh "docker login ${DOCKER_REGISTRY_URL} -u ${USER_LOGIN} -p ${TOKEN_LOGIN}"
          }
        }
      }
    }

    stage('Build, Push, and Deploy') {
      steps {
        sh 'docker-compose build && docker-compose push && docker-compose up -d --build'
      }
    }
    stage('Countdown') {
      steps {
        script {
          for (int i = 60; i >= 0; i--) {
              echo "เวลาที่เหลือ: ${i} วินาที"
              sleep 1
          }
          echo "หมดเวลาแล้ว!"
        }
      }
    }
    stage('Remove') {
      steps {
        script {
          sh "docker-compose down"
        }
      }
    }
  }
}
