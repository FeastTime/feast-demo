package io.rong.messages;

import io.rong.util.GsonUtil;

public class OpenRedPackageMessage extends BaseMessage {

    private long mSendTime;
    private String mContent;

    private transient static final String TYPE = "CM:openRedPackage";

    public OpenRedPackageMessage(long mSendTime,String mContent) {
        this.mSendTime = mSendTime;
        this.mContent = mContent;
    }

    public String getType() {
        return TYPE;
    }

    public long getmSendTime() {
        return mSendTime;
    }

    public void setmSendTime(long mSendTime) {
        this.mSendTime = mSendTime;
    }

    public String getmContent() {
        return mContent;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }

    @Override
    public String toString() {
        return GsonUtil.toJson(this, OpenRedPackageMessage.class);
    }
}