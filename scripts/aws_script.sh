aws elb create-load-balancer --load-balancer-name my-load-balancer --listeners "Protocol=HTTP,LoadBalancerPort=80,InstanceProtocol=HTTP,InstancePort=8080"  --subnets subnet-63f4eb0a subnet-056d317e subnet-1ba93056
instance_ids=$(aws ec2 describe-instances --query "Reservations[].Instances[].InstanceId" --output text)
aws elb register-instances-with-load-balancer --load-balancer-name my-load-balancer --instances $instance_ids

aws ec2 run-instances --image-id ami-c5062ba0 --count 1 --instance-type t2.micro --key-name kp1 --security-groups launch-wizard-1 --user-data "file://install_apache.sh"

; Pull DNS names
aws ec2 describe-instances --query "Reservations[].Instances[].PublicDnsName"


; Create LB & group
aws elbv2 create-load-balancer --name lb-v2 --security-groups launch-wizard-1 --subnets subnet-63f4eb0a subnet-63f4eb0a subnet-056d317e  --query "LoadBalancers[0].LoadBalancerArn" --output text
aws elbv2 create-target-group --name apache-group --protocol HTTP --port 8080 --vpc-id vpc-86d7d9ef

; Register VMs w/ group
aws elbv2 register-targets --target-group-arn arn:aws:elasticloadbalancing:us-east-2:771905060175:targetgroup/apache-group/077587067a7beab2 --targets Id=i-0582fec09ab4cd7f7

; Add the listener
aws elbv2 create-listener --load-balancer-arn arn:aws:elasticloadbalancing:us-east-2:771905060175:loadbalancer/app/lb-v2/f28a77ddf33aa9b5 --protocol HTTP --port 80 --default-actions Type=forward,TargetGroupArn=arn:aws:elasticloadbalancing:us-east-2:771905060175:targetgroup/apache-group/077587067a7beab2

; Get target-group arn
elbv2 describe-target-groups --name apache-group --query "TargetGroups[*].[TargetGroupArn]" --output text


vpc=vpc-86d7d9ef
lbarn=$(aws elbv2 create-load-balancer --name lb-v2 --subnets subnet-63f4eb0a subnet-63f4eb0a subnet-056d317e  --query "LoadBalancers[0].LoadBalancerArn" --output text)
group_arn=$(aws elbv2 create-target-group --name apache-group-2 --protocol HTTP --port 8080 --vpc-id $vpc --query "'TargetGroups[?TargetGroupName==`apache-group`].TargetGroupArn'")

aws ec2 run-instances --image-id ami-c5062ba0 --count 1 --instance-type t2.micro --key-name kp1 --security-groups launch-wizard-1


; Create RDS MySQL db
aws rds create-db-instance --db-instance-identifier sg-cli-test --allocated-storage 20 --db-instance-class db.t2.micro --engine mysql --master-username myawsuser --master-user-password myawsuser