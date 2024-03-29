def version
node {
       version = bat script: 'mvn help:evaluate -Dexpression=project.version -q -DforceStdout', returnStdout: true
    }

pipeline {
  agent any
  parameters {
        string(name: 'VERSION', defaultValue: version, description: 'Current POM version')
    }
  stages {
    stage('1') {
            steps {
                echo "Params : ${params}"
            }
    }
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
            try {
              echo 'Performance Test'
              // commenting out this next line will make it fail when using mvn clean test - for testing fail scenario
              unstash(name: 'jar')
              //bat 'mvn -B -DtestFailureIgnore test || exit 0'
              //bat '# ./mvn -B gatling:execute'
            } catch (Exception err) {
              unstable 'Performance tests failed'
            }
            })
       }
    }

    stage('Frontend') {
      //agent {docker 'node:alpine'}
      steps {
        echo 'Frontend'
        bat 'node --version'
        //bat 'yarn install'
        //bat 'yarn global add gulp-cli'
        //bat 'gulp test'
      }
    }

    stage('Static Analysis') {
      steps {
        echo 'Static'
      }
    }

   stage('Deliver to Development') {
     when {
       anyOf {
         branch 'develop'
         branch 'feature'
         branch 'release'
       }
     }
       steps {
         //sh './jenkins/scripts/deliver-to-development.sh'
         echo 'Deliver to development'
       }
    }

    stage('Deploy to Staging') {
      when {
        anyOf {
        branch 'release'
        //expression {VERSION ==~ /SNAPSHOT\/.*/}
        expression { params.VERSION == 'SNAPSHOT' }
        }
      }
      steps {
        echo 'Deploy to Staging'
        //sh './deploy.sh staging'
        //bat 'deploy scm:tag -Drevision=$BUILD_NUMBER'
        bat 'deploy.bat staging'
        echo 'Notifying the team...'
      }
    }

    stage('Deploy to Production') {
      when {
        expression { params.VERSION == 'RELEASE' }
      }
      steps {
        echo 'Deploy to Production'
        input message: 'Deploy to Production?',
        ok: 'Yes'
        //sh './deploy.sh production'
        bat 'deploy.bat production'
        echo 'Notifying the team...'
      }
    }

  }
  post {
        always {
            echo 'This will always run'
            archiveArtifacts artifacts: '**/*.jar', fingerprint: true
            junit 'target/surefire-reports/**/*.xml'
        }
        success {
            echo 'This will run only if successful'
        }
        failure {
            echo 'This will run only if failed'
        }
        unstable {
            echo 'This will run only if the run was marked as unstable'
        }
        changed {
            echo 'This will run only if the state of the Pipeline has changed'
            echo 'For example, if the Pipeline was previously failing but is now successful'
        }
    }
}