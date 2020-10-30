FROM openjdk:14-jdk-alpine
LABEL source="https://github.com/arduini/data-analysis" \

ADD ./build/libs/*.jar /app.jar

ENTRYPOINT java -jar /app.jar