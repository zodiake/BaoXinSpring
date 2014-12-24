package com.baosight.scc.ec.utils;

import org.joda.time.DateTime;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.UUID;

public class GuidUtil {
	
	public static String newGuid()
	{
		return UUID.randomUUID().toString();
	}
	
	public static String newGuid(String objectGuid)
	{
		if (StringUtils.isEmpty(objectGuid))
			return UUID.randomUUID().toString();
		else
			return objectGuid.trim();
	}

    public static String getStr(){
        String str = "";
        DateTime dt = new DateTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmssSSSS");
        str = sdf.format(dt.toDate())+Math.round(Math.random()*900+100);
        return str;
    }

    public static void main(String args[]){
        System.out.println(getStr());
    }

}
