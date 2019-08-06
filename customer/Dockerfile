FROM openjdk:8
ADD build/libs/customer.war  customer.war
EXPOSE 8090
ENTRYPOINT ["java","-jar","customer.war"]