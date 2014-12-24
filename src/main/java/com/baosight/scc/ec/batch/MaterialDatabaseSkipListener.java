package com.baosight.scc.ec.batch;

import com.baosight.scc.ec.utils.GuidUtil;
import org.springframework.batch.core.annotation.OnSkipInProcess;
import org.springframework.batch.core.annotation.OnSkipInRead;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Calendar;

/**
 * Created by Administrator on 2014/9/26.
 */
public class MaterialDatabaseSkipListener {
    private JdbcTemplate template;

    public JdbcTemplate getTemplate() {
        return template;
    }

    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

    @OnSkipInProcess
    public void onSkipInProcess(BatchProduct batchProduct, Throwable t) {
        if (t instanceof ValidationException) {
            template.update("insert into t_ec_batch_material_skip(id,row_num,createdTime,description) values(?,?,?,?)", GuidUtil.newGuid(), batchProduct.getRowNumber(), Calendar.getInstance().getTime(),t.getMessage());
        }
    }

    @OnSkipInRead
    public void onSkipInRead(Throwable t) {
        if (t instanceof FlatFileParseException) {
            template.update("insert into t_ec_batch_material_skip(id,row_num,createdTime,description) values(?,?,?,?)", GuidUtil.newGuid(), "", Calendar.getInstance().getTime(),t.getMessage());
        }

    }
}
