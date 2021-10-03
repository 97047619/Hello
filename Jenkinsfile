pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        bat 'mvn -B -DskipTests clean package'
        stash(name: 'jar', includes: 'target/**')
      }
    }

    stage('Backend') {
      parallel {
        stage('Unit') {
          steps {
            echo 'Unit Test'
            // commenting out this next line will make it fail when using mvn clean test
            //unstash(name: 'jar')
            bat 'mvn -B clean test'
            junit '**/surefire-reports/**/*.xml'
          }
        }

        stage('Performance') {
          steps {
            echo 'Performance Test'
            unstash(name: 'jar')
            bat 'mvn clean test'
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
  post {
        always {
            junit 'build/reports/**/*.xml'
        }
    }
}