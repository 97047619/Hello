pipeline {
  agent any
  stages {
    stage('Build') {
      //agent {
      //    docker {
      //      image 'maven:3.8.1-adoptopenjdk-11'
      //      args '-v /root/.m2:/root/.m2'
      //  }
      //}
      steps {
        bat 'mvn -B -DskipTests clean package'
        stash name: 'jar', includes: 'target/**
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