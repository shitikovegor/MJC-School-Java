#!groovy
properties([disableConcurrentBuilds(), pipelineTriggers([githubPush()])])

pipeline {
    agent any

    triggers {
        pollSCM('* * * * *')
    }

    stages {
        stage("Checkout") {
            steps {
                git branch: 'module_6', changelog: true, poll: true, 
                        url: 'https://github.com/shitikovegor/MJC-School-Java.git'
            }
        }

        stage("Build, Test") {
            steps {
                dir('gift_certificates_system') {
                    script {
                        try {
                            sh 'gradle clean build codeCoverageReport'
                        } finally {
                        junit '**/build/test-results/**/*.xml'
                        }
                    }
                }
            }
        }

//        stage("Build, Test") {
//            steps {
//                dir('gift_certificates_system') {
//                    sh 'gradle clean build codeCoverageReport'
//                }
//            }
//        }

        stage("SonarQube") {
            environment {
                scannerHome = tool 'sonarqube'
            }
            steps {
                def scannerHome = tool 'sonarqube';
                dir('gift_certificates_system') {
                    withSonarQubeEnv('sonarqube') {
                        sh "${env.scannerHome}/bin/sonar-scanner"
                    }
                }
            }
        }

        stage("Deploy") {
            steps {
                deploy adapters: [tomcat9(credentialsId: 'tomcat',
                        path: '', url: 'http://localhost:8080/')],
                        contextPath: '/', onFailure: false, war: '**/*.war'
            }
        }
    }
    post {
        always {
            echo 'Build completed'
        }
        success {
            echo 'Build successful'
        }
        failure {
            echo 'Build failed'
        }
    }
}