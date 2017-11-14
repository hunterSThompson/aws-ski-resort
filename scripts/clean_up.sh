#!/usr/bin/env bash

set -x

# Make the user confirm
echo ""
#read YESNO
#if [["$YESNO" != "y"]]; then
    #exit 0
#fi
read -p "Are you TOTALLY SURE you want to delete all EC2, RDS, LBs, and SQS instances? " -n 1 -r
echo    # (optional) move to a new line
if [[ ! $REPLY =~ ^[Yy]$ ]]
then
    [[ "$0" = "$BASH_SOURCE" ]] && exit 1 || return 1 # handle exits from shell or function but don't exit interactive shell
fi

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
ec2_ids=$(aws ec2 describe-instances --query 'Reservations[0].Instances[*].InstanceId' --output text)
aws ec2 terminate-instances --instance-ids ${ec2_ids}

# Delete SQS queues
queue_urls=$(aws sqs list-queues --query "QueueUrls" --output text)
for i in ${queue_urls}
do
    echo "Deleting Queue: $i"
    aws sqs delete-queue --queue-url ${i}
done