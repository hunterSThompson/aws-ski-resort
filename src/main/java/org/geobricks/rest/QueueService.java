package org.geobricks.rest;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.google.gson.Gson;
import org.geobricks.core.Config;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

//todo: set this up with DI container.
@Component
public class QueueService {

    private AmazonSQS _sqs;
    private String _queueDns = "https://sqs.us-east-2.amazonaws.com/771905060175/queue-1";
    private Gson _gson;

    private final String accessKey = "AKIAIIZQOBEAHOAEPPOA";
    private final String secretyKey = "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";

    public QueueService() {
        /*
        Config config = Config.getInstance();
        _queueDns = config.queueDns;
        */
        _sqs = initializeSqs();
        _gson = new Gson();
    }

    public void sendMessage(QueueMessage message) throws AmazonClientException {
        String serializedMessage = _gson.toJson(message, QueueMessage.class);
        SendMessageRequest sendRequest = new SendMessageRequest(_queueDns, serializedMessage);
        _sqs.sendMessage(sendRequest);
    }

    public <T> void sendMessage(T message) throws AmazonClientException {
        String serializedMessage = _gson.toJson(message, message.getClass());
        SendMessageRequest sendRequest = new SendMessageRequest(_queueDns, serializedMessage);
        _sqs.sendMessage(sendRequest);
    }

    public void deleteMessage(String receiptHandle) {
    }

    public List<QueueMessage> getMessages() {

        ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(_queueDns);
        List<Message> messages = _sqs.receiveMessage(receiveMessageRequest).getMessages();

        List<QueueMessage> deserializedMessages = new ArrayList<>();

        for (Message message : messages) {
            String body = message.getBody();
            QueueMessage qm = _gson.fromJson(body, QueueMessage.class);
            deserializedMessages.add(qm);

            System.out.println("query time: " + qm.queryExecutionTime);
            System.out.println("exec time: " + qm.totalExecutionTime);
            System.out.println("Has ex: " + qm.hasException);
            System.out.println("ex message: " + qm.exceptionMessage + "\n");

            String receiptHandle = message.getReceiptHandle();
            _sqs.deleteMessage(new DeleteMessageRequest(_queueDns, receiptHandle));
        }

        return deserializedMessages;
    }

    private AmazonSQS initializeSqs() {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretyKey);
        AWSStaticCredentialsProvider provider = new AWSStaticCredentialsProvider(awsCredentials);

        return AmazonSQSClientBuilder.standard()
                .withCredentials(provider)
                .withRegion(Regions.US_EAST_2)
                .build();
    }
}