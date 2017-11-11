#!/bin/bash
yum update -y
yum install -y tomcat7-webapps tomcat7-docs-webapp tomcat7-admin-webapps
service tomcat7 start
chkconfig tomcat7 on
setfacl -m u:ec2-user:rwx /var/lib/tomcat7/webapps	

