package com.baosight.scc.ec.batch;

import com.baosight.scc.ec.model.Fabric;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.batch.item.validator.Validator;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * Created by Administrator on 2014/9/23.
 */
public class ProductValidator implements Validator<Fabric> {
    @Override
    public void validate(Fabric value) throws ValidationException {
        if (value.getMainUseTypes() == null)
            throw new ValidationException("mainusetype can not be null");
        if (value.getFirstCategory() == null)
            throw new ValidationException("firstCategory can not be null");
        if (value.getCategory() == null)
            throw new ValidationException("secondCategory can not be null");
        if (value.getSourceDetail() == null)
            throw new ValidationException("secondSource can not be null");
        if (value.getSource() == null)
            throw new ValidationException("firstSource can not be null");
        if(MapUtils.isEmpty(value.getRanges()))
            throw new ValidationException("price can not be null");
        if(StringUtils.isEmpty(value.getFabricMeasureType()))
            throw new ValidationException("unit can not be null");
        if(StringUtils.isEmpty(value.getContent()))
            throw new ValidationException("content can not be null");
        if(StringUtils.isEmpty(value.getName()))
            throw new ValidationException("name can not be null");
        if(value.getSeasons()==null)
            throw new ValidationException("name can not be null");
        if(StringUtils.isEmpty(value.getFabricWidthType()))
            throw new ValidationException("width can not be null");
        if(CollectionUtils.isEmpty(value.getFabricProvideType()))
            throw new ValidationException("provideType can not be null");
        if(value.getYarn().length()>60)
            throw new ValidationException("yarn too long");
    }
}
