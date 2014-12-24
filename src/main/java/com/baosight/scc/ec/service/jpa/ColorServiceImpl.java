package com.baosight.scc.ec.service.jpa;

import com.baosight.scc.ec.model.Color;
import com.baosight.scc.ec.service.ColorService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zodiake on 2014/6/13.
 */
@Service
public class ColorServiceImpl implements ColorService{
    Map<String,Color> colors = new HashMap<String,Color>();

    public ColorServiceImpl(){
        colors.put("1",new Color("1", "#f00"));
        colors.put("2",new Color("2", "#f00"));
        colors.put("3",new Color("3", "#f00"));
    }

    @Override
    public Color findById(String id) {
        return colors.get(id);
    }

    @Override
    public Collection<Color> findAll() {
        return colors.values();
    }

}
