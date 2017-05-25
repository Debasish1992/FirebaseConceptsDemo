package com.example.debasishkumardas.firebaseconceptsdemo.model;

/**
 * Created by Debasish Kumar Das on 5/25/2017.
 */
public class MessageModel {

    String messageTitle,
            messageContent,
            messagePassword,
            messageOccasion,
            messageType,
            messageId;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getMessagePassword() {
        return messagePassword;
    }

    public void setMessagePassword(String messagePassword) {
        this.messagePassword = messagePassword;
    }

    public String getMessageOccasion() {
        return messageOccasion;
    }

    public void setMessageOccasion(String messageOccasion) {
        this.messageOccasion = messageOccasion;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }
}
