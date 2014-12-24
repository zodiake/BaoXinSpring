package com.baosight.scc.ec.batch;

import com.baosight.scc.ec.batch.repository.FabricDTOService;
import com.baosight.scc.ec.image.ImageTools;
import com.baosight.scc.ec.image.fileAPI;
import com.baosight.scc.ec.model.*;
import com.baosight.scc.ec.rest.StringUtil;
import com.baosight.scc.ec.service.*;
import com.baosight.scc.ec.type.FabricMainUseType;
import com.baosight.scc.ec.type.FabricProvideType;
import com.baosight.scc.ec.type.ItemState;
import com.baosight.scc.ec.utils.GuidUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2014/9/22.
 */
public class FabricDTO implements FabricDTOService {
    @Autowired
    private FabricCategoryService fabricCategoryService;
    @Autowired
    private FabricSourceService fabricSourceService;
    @Autowired
    private EcColorService ecColorService;
    @Autowired
    private FabricMainUseTypeService fabricMainUseTypeService;
    @Autowired
    private SeasonService seasonService;
    @Autowired
    private EcPatternService ecPatternService;
    @Autowired
    private FabricTechnologyTypeService fabricTechnologyTypeService;
    @Autowired
    private FabricProvideTypeService fabricProvideTypeService;

    private final static String SPLIT = "\\|";

    @Override
    public Fabric transferFromBatchProductToFabric(BatchProduct product) throws ParseException {
        Fabric fabric = new Fabric();
        fabric.setId(GuidUtil.newGuid());
        fabric.setFirstCategory(fabricCategoryService.findByName(product.getFirstCategory()));
        fabric.setCategory(fabricCategoryService.findByName(product.getSecondCategory()));
        fabric.setSource(fabricSourceService.findFirstSourceByName(product.getFirstSource()));
        fabric.setSourceDetail(fabricSourceService.findSecondSourceByName(product.getSecondSource()));
        fabric.setMainUseTypes(transferFromString(product.getMainUse(), SPLIT));
        fabric.setName(product.getName());
        fabric.setSeasons(transferSeasonFromString(product.getSeason(), SPLIT));
        fabric.setIngredient(product.getSource());
        fabric.setYarn(product.getShazhi());
        fabric.setDensity(product.getMidu());
        fabric.setFabricWidthType(product.getWidth());
        fabric.setFabricHeightType(product.getHeight());
        fabric.setFabricProvideType(transferProvideTypeFromString(product.getProvideType(), SPLIT));

        fabric.setFabricTechnologyId(transferTechnologyIdFromString(product.getFirstTechnology()));
        fabric.setFabricSecondTechnologyId(transferTechnologyIdFromString(product.getSecondTechnology()));

        fabric.setPatterns(transferPatternFromString(product.getFirstPatternSecondCategory(), product.getSecondPatternSecondCategory()));
        fabric.setCustomId(product.getProductNumber());

        String color = product.getColor();
        String[] colorArray = color.split(SPLIT);
        List<EcColor> colorList = new ArrayList<EcColor>();
        if (!CollectionUtils.isEmpty(colorList))
            for (String i : colorArray) {
                colorList.add(ecColorService.findOne(i));
            }

        fabric.setColors(colorList);
        fabric.setFabricMeasureType(product.getUnit());
        Map<Double, Double> showRanges = new LinkedHashMap<Double, Double>();
        showRanges.put(product.getFirstQuantity(), product.getFirstPrice());
        showRanges.put(product.getSecondQuantity(), product.getSecondPrice());
        showRanges.put(product.getThirdQuantity(), product.getThirdPrice());
        fabric.setRanges(showRanges);
        fabric.setPrice(product.getFirstPrice());

        fabric.setAvailableSum(product.getSum());
        fabric.setShipInterval(product.getShipInterval());
        EcContent content = new EcContent();
        content.setId(GuidUtil.newGuid());
        content.setContent(product.getDesc());
        fabric.setContent(content);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar start = Calendar.getInstance();
        if (!StringUtils.isEmpty(product.getStartValidDate()))
            start.setTime(sdf.parse(product.getStartValidDate()));
        Calendar end = Calendar.getInstance();
        if (!StringUtils.isEmpty(product.getEndValidDate()))
            end.setTime(sdf.parse(product.getEndValidDate()));
        fabric.setValidDateFrom(start);
        fabric.setValidDateTo(end);

        List<CultureImage> images = transferImageFromString(product.getImages(), SPLIT);
        if (!CollectionUtils.isEmpty(images)) {
            fabric.setCoverImage(images.get(0).getLocation());
            fabric.setImages(images);
        }
        fabric.setState(ItemState.出售中);
        fabric.setCreatedBy(new EcUser(product.getCreatedBy()));

        return fabric;
    }

    private List<FabricMainUseType> transferFromString(String use, String split) {
        List<FabricMainUseType> result = new LinkedList<FabricMainUseType>();
        if(!StringUtils.isEmpty(use)) {
            String[] array = use.split(split);
            for (String i : array) {
                FabricMainUseType type = fabricMainUseTypeService.findByName(i);
                if (type != null)
                    result.add(type);
            }
        }
        return result;
    }

    private List<Season> transferSeasonFromString(String use, String split) {
        String[] array = use.split(split);
        List<Season> result = new LinkedList<Season>();
        for (String i : array) {
            Season season = seasonService.findByName(i);
            if (season != null)
                result.add(season);
        }
        return result;
    }

    private List<EcPattern> transferPatternFromString(String pattern1, String pattern2) {
        List<EcPattern> result = new LinkedList<EcPattern>();
        if (!StringUtils.isEmpty(pattern1))
            result.add(ecPatternService.findByName(pattern1));
        if (!StringUtils.isEmpty(pattern2))
            result.add(ecPatternService.findByName(pattern2));
        return result;
    }

    private List<FabricProvideType> transferProvideTypeFromString(String type, String split) {
        String[] array = type.split(split);
        List<FabricProvideType> result = new LinkedList<FabricProvideType>();
        for (String i : array) {
            FabricProvideType provideType = fabricProvideTypeService.findByName(i);
            if (provideType != null)
                result.add(provideType);
        }
        return result;
    }

    private String transferTechnologyIdFromString(String name) {
        return fabricTechnologyTypeService.findByName(name).getId();
    }

    private List<CultureImage> transferImageFromString(String image, String split) {
        List<CultureImage> result = new ArrayList<CultureImage>();
        if(image.contains(split)) {
            String[] source = image.split(split);
            for (String i : source) {
                CultureImage c = new CultureImage();
                c.setLocation(importCreateImg("C:\\云端时尚数据信息照片录入\\800X800\\" + i));
                result.add(c);
            }
        }else {
            CultureImage c = new CultureImage();
            c.setLocation(importCreateImg("C:\\云端时尚数据信息照片录入\\800X800\\" + image));
            result.add(c);
        }
        return result;
    }

    private String importCreateImg(String oldPath) {
        File fold = new File(oldPath);
        String newfilepath = "";
        File f = new File(oldPath);
        System.err.println(oldPath + "-------");
        try {
            InputStream fi = new FileInputStream(f);
            newfilepath = fileAPI.getSysFilePath("EC");
            newfilepath = newfilepath + "/" + fileAPI.getSysFileName(fold.getName());
            int c;
            File file2 = new File(newfilepath);
            if (!file2.exists()) {
                file2.createNewFile();
            }
            FileOutputStream fop = new FileOutputStream(file2);
            byte[] buf = new byte[(int) f.length()];
            while ((c = fi.read(buf)) != -1) {
                fop.write(buf, 0, c);
            }
            fop.flush();
            fop.close();
            fi.close();
            System.out.println("图片上传成功！");
            ImageTools.zoomImageBycode("EC", newfilepath);
        } catch (Exception e) {
            e.printStackTrace();
            //throw new PlatException(e.getMessage());
        }
        return newfilepath;
    }

}

