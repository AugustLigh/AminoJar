package org.august.AminoApi.dto.response;

class InnerObject {
    private int mediaType;
    private String url;
    private String tag;

    public int getMediaType() {
        return mediaType;
    }

    public void setMediaType(int mediaType) {
        this.mediaType = mediaType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}

public class MediaList {
    private InnerObject innerObject;

    public InnerObject getInnerObject() {
        return innerObject;
    }

    public void setInnerObject(InnerObject innerObject) {
        this.innerObject = innerObject;
    }
}
