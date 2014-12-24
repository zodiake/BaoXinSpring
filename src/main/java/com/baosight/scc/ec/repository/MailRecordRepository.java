package com.baosight.scc.ec.repository;

import com.baosight.scc.ec.model.MailRecord;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Charles on 2014/10/14.
 */
public interface MailRecordRepository extends PagingAndSortingRepository<MailRecord,String> {
}
