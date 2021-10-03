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

    stage('Deploy to QA') {
      when {
        branch 'main'
        expression {BRANCH_NAME ==~ /release\/.*/}
      }
      steps {
        echo 'Deploy to Staging'
        bat './deploy.sh staging'
        echo 'Notifying the team...'
      }
    }

    stage('Deploy to Production') {
      when {
        anyOf {
          branch 'main'
          branch 'production'
        }
        
      }
      steps {
        echo 'Deploy to Production'
        input message: 'Deploy to Production?',
        ok: 'Fire away!'
        bat './deploy.sh production'
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