package com.baosight.scc.ec.service;

import com.baosight.scc.ec.model.MailRecord;

/**
 * Created by Charles on 2014/10/14.
 */
public interface MailRecordService {

    /**
     * 保存邮件发送记录
     * @param mailRecord
     * @return
     */
    MailRecord save(MailRecord mailRecord);
}
