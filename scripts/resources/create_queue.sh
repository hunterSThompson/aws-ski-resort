#!/usr/bin/env bash

set -x

#variable
queue_name=queue-1

# Create the queue
# todo: Fix this. Doesn't provide correct URL
queue_url=$(aws sqs create-queue --queue-name $queue_name --query "QueueUrl" --output text)

# Save DNS names to the config file
config_path=../conf.txt
echo "queue_url: $queue_url" >> ${config_path}