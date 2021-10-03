pipeline {
  agent any
  stages {
    stage('Build') {
      //agent {
      //  docker {
      //    image 'maven:3.8.1-adoptopenjdk-11'
      //    args '-v /root/.m2:/root/.m2'
      //}
      //}
      steps {
        bat 'mvn -B -DskipTests clean package'
        stash(name: 'jar', includes: 'target/**')
      }
    }

    stage('Backend') {
      //agent {
      //  docker {
      //    image 'maven:3.8.1-adoptopenjdk-11'
      //    args '-v /root/.m2:/root/.m2'
      //}
      //}
       steps {
         parallel (
          'Unit': {
            echo 'Unit Test'
            // commenting out this next line will make it fail when using mvn clean test
            unstash(name: 'jar')
            bat 'mvn -B -DtestFailureIgnore test || exit 0'
            junit '**/surefire-reports/**/*.xml'
          },
          'Performance': {
            echo 'Performance Test'
            // commenting out this next line will make it fail when using mvn clean test
            unstash(name: 'jar')
            bat 'mvn -B -DtestFailureIgnore test || exit 0'
            junit '**/surefire-reports/**/*.xml'
            })
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
            archiveArtifacts artifacts: '**/*.jar', fingerprint: true
            junit 'target/surefire-reports/**/*.xml'
        }
    }
}