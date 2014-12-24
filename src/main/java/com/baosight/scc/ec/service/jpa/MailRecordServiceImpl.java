package com.baosight.scc.ec.service.jpa;

import com.baosight.scc.ec.model.MailRecord;
import com.baosight.scc.ec.repository.MailRecordRepository;
import com.baosight.scc.ec.service.MailRecordService;
import com.baosight.scc.ec.utils.GuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Charles on 2014/10/14.
 */
@Service
@Transactional
public class MailRecordServiceImpl implements MailRecordService {
    @Autowired
    private MailRecordRepository mailRecordRepository;

    @Override
    public MailRecord save(MailRecord mailRecord) {
        mailRecord.setId(GuidUtil.newGuid());
        return mailRecordRepository.save(mailRecord);
    }
}
