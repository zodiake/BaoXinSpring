package com.baosight.scc.ec.batch;

import com.baosight.scc.ec.model.Fabric;
import com.baosight.scc.ec.utils.GuidUtil;
import org.springframework.batch.core.annotation.OnSkipInProcess;
import org.springframework.batch.core.annotation.OnSkipInRead;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.Calendar;

/**
 * Created by Administrator on 2014/9/23.
 */
public class DatabaseSkipListener {
    private JdbcTemplate template;

    public JdbcTemplate getTemplate() {
        return template;
    }

    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

    @OnSkipInProcess
    public void onSkipInProcess(BatchProduct fabric, Throwable t) {
        if (t instanceof ValidationException) {
            template.update("insert into t_ec_batch_skip(id,row_num,createdTime,description) values(?,?,?,?)", GuidUtil.newGuid(),fabric.getProductNumber(), Calendar.getInstance().getTime(),t.getMessage());
        }
        if (t instanceof NoResultException) {
            template.update("insert into t_ec_batch_skip(id,row_num,createdTime,description) values(?,?,?,?)", GuidUtil.newGuid(),fabric.getProductNumber(), Calendar.getInstance().getTime(),t.getMessage());
        }
    }

    @OnSkipInRead
    public void onSkipInRead(Throwable t) {
        if (t instanceof FlatFileParseException) {
            template.update("insert into t_ec_batch_skip(id,row_num,createdTime,description) values(?,?,?,?)", GuidUtil.newGuid(), "", Calendar.getInstance().getTime(),t.getMessage());
        }

    }
}
