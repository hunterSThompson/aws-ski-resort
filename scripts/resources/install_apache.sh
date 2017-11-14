#!/bin/bash
yum update -y
yum install -y tomcat8-webapps tomcat8-docs-webapp tomcat8-admin-webapps
service tomcat8 start
chkconfig tomcat8 on
setfacl -m u:ec2-user:rwx /var/lib/tomcat8/webapps

