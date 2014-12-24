package com.baosight.scc.ec.controller;

import com.baosight.scc.ec.rest.CodeAPI;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by zodiake on 2014/6/25.
 */
@Controller
public class ImageController {
    final Logger logger = LoggerFactory.getLogger(ImageController.class);

    @RequestMapping(value = "/share/attachment/HD/{date}/Img/{fileName:.+}", method = RequestMethod.GET)
    public ModelAndView handleHeaderRequest(
            @PathVariable(value = "date") String date,
            @PathVariable(value = "fileName") String fileName,
            HttpServletResponse response) {
        return handleImage("downloadFileServer?filePath=/share/attachment/HD/" + date + "/Img/", null, fileName, response, date);
    }

    @RequestMapping(value = "/share/attachment/YYZZ/{date}/Img/{fileName:.+}", method = RequestMethod.GET)
    public ModelAndView handleYyzzRequest(
            @PathVariable(value = "date") String date,
            @PathVariable(value = "fileName") String fileName,
            HttpServletResponse response) {
        return handleImage("downloadFileServer?filePath=/share/attachment/YYZZ/" + date + "/Img/", "206", fileName, response, date);
    }

    @RequestMapping(value = "/share/attachment/ORG/{date}/Img/{fileName:.+}", method = RequestMethod.GET)
    public ModelAndView handleORGRequest(
            @PathVariable(value = "date") String date,
            @PathVariable(value = "fileName") String fileName,
            HttpServletResponse response) {
        return handleImage("downloadFileServer?filePath=/share/attachment/ORG/" + date + "/Img/", "206", fileName, response, date);
    }

    @RequestMapping(value = "/share/attachment/TAX/{date}/Img/{fileName:.+}", method = RequestMethod.GET)
    public ModelAndView handleTAXRequest(
            @PathVariable(value = "date") String date,
            @PathVariable(value = "fileName") String fileName,
            HttpServletResponse response) {
        return handleImage("downloadFileServer?filePath=/share/attachment/TAX/" + date + "/Img/", "206", fileName, response, date);
    }

    @RequestMapping(value = "/share/attachment/LOG/{date}/Img/{fileName:.+}", method = RequestMethod.GET)
    public ModelAndView handleLogRequest(
            @PathVariable(value = "date") String date,
            @PathVariable(value = "fileName") String fileName,
            HttpServletResponse response) {
        return handleImage("downloadFileServer?filePath=/share/attachment/LOG/" + date + "/Img/", "256", fileName, response, date);
    }

    @RequestMapping(value = "/share/attachment/EC/{date}/Img/{fileName:.+}", method = RequestMethod.GET)
    public ModelAndView handleRequest(@RequestParam(value = "size", defaultValue = "600") String imageSize,
                                      @PathVariable(value = "date") String date,
                                      @PathVariable(value = "fileName") String fileName,
                                      HttpServletResponse response) {
        return handleImage("downloadFileServer?filePath=/share/attachment/EC/" + date + "/Img/", imageSize, fileName, response, date);
    }

    public ModelAndView handleImage(String sourceUrl, String imageSize, String fileName, HttpServletResponse response, String date) {
        String type = "jpg";
        if (fileName.endsWith(".jpg") || fileName.endsWith(".JPG")) {
            response.setContentType("image/jpeg");
            type = "jpg";
        } else if (fileName.endsWith(".png") || fileName.endsWith(".PNG")) {
            response.setContentType("image/png");
            type = "png";
        } else if (fileName.endsWith(".gif") || fileName.endsWith(".GIF")) {
            response.setContentType("image/gif");
            type = "gif";
        } else if (fileName.endsWith(".bmp") || fileName.endsWith(".BMP")) {
            response.setContentType("image/bmp");
            type = "bmp";
        }

        // return a jpeg
        response.setContentType("image/jpeg");

        InputStream inputStream = null;

        String[] names = fileName.split("\\.");

        // create the image with the text
        String url = null;
        logger.info(fileName);
        if (imageSize != null && !"".equals(imageSize)){
            url = sourceUrl + names[0] + "_" + imageSize + "." + names[1];
        }else{
            url = sourceUrl + names[0] + "." + names[1];
        }

        logger.info(url);

//        ClientRequest restRequest = new ClientRequest(url);
        try {
            inputStream = CodeAPI.getRestResultInputStream(url);
            if (inputStream == null) logger.info("inputStream is null");
            BufferedImage bi = ImageIO.read(inputStream);
            ServletOutputStream out = response.getOutputStream();
            ImageIO.write(bi, type, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
