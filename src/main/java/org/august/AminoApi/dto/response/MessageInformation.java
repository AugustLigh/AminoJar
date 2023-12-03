package org.august.AminoApi.dto.response;

import org.august.AminoApi.dto.intermediate.WssChatMsg;

public class MessageInformation {
    int ndcId;
    WssChatMsg chatMessage;
    String threadId;

    public String getThreadId() {
        return threadId;
    }

    public void setThreadId(String threadId) {
        this.threadId = threadId;
    }

    public int getNdcId() {
        return ndcId;
    }

    public void setNdcId(int ndcId) {
        this.ndcId = ndcId;
    }

    public WssChatMsg getChatMessage() {
        return chatMessage;
    }

    public void setChatMessage(WssChatMsg chatMessage) {
        this.chatMessage = chatMessage;
    }
}
