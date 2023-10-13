FROM eclipse-temurin:17-jdk-focal
ADD target/rickandmorty-2.1.0.jar rickandmorty-2.1.0.jar
EXPOSE 9960
ENTRYPOINT ["java", "-jar", "rickandmorty-2.1.0.jar"]
