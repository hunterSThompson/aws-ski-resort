package org.geobricks.rest;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.google.gson.Gson;
import org.geobricks.core.Config;
import org.springframework.stereotype.Component;

//todo: set this up with DI container.
@Component
public class QueueService {

    private AmazonSQS _sqs;
    private String _queueDns;
    private Gson _gson;

    public QueueService() {
        Config config = Config.getInstance();
        _queueDns = config.queueDns;
        _sqs = initializeSqs();
    }

    public void sendMessage(QueueMessage message) throws AmazonClientException {
        String serializedMessage = _gson.toJson(message, QueueMessage.class);
        SendMessageRequest sendRequest = new SendMessageRequest(_queueDns, serializedMessage);
        _sqs.sendMessage(sendRequest);
    }

    private AmazonSQS initializeSqs() {
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

        return AmazonSQSClientBuilder.standard()
                .withCredentials(credentialsProvider)
                .withRegion(Regions.US_EAST_2)
                .build();
    }
}