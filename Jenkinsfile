properties([
	parameters([
        string(defaultValue: "master", description: 'Which Git Branch to clone?', name: 'GIT_BRANCH'),
        string(defaultValue: "https://github.com/sureshgit4u/tenant-develop.git", description: 'Git repo name?', name: 'GIT_REPO'),
        booleanParam(defaultValue: true,description: 'Skip tests while building?', name: 'SkipBuildTests')
	])
])

node {
    def mvnHome = tool 'M3'
    stage('Preparation') { // for display purposes
        // Get some code from a GitHub repository
        //git  'https://github.com/jglick/simple-maven-project-with-tests.git'
        git branch: "${params.GIT_BRANCH}", url: "https://github.com/sureshgit4u/tenant-develop.git"
        // Get the Maven tool.
        // ** NOTE: This 'M3' Maven tool must be configured
        // **       in the global configuration.

    }
    stage('Build') {
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
}
