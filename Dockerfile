FROM eclipse-temurin:17
LABEL mentainer = "leanhtrung97@gmail.com"
WORKDIR /app
COPY target/SocialAPI-0.0.1-SNAPSHOT.jar /app/SocialAPI.jar
ENTRYPOINT ["java", "-jar", "SocialAPI.jar"]