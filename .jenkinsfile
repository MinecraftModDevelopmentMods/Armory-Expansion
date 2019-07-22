pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                git pull
                gradle clean build
                echo 'Building..'
            }
        }
    }
}