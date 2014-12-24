package com.baosight.scc.ec.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * Created by zodiake on 2014/6/16.
 */
public class ApplicationConversionServiceFactoryBean extends FormattingConversionServiceFactoryBean{
    final Logger logger =
            LoggerFactory.getLogger(ApplicationConversionServiceFactoryBean.class);
    private static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
    private String datePattern = DEFAULT_DATE_PATTERN;
    private Set<Formatter<?>> formatters = new HashSet<Formatter<?>>();
    public String getDatePattern() {
        return datePattern;
    }
    @Autowired(required=false)
    public void setDatePattern(String datePattern) {
        this.datePattern = datePattern;
    }
    @PostConstruct
    public void init() {
        formatters.add(getDateTimeFormatter());
        setFormatters(formatters);
    }
    public Formatter<Calendar> getDateTimeFormatter() {
        return new Formatter<Calendar>() {
            public Calendar parse(String dateTimeString, Locale locale) throws java.text.ParseException {
                logger.info("Parsing date string: " + dateTimeString);
                Calendar cal = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
                cal.setTime(sdf.parse(dateTimeString));
                return cal;
            }
            public String print(Calendar dateTime, Locale locale) {
                logger.info("Formatting datetime: " + dateTime);
                return datePattern.toString();
            }
        };
    }
}
