#!/usr/bin/env bash

set -x

# Variables
vpc=vpc-86d7d9ef
lb_name=loadbalancer-1
group_name=lb-group-1

# Create load balancer & group	
aws elbv2 create-load-balancer --name ${lb_name} --type network \
                               --subnets subnet-63f4eb0a subnet-1ba93056 subnet-056d317e
aws elbv2 create-target-group --name ${group_name} --protocol TCP \
                              --port 8080 --vpc-id ${vpc}

# Pull LB and Group ARNs
lb_arn=$(aws elbv2 describe-load-balancers --name $lb_name --query 'LoadBalancers[].LoadBalancerArn' --output text)
group_arn=$(aws elbv2 describe-target-groups --name $group_name --query 'TargetGroups[].TargetGroupArn' --output text)

# Fetch instance IDs & format them to JSON-shorthand (yuck)
instance_ids=$(aws ec2 describe-instances --filter "Name=instance-state-name,Values=running" --query "Reservations[].Instances[*].InstanceId" --output text)
id_string=""
for i in ${instance_ids}
do
    id_string="$id_string Id=$i"
done

# Register instances & create the listener
aws elbv2 register-targets --target-group-arn ${group_arn} --targets $id_string
aws elbv2 create-listener --load-balancer-arn $lb_arn --protocol TCP --port 80 \
                          --default-actions Type=forward,TargetGroupArn=$group_arn

# Output the LB DNS name
lb_dns=$(aws elbv2 describe-load-balancers --name ${lb_name} --query 'LoadBalancers[].DNSName' --output text)
echo "LB DNS name: $lb_dns"