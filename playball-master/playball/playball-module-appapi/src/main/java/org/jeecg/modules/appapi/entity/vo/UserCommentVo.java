package org.jeecg.modules.appapi.entity.vo;

import org.jeecg.common.playball.entity.PlayballDynamic;
import org.jeecg.modules.appapi.entity.PlayballUserComment;
import org.jeecg.modules.appapi.entity.bo.PlayballUserCommentBo;
import org.jeecg.modules.appapi.entity.bo.PlayballUserDynamicBo;

import java.util.List;

public class UserCommentVo extends PlayballUserDynamicBo {
    private List<PlayballUserCommentBo> playballUserCommentBoList;

    public List<PlayballUserCommentBo> getPlayballUserCommentBoList() {
        return playballUserCommentBoList;
    }

    public void setPlayballUserCommentBoList(List<PlayballUserCommentBo> playballUserCommentBoList) {
        this.playballUserCommentBoList = playballUserCommentBoList;
    }
}
