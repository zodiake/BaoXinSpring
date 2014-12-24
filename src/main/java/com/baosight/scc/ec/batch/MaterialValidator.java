package com.baosight.scc.ec.batch;

import com.baosight.scc.ec.model.Material;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.batch.item.validator.Validator;
import org.springframework.util.StringUtils;

/**
 * Created by Administrator on 2014/9/28.
 */
public class MaterialValidator implements Validator<Material> {
    @Override
    public void validate(Material value) throws ValidationException {
        if (value.getFirstCategory() == null)
            throw new ValidationException("firstCategory can not be null");
        if (value.getCategory() == null)
            throw new ValidationException("category can not be null");
        if (StringUtils.isEmpty(value.getName()))
            throw new ValidationException("name can not be null");
        if (StringUtils.isEmpty(value.getPrice()))
            throw new ValidationException("price can not be null");
        if (StringUtils.isEmpty(value.getMaterialType()))
            throw new ValidationException("materialType can not be null");
        if (value.getMaterialScope().size() == 0)
            throw new ValidationException("materialScope can not be null");
        if (value.getMaterialProvideType().size() == 0)
            throw new ValidationException("materialProvideType can not be null");
        if(StringUtils.isEmpty(value.getMaterialMeasureType()))
            throw new ValidationException("unit can not be null");
    }
}
