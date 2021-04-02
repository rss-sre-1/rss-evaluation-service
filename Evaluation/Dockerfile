# APPLICATION INSTRUCTIONS
FROM maven:3.6.1-jdk-8 as builder
WORKDIR /app
COPY pom.xml pom.xml
COPY src/ src/
RUN mvn clean package -Dmaven.test.skip=true

# PINPOINT INSTRUCTIONS
FROM ubuntu:20.10 as extractor
WORKDIR /pinpoint-agent
ADD https://github.com/pinpoint-apm/pinpoint/releases/download/v2.2.2/pinpoint-agent-2.2.2.tar.gz pinpoint-agent-2.2.2.tar.gz
RUN tar -xzf pinpoint-agent-2.2.2.tar.gz


FROM java:8 as runner
WORKDIR /app
COPY --from=extractor /pinpoint-agent/pinpoint-agent-2.2.2/ /pinpoint-agent
COPY pinpoint.config /pinpoint-agent/pinpoint.config


COPY --from=builder /app/target/*.jar app.jar
#COPY --from=builder /target/*.jar app.jar
ENTRYPOINT ["java", "-javaagent:/pinpoint-agent/pinpoint-bootstrap-2.2.2.jar", "-Dpinpoint.config=/pinpoint-agent/pinpoint.config", "-Dpinpoint.container=rss-evaluation", "-Dpinpoint.agentId=rss-evaluation-service", "-Dpinpoint.applicationName=rss-evaluation-service", "-jar", "app.jar"]
