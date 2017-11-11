endpoints=( "ec2-18-216-1-196.us-east-2.compute.amazonaws.com" )

for i in "${endpoints[@]}"
do
	echo "Deploying to: $i\n"	
	scp -i kp1.pem deploy.sh ec2-user@$i:/var/lib/tomcat7/webapps
done