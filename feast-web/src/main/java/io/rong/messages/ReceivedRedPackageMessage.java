package io.rong.messages;

import com.feast.demo.web.service.IMEvent;
import io.rong.util.GsonUtil;

public class ReceivedRedPackageMessage extends BaseMessage {

    private long mSendTime;
    private String content;

    private transient static final String TYPE = IMEvent.RECEIVED_RED_PACKAGE;

    public ReceivedRedPackageMessage(long mSendTime, String content) {
        this.mSendTime = mSendTime;
        this.content = content;
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


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override

    public String toString() {
        return GsonUtil.toJson(this, ReceivedRedPackageMessage.class);
    }
}
