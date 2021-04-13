#!/bin/bash
Namespace="rss-evaluation"

ImageRegistry="855430746673.dkr.ecr.us-east-1.amazonaws.com";
ImageName="matt-oberlies-sre-p3-rss-evaluation";
ImageTag="latest";
DockerfileLocation="/Evaluation/";

DeploymentName="rss-evaluation-deployment";
########################### The relative path + filename.
DeploymentManifestLocation="rss-evaluation-manifests/rss-evaluation-deployment.yaml";

aws ecr get-login-password --profile sre | docker login --username AWS --password-stdin 855430746673.dkr.ecr.us-east-1.amazonaws.com;
git pull;


docker build -t $ImageRegistry/$ImageName:$ImageTag .$DockerfileLocation;
docker push $ImageRegistry/$ImageName:$ImageTag;


kubectl -n $Namespace delete deployment $DeploymentName;
kubectl -n $Namespace apply -f $DeploymentManifestLocation;
