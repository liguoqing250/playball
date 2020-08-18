package org.jeecg.modules.appapi.entity.vo;

import java.util.Date;

public class UserNoticeVo {
    private Integer uId;
    private Date readTime;

    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
    }

    public Date getReadTime() {
        return readTime;
    }

    public void setReadTime(Date readTime) {
        this.readTime = readTime;
    }
}
