package com.baosight.scc.ec.batch;

import com.baosight.scc.ec.model.FabricIndex;
import com.baosight.scc.ec.utils.GuidUtil;
import org.elasticsearch.transport.TransportSerializationException;
import org.springframework.batch.core.annotation.OnSkipInWrite;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Calendar;

/**
 * Created by Administrator on 2014/11/7.
 */
public class ElasticSearchSkipListener {
    private JdbcTemplate template;

    public JdbcTemplate getTemplate() {
        return template;
    }

    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

    @OnSkipInWrite
    public void onSkipInWrite(FabricIndex fabric, Throwable t) {
        if (t instanceof TransportSerializationException) {
            template.update("insert into t_ec_batch_skip(id,row_num,createdTime,description) values(?,?,?,?)", GuidUtil.newGuid(), fabric.getId(), Calendar.getInstance().getTime(),t.getMessage());
        }
    }
}
