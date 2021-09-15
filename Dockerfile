# syntax=docker/dockerfile:1

FROM maven:3-jdk-8

ENV HOME=/home/usr/app

RUN mkdir -p $HOME

WORKDIR $HOME

# 1. add pom.xml only here

ADD pom.xml $HOME

# 2. start downloading dependencies

RUN ["/usr/local/bin/mvn-entrypoint.sh", "mvn", "verify", "clean", "--fail-never"]

# 3. add all source code and start compiling

ADD . $HOME

RUN ["mvn", "package"]

CMD ["java", "-jar", "target/App-1.0-SNAPSHOT.jar", "server", "config.yml"]

