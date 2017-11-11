package org.geobricks.core;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.*;
import com.sun.xml.internal.bind.v2.util.QNameMap;

import java.util.List;

public class App {
    public static void main(String[] args)
    {
        System.out.println("Hello");

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

        AmazonSQS sqs = AmazonSQSClientBuilder.standard()
                .withCredentials(credentialsProvider)
                .withRegion(Regions.US_EAST_2)
                .build();

        System.out.println("===========================================");
        System.out.println("Getting Started with Amazon SQS");
        System.out.println("===========================================\n");

        try {
            // Create a queue
            /*
            System.out.println("Creating a new SQS queue called MyQueue.\n");
            CreateQueueRequest createQueueRequest = new CreateQueueRequest("MyQueue");
            String myQueueUrl = sqs.createQueue(createQueueRequest).getQueueUrl();
            */

            String myQueueUrl = "https://sqs.us-east-2.amazonaws.com/771905060175/queue-1";

            // List queues
            System.out.println("Listing all queues in your account.\n");
            for (String queueUrl : sqs.listQueues().getQueueUrls()) {
                System.out.println("  QueueUrl: " + queueUrl);
            }
            System.out.println();

            // Send a message
            System.out.println("Sending a message to MyQueue.\n");
            sqs.sendMessage(new SendMessageRequest(myQueueUrl, "This is my message text."));

            // Receive messages
            System.out.println("Receiving messages from MyQueue.\n");
            ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(myQueueUrl);
            List<Message> messages = sqs.receiveMessage(receiveMessageRequest).getMessages();
            for (Message message : messages) {
                System.out.println("  Message");
                System.out.println("    MessageId:     " + message.getMessageId());
                System.out.println("    ReceiptHandle: " + message.getReceiptHandle());
                System.out.println("    MD5OfBody:     " + message.getMD5OfBody());
                System.out.println("    Body:          " + message.getBody());
                /*
                for (Map<String, String> entry : message.getAttributes().entrySet()) {
                    System.out.println("  Attribute");
                    System.out.println("    Name:  " + entry.getKey());
                    System.out.println("    Value: " + entry.getValue());
                }
                */
            }
            System.out.println();

            // Delete a message
            System.out.println("Deleting a message.\n");
            String messageReceiptHandle = messages.get(0).getReceiptHandle();
            sqs.deleteMessage(new DeleteMessageRequest(myQueueUrl, messageReceiptHandle));

            // Delete a queue
            /*
            System.out.println("Deleting the test queue.\n");
            sqs.deleteQueue(new DeleteQueueRequest(myQueueUrl));
            */
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
    }

    private void addMessage(String message) {

    }

    private void getMessages(String message) {

    }
}

