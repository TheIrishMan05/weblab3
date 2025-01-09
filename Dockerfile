FROM tomee:10-jre17-webprofile

ADD ojdbc17.jar /usr/local/tomee/lib/

COPY build/libs/labwork3.war /usr/local/tomee/webapps/

COPY src/main/resources/META-INF/tomee.xml /usr/local/tomee/conf/
COPY src/main/resources/META-INF/context.xml /usr/local/tomee/conf/

EXPOSE 8080

CMD ["catalina.sh", "run"]