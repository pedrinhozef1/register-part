#!/bin/bash

export AWS_ACCESS_KEY_ID="tst"
export AWS_SECRET_ACCESS_KEY="tst"
export AWS_DEFAULT_REGION="us-east-1"
export AWS_CA_BUNDLE=""

echo ":::: Iniciando a criação dos recursos ::::"

awslocal sns create-topic --name email-sender-command-local

awslocal sns create-topic --name email-sender-domain-event-local

awslocal sns create-topic --name notification-client-with-websocket-local

awslocal sns create-topic --name authentication-user-domain-event-local

awslocal sqs create-queue --queue-name notification-client-with-websocket-local

awslocal sqs create-queue --queue-name email-sender-command-local

awslocal sqs create-queue --queue-name email-sent-with-success-event-local

awslocal sns subscribe --topic-arn arn:aws:sns:us-east-1:000000000000:notification-client-with-websocket-local \
    --protocol sqs \
    --notification-endpoint arn:aws:sqs:us-east-1:000000000000:notification-client-with-websocket-local \
    --attributes RawMessageDelivery=true \
    --output text \


awslocal sns subscribe --topic-arn arn:aws:sns:us-east-1:000000000000:email-sender-command-local \
    --protocol sqs \
    --notification-endpoint arn:aws:sqs:us-east-1:000000000000:email-sender-command-local \
    --attributes RawMessageDelivery=true \
    --output text \


email_sucess=$(awslocal sns subscribe --topic-arn arn:aws:sns:us-east-1:000000000000:email-sender-domain-event-local \
    --protocol sqs \
    --notification-endpoint arn:aws:sqs:us-east-1:000000000000:email-sent-with-success-event-local \
    --attributes RawMessageDelivery=true \
    --output text)
