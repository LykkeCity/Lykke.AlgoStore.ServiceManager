pipeline {
    agent any
    stages {
        stage('Clean') {
            steps {
                runWithMaven("mvn clean")

            }
        }
        stage('Validate') {
            steps {
                runWithMaven("mvn validate")

            }

        }
        stage('Compile') {
            steps {
                runWithMaven("mvn compile")
            }
        }
        stage('Test') {
            steps {
                configFileProvider([configFile(fieldId: 'bf10fa46-c5b4-444f-a2a8-b7c0226b1d98',variable: 'testEnvApplicationProperties')])  {
                    configFileProvider([configFile(fieldId: 'test-env.docker-java.properties',variable: 'testEnvDockerJavaProperties')]) {

                        load "testEnv.docker-java.properties";

                        runWithMaven("mvn -D spring.config.location=${testEnvApplicationProperties} test")
                    }
                }
            }
        }
        stage('Package') {
            steps {
                runWithMaven("mvn package")
            }
        }
        stage('Verify') {
            steps {
                runWithMaven("mvn verify")
            }
        }
        stage('Install') {
            steps {
                runWithMaven("mvn install")
            }
        }

        stage('Release') {
            steps {
                sh "mvn -P sign-artifacts release:prepare release:perform"
            }
        }


    }
}

def runWithMaven(String command) {
    withMaven(
            maven: 'M3',
            mavenLocalRepo: '.repository') {
        sh "$command"
    }
}
