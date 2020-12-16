FROM java:8
EXPOSE 8082
ADD /target/subscription-0.0.1-SNAPSHOT.jar subscription.jar
ENTRYPOINT ["java","jar","subscription.jar"]