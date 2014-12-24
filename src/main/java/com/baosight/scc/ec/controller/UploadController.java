package com.baosight.scc.ec.controller;

import com.baosight.scc.ec.model.Message;
import com.baosight.scc.ec.rest.fileAPI;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by zodiake on 2014/6/26.
 */
@Controller
public class UploadController {
    final Logger logger = LoggerFactory.getLogger(UploadController.class);

    @Value("#{'${image.contentType}'.split(',')}")
    private List<String> contentType;

    private String imageHint = "请上传正确规格的图片";

    @RequestMapping(value = "/image/upload", method = RequestMethod.POST, produces = "text/html")
    @ResponseBody
    public String create(@RequestParam(value = "file", required = false) MultipartFile file) {
        // Process upload file
        String location = null;
        if (file != null) {
            try {
                InputStream inputStream = file.getInputStream();
                if (inputStream == null) logger.info("File inputstream is null");
                logger.info("file name:" + file.getOriginalFilename());
                logger.info("file contentType:" + file.getContentType());
                if (!validateContentType(file)) {
                    JSONObject object = new JSONObject();
                    object.put("status", "type");
                    return object.toString();
                }
                if (!validateImage(file.getInputStream())) {
                    JSONObject object = new JSONObject();
                    object.put("status", "size");
                    return object.toString();
                }
                location = saveImage(file);
                JSONObject object = new JSONObject();
                object.put("status", "success");
                object.put("location", location);
                return object.toString();
            } catch (IOException ex) {
                logger.error("Error saving uploaded file");
            }
        }
        return "fail";
    }

    //@sam
    @RequestMapping(value = "/image/uploadImage", method = RequestMethod.POST, produces = "text/html")
    @ResponseBody
    public String createImage(@RequestParam(value = "file", required = false) MultipartFile file) {
        // Process upload file
        String location = null;
        if (file != null) {
            try {
                InputStream inputStream = file.getInputStream();
                if (inputStream == null) logger.info("File inputstream is null");
                logger.info("file name:" + file.getOriginalFilename());
                logger.info("file contentType:" + file.getContentType());
                if (!validateContentType(file)) {
                    JSONObject object = new JSONObject();
                    object.put("status", "type");
                    return object.toString();
                }
                if (!validateImageSize(file.getInputStream())) {
                    JSONObject object = new JSONObject();
                    object.put("status", "size");
                    return object.toString();
                }
                location = saveImage(file);
                JSONObject object = new JSONObject();
                object.put("status", "success");
                object.put("location", location);
                return object.toString();
            } catch (IOException ex) {
                logger.error("Error saving uploaded file");
            }
        }
        return "fail";
    }

    private boolean validateContentType(MultipartFile file) {
        String type = file.getContentType();
        if (!contentType.contains(type))
            return false;
        return true;
    }

    @RequestMapping(value = "/ckImage/upload", method = RequestMethod.POST)
    public ModelAndView uploadImages(@RequestParam(value = "upload", required = false) MultipartFile file,
                                     @RequestParam("CKEditorFuncNum") String num,
                                     HttpServletResponse response) throws IOException {
        String url = saveImage(file);
        PrintWriter writer = response.getWriter();
        response.setContentType("text/html");
        writer.write("<script type=\"text/javascript\">");
        writer.write("window.parent.CKEDITOR.tools.callFunction(" + num + ",'" + url + "','')");
        writer.write("</script>");
        return null;
    }

    private String getImageExtension(String fileName) {
        String type = "jpg";
        if (fileName.endsWith(".jpg") || fileName.endsWith(".JPG")) {
            type = "jpg";
        } else if (fileName.endsWith(".png") || fileName.endsWith(".PNG")) {
            type = "png";
        } else if (fileName.endsWith(".gif") || fileName.endsWith(".GIF")) {
            type = "gif";
        } else if (fileName.endsWith(".bmp") || fileName.endsWith(".BMP")) {
            type = "bmp";
        }
        return type;
    }

    public boolean validateImage(InputStream stream) {
        try {
            BufferedImage bi = ImageIO.read(stream);
            int height = bi.getHeight();
            int width = bi.getWidth();
            logger.info("image height:" + bi.getHeight());
            logger.info("image width:" + bi.getWidth());
            if (((height + 5 >= width && height - 5 <= width) || (width + 5 >= height && width - 5 <= height)) && width > 299)
                return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    //@sam
    public boolean validateImageSize(InputStream stream) {
        try {
            BufferedImage bi = ImageIO.read(stream);
            int height = bi.getHeight();
            int width = bi.getWidth();
            logger.info("image height:" + bi.getHeight());
            logger.info("image width:" + bi.getWidth());
            if (height >=300 && width >= 300) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String saveImage(MultipartFile image) {
        String hash = null;
        try {
            String fileName = fileAPI.getSysFileName(image.getOriginalFilename());
            String filePath = fileAPI.getSysFilePath("EC");
            fileAPI.uploadFileClient(image.getInputStream(), filePath, fileName, "EC");
            logger.info("absolute file path:" + filePath + fileName);
            return filePath + "/" + fileName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hash;
    }

    @RequestMapping(value = "/addImage/upload", method = RequestMethod.POST, produces = "text/html")
    @ResponseBody
    public String uploadAddImage(@RequestParam(value = "file", required = false) MultipartFile file) {
        // Process upload file
        String location = null;
        if (file != null) {
            try {
                InputStream inputStream = file.getInputStream();
                if (inputStream == null) logger.info("File inputstream is null");
                logger.info("file name:" + file.getOriginalFilename());
                logger.info("file contentType:" + file.getContentType());
                if (validateContentType(file)) {
                    location = saveImage(file);
                    return location;
                }
            } catch (IOException ex) {
                logger.error("Error saving uploaded file");
            }
        }
        return "fail";
    }

    @RequestMapping(value = "/infoImage/upload", method = RequestMethod.POST, produces = "text/html")
    @ResponseBody
    public String uploadInfoImage(@RequestParam(value = "file", required = false) MultipartFile file) {
        // Process upload file
        String location = null;
        if (file != null) {
            try {
                InputStream inputStream = file.getInputStream();
                if (inputStream == null) logger.info("File inputstream is null");
                logger.info("file name:" + file.getOriginalFilename());
                logger.info("file contentType:" + file.getContentType());
                if (validateContentType(file)) {
                    location = saveImage(file);
                    return location;
                }
            } catch (IOException ex) {
                logger.error("Error saving uploaded file");
            }
        }
        return "fail";
    }
}
