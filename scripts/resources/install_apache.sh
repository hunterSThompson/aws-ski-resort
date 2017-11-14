#!/bin/bash
yum update -y
sudo yum install java-1.8.0
sudo yum remove java-1.7.0-openjdk
yum install -y tomcat8-webapps tomcat8-docs-webapp tomcat8-admin-webapps
service tomcat8 start
chkconfig tomcat8 on
setfacl -m u:ec2-user:rwx /var/lib/tomcat8/webapps
