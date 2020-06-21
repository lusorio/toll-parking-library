FROM tomcat:9-jre11-slim


RUN rm -rf ./webapps/*

# copy the WAR bundle to tomcat
COPY ./toll-parking-library.war ./webapps/ROOT.war

CMD ["catalina.sh", "run"]
