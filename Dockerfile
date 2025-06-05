FROM tomee:10-jre17-webprofile


RUN echo 'export CATALINA_OPTS="$CATALINA_OPTS -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=9010 -Dcom.sun.management.jmxremote.rmi.port=9010 -Dcom.sun.management.jmxremote.local.only=false -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -Djava.rmi.server.hostname=127.0.0.1"' > /usr/local/tomee/bin/setenv.sh

RUN chmod +x /usr/local/tomee/bin/setenv.sh

ADD ojdbc17.jar /usr/local/tomee/lib/
COPY build/libs/labwork3.war /usr/local/tomee/webapps/
COPY src/main/resources/META-INF/tomee.xml /usr/local/tomee/conf/
COPY src/main/resources/META-INF/context.xml /usr/local/tomee/conf/

EXPOSE 8080 9010


CMD ["catalina.sh", "run"]
