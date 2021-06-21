pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                echo 'Build App'
            }
        }
        stage('Test') {
            steps {
                echo 'Test App'
            }
        }
        stage('deploy') {
            steps {
                echo 'Deploy App'
            }
        }
    }
        post
        {
            always
            {
                emailext body: 'Summary', subject: 'Pipeline Status', to: 'kripashankar2509@gmail.com'
            }
        }
    
}
