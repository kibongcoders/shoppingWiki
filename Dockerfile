FROM amazoncorretto:17
RUN mkdir /app
ADD /build/libs/shoppingWiki-0.0.1-SNAPSHOT.war /app
CMD [ "java", "-jar", "/app/shoppingWiki-0.0.1-SNAPSHOT.war" ]