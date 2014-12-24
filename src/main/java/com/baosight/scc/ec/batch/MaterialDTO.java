package com.baosight.scc.ec.batch;

import com.baosight.scc.ec.batch.repository.MaterialDTOService;
import com.baosight.scc.ec.model.*;
import com.baosight.scc.ec.service.MaterialCategoryService;
import com.baosight.scc.ec.service.MaterialProvideTypeService;
import com.baosight.scc.ec.service.MaterialScopeService;
import com.baosight.scc.ec.type.MaterialProvideType;
import com.baosight.scc.ec.type.MaterialScope;
import com.baosight.scc.ec.utils.GuidUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2014/9/26.
 */
public class MaterialDTO implements MaterialDTOService {
    @Autowired
    private MaterialCategoryService categoryService;
    @Autowired
    private MaterialProvideTypeService provideTypeService;
    @Autowired
    private MaterialScopeService scopeService;

    private final static String SPLIT = "\\|";

    @Override
    public Material transferFromBatch(BatchMaterial batch) throws ParseException {
        Material material = new Material();
        material.setId(GuidUtil.newGuid());
        //一级分类
        material.setFirstCategory(categoryService.findByName(batch.getFirstCategory()));
        //二级分类
        material.setCategory(categoryService.findByName(batch.getSecondCategory()));
        //货品名称
        material.setName(batch.getName());
        //类型
        material.setMaterialType(batch.getType());
        //厚薄
        material.setMaterialWidthAndSizeType(batch.getSize());
        //适用范围
        material.setMaterialScope(transferScopeFromString(batch, SPLIT));
        //货号
        material.setCustomId(batch.getCustomId());
        //供货方式
        material.setMaterialProvideType(transferFromString(batch, SPLIT));
        //计量单位
        material.setMaterialMeasureType(batch.getUnit());

        //set price range
        Map<Double, Double> ranges = new HashMap<Double, Double>();
        if (batch.getFirstQuantity() != null && batch.getFirstQuantity() != null)
            ranges.put(batch.getFirstPrice(), batch.getFirstQuantity());
        if (batch.getSecondQuantity() != null && batch.getSecondPrice() != null)
            ranges.put(batch.getSecondPrice(), batch.getSecondQuantity());
        if (batch.getThirdPrice() != null && batch.getThirdQuantity() != null)
            ranges.put(batch.getThirdPrice(), batch.getThirdQuantity());
        material.setRanges(ranges);
        material.setPrice(batch.getFirstPrice());

        //可售总量
        material.setAvailableSum(batch.getTotal());
        //发货天数
        material.setShipInterval(batch.getShipInterval());
        //描述
        EcContent content = new EcContent();
        content.setId(GuidUtil.newGuid());
        content.setContent(batch.getContent());
        material.setContent(content);

        //set validDate from to
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar start = Calendar.getInstance();
        start.setTime(sdf.parse(batch.getValidDateFrom()));
        Calendar end = Calendar.getInstance();
        end.setTime(sdf.parse(batch.getValidDateTo()));
        material.setValidDateFrom(start);
        material.setValidDateTo(end);

        //图片
        material.setImages(transferImageFromString(batch.getImages(),SPLIT));
        material.setCoverImage(setCover(batch));

        //创建人
        material.setCreatedBy(new EcUser(batch.getCreatedBy()));

        return material;
    }

    private List<MaterialProvideType> transferFromString(BatchMaterial batch, String split) {
        String[] array = batch.getProvideType().split(split);
        List<MaterialProvideType> provideTypes = new ArrayList<MaterialProvideType>();
        for (String i : array) {
            MaterialProvideType type = provideTypeService.findByName(i);
            if (type != null)
                provideTypes.add(type);
        }
        return provideTypes;
    }

    private List<MaterialScope> transferScopeFromString(BatchMaterial batch, String split) {
        String[] array = batch.getScope().split(split);
        List<MaterialScope> provideTypes = new ArrayList<MaterialScope>();
        for (String i : array) {
            MaterialScope scope = scopeService.findByName(i);
            if (scope != null)
                provideTypes.add(scope);
        }
        return provideTypes;
    }

    private List<CultureImage> transferImageFromString(String image, String split) {
        String[] source = image.split(split);
        List<CultureImage> result = new ArrayList<CultureImage>();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        String formatted = format1.format(calendar.getTime());
        for (String i : source) {
            CultureImage c = new CultureImage();
            c.setLocation(getLocation(formatted, i));
            result.add(c);
        }
        return result;
    }

    private String getLocation(String prefix, String source) {
        return "/share/attachment/EC/material" + prefix + "/Img/" + source;
    }

    private String setCover(BatchMaterial batch) {
        String images = batch.getImages();
        String location = null;
        if (images.contains("|")) {
            location = images.split(SPLIT)[0];
        } else
            location = images;
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        String formatted = format1.format(calendar.getTime());
        return getLocation(formatted, location);
    }
}
