package com.baosight.scc.ec.rest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidParameterException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import com.baosight.scc.ec.rest.StringUtil;

public class fileAPI {

    /**
     * 上传父目录
     */
    public static String FileRootPath = "/share/attachment";

    /**
     * 服务器地址
     */
    //public static String serverAddress = "http://10.70.82.33:8080/buap/restservice/";

    /**
     * 日期转换方法
     */
    private static HashMap parsers = new HashMap();
    /**
     * 日期转换方法
     */
    private static SimpleDateFormat getDateParser(String pattern) {
        Object parser = parsers.get(pattern);
        if (parser == null) {
            parser = new SimpleDateFormat(pattern);
            parsers.put(pattern, parser);
        }
        return (SimpleDateFormat) parser;
    }
    /**
     * 日期转换方法
     */
    public static String nowDate(String strFormat) {
        java.util.Date date = new java.util.Date();
        return getDateParser(strFormat).format(date);
    }

    /**
     * 上传目录
     * @param moduleCode
     * @return
     */
    public static String getSysFilePath(String moduleCode) {
        if (StringUtil.isEmpty(moduleCode))
            moduleCode = POConstant.moduleCode_SCC;
        String typeCode = POConstant.typeCode_Img;
        StringBuffer uploadPath = new StringBuffer(FileRootPath + "/")
                .append(moduleCode);
        uploadPath.append("/").append(nowDate("yyyy-MM-dd"));
        uploadPath.append("/").append(typeCode);
        return uploadPath.toString();
    }

    /**
     * 上传文件名
     * @param fileName
     * @return
     * @throws InvalidParameterException
     */
    public static String getSysFileName(String fileName)
            throws InvalidParameterException {
        if (StringUtil.isEmpty(fileName))
            throw new InvalidParameterException("文件名不能为空");
        String sysFileName = System.currentTimeMillis() + "."
                + parseFileExtension(fileName);
        return sysFileName;
    }


    /**
     * 获取新路径
     * @param inString
     * @param newPattern
     * @return
     */
    public static String getNewPath(String inString, String newPattern) {
        String oldPattern = ".";
        return StringUtil
                .replace(inString, oldPattern, newPattern + oldPattern);
    }

    /**
     * 获取缩略图文件路径或文件名
     * @param filePath 文件路径或文件名
     * @param imageSize 缩略图样式（POConstant.imageSize_*）
     * @return
     */
    public static String getZoomImagePath(String filePath, String imageSize) {
        return getNewPath(filePath, "_" + imageSize);
    }

    /**
     * 获取文件扩展名
     * @param fileName 文件名
     * @return
     */
    public static String parseFileExtension(String fileName){
        String ext="";
        int start=fileName.lastIndexOf(".");
        if(start!=-1)
            ext=fileName.substring(start+1);
        return ext;
    }

    /**
     * 获取文件类型
     * @param filePath 文件名
     * @return
     */
    public static String getImgMIMEType(String filePath) {
        String MIMEType = "application/x-unknown";
        String imageType = parseFileExtension(filePath);
        if (!StringUtil.isEmpty(imageType)) {
            if (imageType.equals("gif"))
                MIMEType = "image/gif";
            else if (imageType.equals("jpg") || imageType.equals("jpeg"))
                MIMEType = "image/jpeg";
            else if (imageType.equals("png"))
                MIMEType = "image/png";
            else if (imageType.equals("bmp"))
                MIMEType = "image/bmp";
        }
        return MIMEType;
    }

    /**
     * 客户端下载图片
     * @param filePath 服务器图片路径
     * @param pageContext
     * @param imageSize 图片尺寸
     * @return
     */
    public static boolean downloadFileClient(String filePath, PageContext pageContext, String imageSize) {
        boolean flage = false;
        OutputStream outputStream = null;
        InputStream inputStream = null;
        if (StringUtil.isEmpty(filePath))
            return flage;
        try {
            if (!StringUtil.isEmpty(imageSize))
                filePath = getZoomImagePath(filePath, imageSize);

            String url = "downloadFileServer";
            url = url + "?filePath=" + filePath;
            inputStream = CodeAPI.getRestResultInputStream(url);

            HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();
            response.reset();
            response.setContentType(getImgMIMEType(filePath));
            outputStream = response.getOutputStream();
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
            }
            flage = true;

        } catch (Exception ioe) {
            flage = false;
        } finally {
            try {
                if (null != inputStream)
                    inputStream.close();
            } catch (IOException ioe) {
            }
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException ioe) {
            }
        }
        return flage;
    }

    /**
     * 上传文件
     * @param fileInputStream 文件流
     * @param filePath 文件路径
     * @param fileName 文件名
     * @param sysCode 系统代码(EC)
     * @throws Exception
     */
    public static void uploadFileClient(InputStream fileInputStream, String filePath, String fileName, String sysCode) throws Exception {
        try {
            String url = "uploadFileServer";
            url = url + "?filePath=" + filePath + "&fileName=" + fileName + "&sysCode=" + sysCode;
            String result = CodeAPI.getRestResultString(url, fileInputStream);

            System.out.println("result==" + result);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
