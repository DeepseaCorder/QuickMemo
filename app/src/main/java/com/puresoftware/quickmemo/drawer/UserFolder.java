package com.puresoftware.quickmemo.drawer;

public class UserFolder {

    String folderName;
    int folderCount;
    long timeStamp;

    public UserFolder() {
    }

    public UserFolder(String folderName, int folderCount, long timeStamp) {
        this.folderName = folderName;
        this.folderCount = folderCount;
        this.timeStamp = timeStamp;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public int getFolderCount() {
        return folderCount;
    }

    public void setFolderCount(int folderCount) {
        this.folderCount = folderCount;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return "UserFolder{" +
                "folderName='" + folderName + '\'' +
                ", folderCount=" + folderCount +
                ", timeStamp=" + timeStamp +
                '}';
    }
}
