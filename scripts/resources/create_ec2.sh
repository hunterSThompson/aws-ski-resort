#!/usr/bin/env bash

set -x

# Variables
ami=ami-c5062ba0
count=1
security_groups=launch-wizard-1
key_name=kp1
user_data=file://install_apache.sh

# Create load balancer & group	
aws ec2 run-instances --image-id ${ami} --count ${count} \
                      --instance-type t2.micro --key-name ${key_name} \
                      --security-groups ${security_groups} \
                      --user-data ${user_data}

