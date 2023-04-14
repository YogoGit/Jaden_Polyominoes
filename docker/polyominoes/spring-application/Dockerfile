FROM eclipse-temurin:17-jre-alpine
RUN mkdir /app
WORKDIR /app

RUN wget https://github.com/jbathon/Polyominoes/releases/download/v0.0.1-alpha/polyominoes-0.0.1-alpha.jar

# Switch the server port to 8080
EXPOSE 8080

COPY /setup-app.sh /setup-app.sh
RUN chmod +x /setup-app.sh
ENTRYPOINT ["/setup-app.sh"]

CMD ["java", "-jar", "-Dserver.port=8080", "polyomineos-0.0.1.jar"]