FROM eclipse-temurin:11-jre
RUN mkdir /opt/app
RUN mkdir /files
COPY target/shopizer.jar /opt/app
COPY SALESMANAGER.h2.db /
COPY ./files /files
CMD ["java", "-Dspring.profiles.active=docker", "-jar", "/opt/app/shopizer.jar"]