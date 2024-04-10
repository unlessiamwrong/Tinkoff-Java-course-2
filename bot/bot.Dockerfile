FROM openjdk:21

COPY target/bot.jar bot.jar

ENTRYPOINT ["java","-jar","bot.jar"]
