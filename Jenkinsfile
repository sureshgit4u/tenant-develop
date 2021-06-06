properties([
	parameters([
        string(defaultValue: "master", description: 'Which Git Branch to clone?', name: 'GIT_BRANCH'),
        string(defaultValue: "https://github.com/sureshgit4u/tenant-develop.git", description: 'Git repo name?', name: 'GIT_REPO'),
        string(defaultValue: "579926917988", description: 'AWS Account Number?', name: 'AWS_ACCOUNT'),
        string(defaultValue: "tenent-demo", description: 'AWS ECR Repository where built docker images will be pushed.', name: 'AWS_ECR_NAME')
	])
])

node {
    def mvnHome = tool 'M3'
    stage('Git Checkout') {
        git branch: "${params.GIT_BRANCH}", url: "${params.GIT_REPO}"
    }
    stage('Maven Build') {
        // Run the maven build
        withEnv(["MVN_HOME=$mvnHome"]) {
            if (isUnix()) {
                sh '"$MVN_HOME/bin/mvn" clean package -Dmaven.test.skip -DskipTests'
            } else {
                bat(/"%MVN_HOME%\bin\mvn" clean package -Dmaven.test.skip -DskipTests/)
            }
        }
    }
    stage('Results') {
        //junit '**/target/surefire-reports/TEST-*.xml'
        archiveArtifacts 'target/*.jar'
    }
    stage('AWS Dockerized')
    {
        // AWS Push
       def GIT_COMMIT_ID = bat (script: "@echo off | git rev-parse --short HEAD",returnStdout: true).trim()
       echo "Git commit id: ${GIT_COMMIT_ID}"
       echo "docker build -t ${params.AWS_ACCOUNT}.dkr.ecr.us-east-1.amazonaws.com/${params.AWS_ECR_NAME}:${GIT_COMMIT_ID} ."
       echo "https://" + params.AWS_ACCOUNT + ".dkr.ecr.us-east-1.amazonaws.com"

       docker.withRegistry("https://" + params.AWS_ACCOUNT + ".dkr.ecr.us-east-1.amazonaws.com", 'ecr:us-east-1:motows.aws.credentials')
       {
           def customImage = docker.build(params.AWS_ACCOUNT + ".dkr.ecr.us-east-1.amazonaws.com/" + params.AWS_ECR_NAME + ":" + GIT_COMMIT_ID)
           customImage.push()
       }
       bat("docker rmi -f ${params.AWS_ACCOUNT}.dkr.ecr.us-east-1.amazonaws.com/${params.AWS_ECR_NAME}:${GIT_COMMIT_ID}")
    }

}