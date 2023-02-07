pipeline{ // the entire Jenkins Job needs to go inside the pipeline section

    agent{
        //this is where we tell Jenkins what agent to use for this build (reference to jenkins value file)
        kubernetes{
            // this tells Jenkins to use the pod called "devops" we defined in the jenkins-values.yaml file
            // which will give us access to the docker commands we need to build/push our docker image
            inheritFrom "devops"
        }
    }

    environment{
        // any environment variables we want to use can go in here
        // I recommand setting variables for the docker registry (which doubles as the image name)
        // and a variable to represent the image itself
        DEVOPS_REGISTRY='tjohns96rev/project-two'
        DEVOPS_IMAGE=''
    }

    stages{
        // this is where the steps of the job will be defined

        stage("Build and push docker image"){
            // steps is where the actual commands go
            steps{
                echo "Print something to console"
                container('docker'){
                        script{
                        // the script section is sometimes needed when using functions provided by Jenkins plugins
                        //
                        DEVOPS_IMAGE= docker.build(DEVOPS_REGISTRY,".") 
                        // if docker file is store in sre file then second prameter should bd "src"
                        // withRegistry(repo location empty string if docker hub, docker credentials)
                        docker.withRegistry("", 'docker-creds'){
                            DEVOPS_IMAGE.push("latest")
                        } //docker-creds is the credentical id when you created yours not the same one
                    }
                }
                
            }
        }
    }
}