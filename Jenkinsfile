pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        sh 'cd app; mvn -B -DskipTests package'
      }
    }

    stage('Backend') {
      parallel {
        stage('Unit') {
          steps {
            echo 'Unit'
          }
        }

        stage('Performance') {
          steps {
            echo 'Performance'
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