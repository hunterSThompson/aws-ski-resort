#!/usr/bin/env bash

### Bootstrap AWS Resources

# EC2
./resources/create_ec2.sh

# ELB Network
./resources/create_lb.sh

# MySQL RDS
./resources/create_rds.sh

# SQS Queue
./resources/create_queue.sh