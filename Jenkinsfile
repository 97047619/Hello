pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        bat 'mvn clean'
      }
    }

    stage('Backend') {
      parallel {
        stage('Unit') {
          steps {
            echo 'Unit'
            sh 'mvn clean'
          }
        }

        stage('Performance') {
          steps {
            echo 'Performance'
            sh 'mvn clean'
          }
        }

      }
    }

    stage('Frontend') {
      steps {
        echo 'Frontend'
      }
    }

    stage('Static Analysis') {
      steps {
        echo 'Static'
      }
    }

    stage('Deploy') {
      steps {
        echo 'Deploy'
      }
    }

  }
}