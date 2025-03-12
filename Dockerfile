FROM eclipse-temurin:17
USER root
WORKDIR /app
COPY ./build/libs/station-monitoring.jar .
CMD ["java", "-jar", "station-monitoring.jar"]
