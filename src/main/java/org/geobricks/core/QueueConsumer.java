package org.geobricks.core;

import org.geobricks.rest.QueueMessage;
import org.geobricks.rest.QueueService;

import java.util.List;

public class QueueConsumer {

    public static void main(String[] args)
    {
        Config config = Config.getInstance();
        String queueDns = config.queueDns;

        QueueService queueService = new QueueService();
        for (;;) {
            List<QueueMessage> messages = queueService.getMessages();
            // do stuff with messages
        }

        // Create credential provider
        /*
        ProfileCredentialsProvider credentialsProvider = new ProfileCredentialsProvider();
        try {
            credentialsProvider.getCredentials();
        } catch (Exception e) {
            throw new AmazonClientException(
                    "Cannot load the credentials from the credential profiles file. " +
                            "Please make sure that your credentials file is at the correct " +
                            "location (~/.aws/credentials), and is in valid format.",
                    e);
        }

        // Create client
        AmazonSQS sqs = AmazonSQSClientBuilder.standard()
                .withCredentials(credentialsProvider)
                .withRegion(Regions.US_EAST_2)
                .build();


        try {
            ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(queueDns);
            List<Message> messages = sqs.receiveMessage(receiveMessageRequest).getMessages();

            for (Message message : messages) {
                String body = message.getBody();
                String receiptHandle = message.getReceiptHandle();
                sqs.deleteMessage(new DeleteMessageRequest(queueDns, receiptHandle));
            }

        } catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which means your request made it " +
                    "to Amazon SQS, but was rejected with an error response for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which means the client encountered " +
                    "a serious internal problem while trying to communicate with SQS, such as not " +
                    "being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        }
        */
    }
}

