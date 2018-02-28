package io.rong.messages;


import com.feast.demo.web.service.IMEvent;
import io.rong.util.GsonUtil;

/**
 * 通知商家用餐人数变更
 */

public class WaitingUserChangedMessage extends BaseMessage {

    private long mSendTime;
    private String content;

    private transient static final String TYPE = IMEvent.WAITING_USER_CHANGED;

    public WaitingUserChangedMessage(long mSendTime,String content) {
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
        return GsonUtil.toJson(this, WaitingUserChangedMessage.class);
    }

}
