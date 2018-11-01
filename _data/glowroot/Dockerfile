FROM svenruppert/maven-3.5-jdk-openjdk-10:1.10.2
LABEL maintainer="Sven Ruppert sven.ruppert@gmail.com" description="Maven && Glowroot & Open JDK 10"

WORKDIR /opt

ENV GLOWROOT 0.11.1

RUN curl -LO https://github.com/glowroot/glowroot/releases/download/v${GLOWROOT}/glowroot-${GLOWROOT}-dist.zip
RUN apt-get update && apt-get update && apt-get install unzip
RUN unzip glowroot-${GLOWROOT}-dist.zip

ENV MAVEN_OPTS="-javaagent:/opt/glowroot/glowroot.jar"

CMD ["mvn"]