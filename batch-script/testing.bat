@echo off
FOR /F "tokens=*" %%g IN ('git rev-parse --short HEAD') do (SET VAR=%%g)
echo %VAR%	
@echo on


echo "docker build -t ${params.AWS_ACCOUNT}.dkr.ecr.us-east-1.amazonaws.com/${params.AWS_ECR_NAME}:" + GIT_COMMIT_ID +" ."

docker build -t 579926917988.dkr.ecr.us-east-1.amazonaws.com/tenent-demo:C:\J E N K I N S\.jenkins\workspace\git-maven-docker-pipeline>git rev-parse --short HEAD 
ee155e6 .


docker.withRegistry('https://579926917988.dkr.ecr.us-east-1.amazonaws.com', 'ecr:us-east-1:demo-ecr-credentials') {
    docker.image('demo').push('latest')
  }
}