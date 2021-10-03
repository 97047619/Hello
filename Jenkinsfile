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
            bat 'mvn -B test'
            junit '**/surefire-reports/**/*.xml'
          }
        }

        stage('Performance') {
          steps {
            echo 'Performance Test'
            unstash(name: 'jar')
            bat 'mvn test'
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
            archiveArtifacts artifacts: 'build/libs/**/*.jar', fingerprint: true
            junit 'target/surefire-reports/**/*.xml'
        }
    }
}