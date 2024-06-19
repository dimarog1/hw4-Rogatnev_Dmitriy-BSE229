FROM maven:3.8.2-openjdk-17-slim as builder

ARG VERSION=0.0.1
WORKDIR /usr/src/app
COPY ./src ./src
COPY *.xml ./
RUN mvn package -Dversion.application=${VERSION} -DskipTests

FROM openjdk:17-jdk-alpine
ARG VERSION=0.0.1
WORKDIR /app

RUN addgroup --system jaruser \
    && adduser -S -s /bin/false -G jaruser jaruser -D -H \
    && apk add --no-cache dumb-init
COPY --chown=jaruser:jaruser \
     --from=builder /usr/src/app/target/auth-${VERSION}-SNAPSHOT.jar ./auth.jar

EXPOSE 8080
EXPOSE 6432

USER jaruser
ENTRYPOINT ["dumb-init", "java", "-jar", "-Dmyserver.bindPort=8080", "./auth.jar"]