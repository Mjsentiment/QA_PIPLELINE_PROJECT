pipeline {
    agent any

    tools {
        maven 'Maven3'    // Ensure Maven is installed in Jenkins
        jdk 'jdk21'       // Adjust based on project
    }

    triggers {
        upstream(upstreamProjects: 'DEVELOPER_CODE_REVIEW', threshold: hudson.model.Result.SUCCESS)
    }

    stages {
        stage('Checkout') {
            steps {
                echo "Checking out source code..."
                checkout scm
            }
        }

        stage('Unit Testing') {
            steps {
                echo "Running Unit Tests..."
                sh 'mvn clean test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('Code Coverage Metrics') {
            steps {
                echo "Generating Cobertura coverage reports..."
                sh 'mvn cobertura:cobertura'
            }
            post {
                success {
                    cobertura autoUpdateHealth: false, autoUpdateStability: false,
                              coberturaReportFile: '**/target/site/cobertura/coverage.xml',
                              failNoReports: false, lineCoverageTargets: '80, 0, 0',
                              maxNumberOfBuilds: 0, onlyStable: false, sourceEncoding: 'ASCII', zoomCoverageChart: false
                }
            }
        }

        stage('Package Build') {
            steps {
                echo "Packaging the application..."
                sh 'mvn clean package'
            }
            post {
                success {
                    archiveArtifacts artifacts: 'target/*.jar, target/*.war', fingerprint: true
                }
            }
        }

        stage('Optional: Selenium Web Tests') {
            when { expression { return fileExists('tests/selenium') } }
            steps {
                echo "Running Selenium Web Tests..."
                sh 'mvn test -Dtest=WebUITests'
            }
        }

        stage('Optional: Load Testing') {
            when { expression { return fileExists('tests/load_test.jmx') } }
            steps {
                echo "Running JMeter Load Tests..."
                sh '''
                jmeter -n -t tests/load_test.jmx -l target/load_results.jtl -e -o target/load_report
                '''
                publishHTML([reportDir: 'target/load_report', reportFiles: 'index.html', reportName: 'Load Test Report'])
            }
        }
    }

    post {
        success {
            echo 'Pipeline executed successfully!'
        }
        failure {
            echo 'Pipeline failed. Please check logs.'
        }
    }
}
