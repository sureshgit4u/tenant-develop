#!/usr/bin/env groovy

def accountConf = [
  "staging": "*******",
  "sbox-internal": "*******",
  "prod-internal" : "*******"
]

def repoConf = [
  "staging": "staging-tenant",
  "sbox-internal": "sbox-tenant",
  "prod-internal": "production-tenant"
]

def branchConf = [
  "staging": "env/staging",
  "sbox-internal" : "env/sbox",
   "prod-internal" : "env/production"
]

void setParam(String paramName, String paramValue) {
  def newParams = []
  newParams.add(new StringParameterValue(paramName, paramValue))
  $build().addOrReplaceAction($build().getAction(ParametersAction.class).createUpdated(newParams))    
}
properties([
	parameters([
        string(defaultValue: "", description: 'Which Git Branch to clone?', name: 'GIT_BRANCH'),
        string(defaultValue: "", description: 'AWS Account Number?', name: 'ACCOUNT'),
        string(defaultValue: "", description: 'AWS ECR Repository where built docker images will be pushed.', name: 'ECR_REPO_NAME'),
        string(defaultValue: "git@gitlab.lazypay.net:cashback/rule_engine.git", description: 'Git repo name?', name: 'Git_repo_name'),
        choice(choices: ['staging','sbox-internal','prod-internal'], description: 'Profile name of the Environment?', name: 'Profile'),
        booleanParam(defaultValue: true,description: 'Skip tests while building?', name: 'SkipBuildTests')
	])
])
try {

  stage('Clone Repo'){
    node('master'){
      cleanWs()
      
      if(params.GIT_BRANCH == ""){
        setParam("GIT_BRANCH",branchConf.get(params.Profile));
      }
      if(params.ACCOUNT == ""){
        setParam("ACCOUNT",accountConf.get(params.Profile));
      }
      if(params.ECR_REPO_NAME == ""){
        setParam("ECR_REPO_NAME",repoConf.get(params.Profile));
      }
      
      checkout([$class: 'GitSCM', branches: [[name: '*/$GIT_BRANCH']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[url: '${Git_repo_name}']]])
    }
   }
   
   stage('Build Maven'){
    node('master'){
      withMaven(maven: 'maven3.6'){
       if (params.SkipBuildTests)
        sh "mvn clean package -Dmaven.test.skip -DskipTests"
       else
        sh "mvn clean package"
       sh "mkdir target/dependency"
       sh "(cd target/dependency; unzip ../*.jar)"
      } 
    }
  }        
   
   stage('Build Docker Image') {
    node('master'){
     sh "\$(aws ecr get-login --no-include-email --region ap-south-1 --profile ${Profile})"
     GIT_COMMIT_ID = sh (
      script: 'git log -1 --pretty=%H',
      returnStdout: true
     ).trim()
     TIMESTAMP = sh (
      script: 'date +%Y%m%d%H%M%S',	
      returnStdout: true																			 ).trim()
     echo "Git commit id: ${GIT_COMMIT_ID}"																
     IMAGETAG="${GIT_COMMIT_ID}-${TIMESTAMP}"
     sh "docker build -t ${ACCOUNT}.dkr.ecr.ap-south-1.amazonaws.com/${ECR_REPO_NAME}:${IMAGETAG} ."
     sh "docker push ${ACCOUNT}.dkr.ecr.ap-south-1.amazonaws.com/${ECR_REPO_NAME}:${IMAGETAG}"
    }
   }

  stage('Deploy on k8s') {
    node('master'){
      withEnv(["KUBECONFIG=${JENKINS_HOME}/.kube/${Profile}","IMAGE=${ACCOUNT}.dkr.ecr.ap-south-1.amazonaws.com/${ECR_REPO_NAME}:${IMAGETAG}"]){
      sh "sed -i 's|IMAGE|${IMAGE}|g' k8s/tenant-deployment.yaml"
      sh "kubectl apply -f k8s"
      DEPLOYMENT = sh (
        script: 'cat k8s/tenant-deployment.yaml | yq -r .metadata.name',
	returnStdout: true
     ).trim()
     echo "Creating k8s resources..."
     sleep 360
     DESIRED= sh (
       script: "kubectl get deployment/$DEPLOYMENT | awk '{print \$3}' | grep -v UP-TO-DATE",
       returnStdout: true
      ).trim()
     CURRENT= sh (
       script: "kubectl get deployment/$DEPLOYMENT | awk '{print \$4}' | grep -v AVAILABLE",
       returnStdout: true
      ).trim()
     if (DESIRED.equals(CURRENT)) {
       currentBuild.result = "SUCCESS"
       return
     } else {
       error("Deployment Unsuccessful.")
     }
    }
   }
  }
 }
 catch (err){
   currentBuild.result = "FAILURE"
    throw err
 }

