FROM maven:3.9-eclipse-temurin-21-alpine AS build
COPY . /home/app
WORKDIR /home/app
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
# Adding wget for the health check
RUN apk --no-cache add wget
COPY --from=build /home/app/target/bpdm-certificate-management.jar /usr/local/lib/bpdmCertificate/app.jar
RUN apk update && apk upgrade --no-cache libssl3 libcrypto3
ARG USERNAME=bpdmCertificate
ARG USERID=10001
ARG GID=10002
RUN addgroup -g $GID -S $USERNAME
RUN adduser -u $USERID -S $USERNAME $USERNAME
USER $USERNAME
WORKDIR /usr/local/lib/bpdmCertificate
EXPOSE 8086
# Health check instruction
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
  CMD wget --quiet --tries=1 --spider http://localhost:8086/actuator/health || exit 1
ENTRYPOINT ["java","-jar","app.jar"]