package com.life.event.www.eventlife.broadcast;

/**
 * Created by xu_s on 6/1/17.
 */

public class BroadcastMessage {
    private String message;
    private String author;

    // Default constructor is required for Firebase object mapping
    @SuppressWarnings("unuser")
    public BroadcastMessage(){}

    public BroadcastMessage(String message, String author) {
        this.message = message;
        this.author = author;
    }

    public String getMessage() {
        return message;
    }

    public String getAuthor() {
        return author;
    }
}
