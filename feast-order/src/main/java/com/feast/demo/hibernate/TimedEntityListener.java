package com.feast.demo.hibernate;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.sql.Timestamp;

/**
 * Created by ggke on 2017/7/29.
 */
public class TimedEntityListener {
        public TimedEntityListener() {
        }

        @PrePersist
        public void onPrePersist(TimedEntity e) {
            Timestamp t = new Timestamp(System.currentTimeMillis());
            e.setCreation(t);
            e.setLastModified(t);
        }

        @PreUpdate
        public void onPreUpdate(TimedEntity e) {
            if(e instanceof ChangeModifiedInfo) {
                ChangeModifiedInfo info = (ChangeModifiedInfo)e;
                if(!info.isChangeLastModifiedField()) {
                    e.setLastModified(e.getLastModified());
                    return;
                }
            }

            e.setLastModified(new Timestamp(System.currentTimeMillis()));
        }
}
