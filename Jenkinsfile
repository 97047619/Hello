def version
node {
       // Icheckout the repo
       //checkout scm
       version = bat script: 'mvn help:evaluate -Dexpression=project.version -q -DforceStdout', returnStdout: true
       
      /*
        //or read value from pom file, if you have youe own buildIn method
        version = readMavenPom().getVersion().replace("-SNAPSHOT", "")
       */
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
            echo 'Performance Test'
            // commenting out this next line will make it fail when using mvn clean test
            unstash(name: 'jar')
            //bat 'mvn -B -DtestFailureIgnore test || exit 0'
            //sh '# ./mvn -B gatling:execute'
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

    stage('Build Container') {
      steps {
        echo 'Build Container'
      }
    }

    stage('Deploy to Staging') {
      when {
        anyOf {
        branch 'develop'
        expression {VERSION ==~ /SNAPSHOT\/.*/}
        }
      }
      steps {
        echo 'Deploy to Staging'
        //sh './deploy.sh staging'
        bat 'deploy scm:tag -Drevision=$BUILD_NUMBER'
        bat 'deploy.bat staging'
        echo 'Notifying the team...'
      }
    }

    stage('Deploy to Production') {
      when {
         branch 'develop'
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
            archiveArtifacts artifacts: '**/*.jar', fingerprint: true
            junit 'target/surefire-reports/**/*.xml'
        }
    }
}