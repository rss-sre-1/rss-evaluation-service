#!/bin/bash
aws ecr get-login-password --profile sre | docker login --username AWS --password-stdin 855430746673.dkr.ecr.us-east-1.amazonaws.com;
git pull;

docker build -t 855430746673.dkr.ecr.us-east-1.amazonaws.com/matt-oberlies-sre-p3-rss-evaluation:latest ./Evaluation/;
docker push 855430746673.dkr.ecr.us-east-1.amazonaws.com/matt-oberlies-sre-p3-rss-evaluation:latest;


./kubectl.sh delete deployment rss-evaluation-deployment;
./kubectl.sh apply -f rss-evaluation-manifests/rss-evaluation-deployment.yml;