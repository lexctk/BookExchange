package com.bookexchange.mongodb.model;

import org.bson.types.ObjectId;

public class PrivateMessage {

    private ObjectId _id;
    private String message;

    public PrivateMessage (ObjectId _id, String message) {
        this._id = _id;
        this.message = message;
    }

    public ObjectId get_id() {
        return _id;
    }

    public String getMessage() {
        return message;
    }
}