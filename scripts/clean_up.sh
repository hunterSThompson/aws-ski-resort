#!/usr/bin/env bash

set -x

# Variables
lb_name=loadbalancer-1

# Delete load-balancers
lb_arns=$(aws elbv2 describe-load-balancers --query 'LoadBalancers[*].LoadBalancerArn' --output text)
for i in ${lb_arns}
do
    echo "Deleting LB: $i"
    aws elbv2 delete-load-balancer --load-balancer-arn ${i}
done

# Delete RDS instances
rds_ids=$(aws rds describe-db-instances --query "DBInstances[*].DBInstanceIdentifier" --output text)
for i in ${rds_ids}
do
    echo "Deleting RDS instance: $i"
    aws rds delete-db-instance --skip-final-snapshot --db-instance-identifier ${i}
done

# Delete EC2 instances
