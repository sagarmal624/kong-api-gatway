FROM openjdk:8
ADD build/libs/transaction.war  transaction.war
EXPOSE 8091
ENTRYPOINT ["java","-jar","transaction.war"]