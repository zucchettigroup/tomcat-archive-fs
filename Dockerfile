FROM maven:3-jdk-8-slim
RUN mkdir -p /var/maven/.m2
RUN mkdir -p /tmp/tomcat-archive/target
ENV MAVEN_CONFIG=/var/maven/.m2
COPY . /tmp/tomcat-archive
WORKDIR /tmp/tomcat-archive
RUN mvn -Duser.home=/var/maven -DskipTests -Dfile.encoding=Cp1252 clean package
RUN ls -lhrt /tmp/tomcat-archive/target
RUN cp /tmp/tomcat-archive/target/tomcat-archive-fs.jar /tmp/tomcat-archive
RUN ls -lhrt /tmp/tomcat-archive
VOLUME ["/tmp/tomcat-archive/archive", "/tmp/tomcat-archive/conf"]
EXPOSE 8080
ENTRYPOINT []
CMD ["java","-jar","tomcat-archive-fs.jar"]
