package org.august.AminoApi.dto.requests;

public class SendMsgDTO {
    final private String content;

    private int type = 0;

    final private long timestamp = System.currentTimeMillis();

    public SendMsgDTO(String content) {
        this.content = content;
    }

    public SendMsgDTO(String content, int type) {
        this.content = content;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
