package com.baosight.scc.ec.controller;

import com.baosight.buapx.security.common.StringUtils;
import com.baosight.scc.ec.rest.StringUtil;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Created by Administrator on 2014/9/22.
 */
@Controller
public class JobLaunchController {
    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private JobRegistry jobRegistry;

    @RequestMapping(value = "/jobLaunch")
    public void launch(@RequestParam("jobName") String jobName, @RequestParam(value = "fileName",required = false) String fileName) throws Exception {
        JobParametersBuilder builder = new JobParametersBuilder();
        if (!StringUtil.isEmpty(fileName))
            builder.addString("file", "classpath:" + fileName + ".csv");
        Calendar now=Calendar.getInstance();
        String date = new SimpleDateFormat("yyyyMMdd").format(now.getTime());
        builder.addString("date",date);
        jobLauncher.run(jobRegistry.getJob(jobName), builder.toJobParameters());
    }
}
