FROM eclipse-temurin:17-jdk-focal
ADD target/rickandmortyv2-2.0.0.jar rickandmortyv2-2.0.0.jar
EXPOSE 9960
ENTRYPOINT ["java", "-jar", "rickandmortyv2-2.0.0.jar"]
