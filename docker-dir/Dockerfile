FROM registry.cn-shenzhen.aliyuncs.com/topcode-yyz/tomcat8_x64:v1.0.0
MAINTAINER MithrandirW "yyzclc@163.com"
ENV	spring.profiles.active=production
RUN mkdir /usr/local/file
COPY website.war /usr/local/tomcat/webapps/ROOT.war
COPY file.xml /usr/local/tomcat/conf/Catalina/localhost/file.xml