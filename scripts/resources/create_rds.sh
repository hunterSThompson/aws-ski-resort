#!/usr/bin/env bash

set -x

# Variables
main_db_name=ski-database-2
log_db_name=ski-log-db-2
db_username=myawsuser
db_password=myawsuser

#3queue_url=$(aws sqs create-queue --queue-name $queue_name --query "QueueUrl" --output_text)

# Create the DBs
aws rds create-db-instance --db-instance-identifier ${main_db_name} \
                           --allocated-storage 20 --db-instance-class db.t2.small --engine mysql \
                           --master-username ${db_username} --master-user-password ${db_password}

# Create the logging DB
aws rds create-db-instance --db-instance-identifier ${log_db_name} \
                           --allocated-storage 20 --db-instance-class db.t2.small --engine mysql \
                           --master-username ${db_username} --master-user-password ${db_password}


# Query the DNS name's of both DBs
main_db_dns=$(aws rds describe-db-instances --db-instance-identifier ${main_db_name} \
                                            --query "DBInstances[0].Endpoint.Address" --output text)
log_db_dns=$(aws rds describe-db-instances --db-instance-identifier ${log_db_name}
                                           --query "DBInstances[0].Endpoint.Address" --output text)

# Save DNS names to the config file
config_path=../conf.txt
echo "main_db_dns: $main_db_dns" >> ${config_path}
echo "log_db_dns: $log_db_dns" >> ${config_path}
