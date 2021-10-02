pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        bat 'mvn -B -DskipTests clean package'
      }
    }

    stage('Backend') {
      parallel {
        stage('Unit') {
          steps {
            echo 'Unit'
            bat 'mvn clean'
          }
        }

        stage('Performance') {
          steps {
            echo 'Performance'
            bat 'mvn clean'
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