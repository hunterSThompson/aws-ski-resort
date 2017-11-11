#!/usr/bin/env bash

set -x

#variable
queue_name=queue-1

# Create the queue
queue_url=$(aws sqs create-queue --queue-name $queue_name --query "QueueUrl" --output_text)

# Export queue URL to environment variable