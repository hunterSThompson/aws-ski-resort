#!/usr/bin/env bash

#endpoints=( "ec2-18-216-1-196.us-east-2.compute.amazonaws.com" )

endpoints=$(aws ec2 describe-instances --filter "Name=instance-state-name,Values=running" \
                                       --query "Reservations[].Instances[*].PublicDnsName" \
                                       --output text)

# Rename WAR file.
cp ../target/jersey-spring-example-0.1.0-SNAPSHOT.war snowman.war

for i in ${endpoints}
do
	echo "Deploying to: $i"
	scp -i kp1.pem ./snowman.war ec2-user@$i:/var/lib/tomcat8/webapps
done