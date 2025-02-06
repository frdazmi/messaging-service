package com.imza.porto.sdk.sqs.exceptions;

public class SkipTaskException extends RuntimeException {
    public SkipTaskException(String message) {
        super(message);
    }
}
