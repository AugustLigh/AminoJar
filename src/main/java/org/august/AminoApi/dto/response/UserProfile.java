package org.august.AminoApi.dto.response;

import java.util.Date;
import java.util.List;

public class UserProfile {
    private int status;
    private int itemsCount;
    private String uid;
    private Date modifiedTime;
    private String nickname;
    private List<List<MediaList>> mediaList;
    private String icon;
    private int level;
    private int role;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getItemsCount() {
        return itemsCount;
    }

    public void setItemsCount(int itemsCount) {
        this.itemsCount = itemsCount;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public List<List<MediaList>> getMediaList() {
        return mediaList;
    }

    public void setMediaList(List<List<MediaList>> mediaList) {
        this.mediaList = mediaList;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}