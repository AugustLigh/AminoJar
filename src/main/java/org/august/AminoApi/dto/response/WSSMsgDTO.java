package org.august.AminoApi.dto.response;

public class WSSMsgDTO {
    int t;
    MessageInformation o;

    public int getT() {
        return t;
    }

    public void setT(int t) {
        this.t = t;
    }

    public MessageInformation getO() {
        return o;
    }

    public void setO(MessageInformation o) {
        this.o = o;
    }
}
