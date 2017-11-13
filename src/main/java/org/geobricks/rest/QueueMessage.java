package org.geobricks.rest;

public class QueueMessage {
    public int totalExecutionTime;
    public int queryExecutionTime;
    public String exceptionMessage;
    public boolean hasException;

    public QueueMessage(int totalExecutionTime, int queryExecutionTime, String exceptionMessage, boolean hasException) {
        this.totalExecutionTime = totalExecutionTime;
        this.queryExecutionTime = queryExecutionTime;
        this.exceptionMessage = exceptionMessage;
        this.hasException = hasException;
    }
}
