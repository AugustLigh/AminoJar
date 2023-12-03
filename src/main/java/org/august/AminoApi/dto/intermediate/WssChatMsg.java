package org.august.AminoApi.dto.intermediate;

import org.august.AminoApi.dto.response.UserProfile;

public class WssChatMsg {
    UserProfile author;
    String content;
    String threadId;
    int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public UserProfile getAuthor() {
        return author;
    }

    public void setAuthor(UserProfile author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getThreadId() {
        return threadId;
    }

    public void setThreadId(String threadId) {
        this.threadId = threadId;
    }
}
