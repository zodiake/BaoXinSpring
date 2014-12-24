package webController;

import annotation.DataSets;
import com.baosight.scc.ec.model.Address;
import com.baosight.scc.ec.model.EcUser;
import com.baosight.scc.ec.model.Material;
import com.baosight.scc.ec.model.SampleOrder;
import com.baosight.scc.ec.service.AddressService;
import com.baosight.scc.ec.service.MaterialService;
import com.baosight.scc.ec.service.SampleOrderService;
import com.baosight.scc.ec.type.ItemState;
import junit.framework.TestCase;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Query;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertNotNull;

/**
 * Created by zodiake on 2014/6/5.
 */
public class SampleOrderController extends AbstractServiceImplTest {
    @Autowired
    SampleOrderService sampleOrderService;
    @Autowired
    AddressService addressService;
    @Autowired
    MaterialService service;

    @Test
    @DataSets(setUpDataSet = "/sampleOrder.xml")
    public void testFindByIdAndCreator()throws Exception{
        EcUser u=new EcUser();
        u.setId("1");
        SampleOrder order=sampleOrderService.findByIdAndCreator("1", u);
        assertNotNull(order);
    }


    @Test
    private void testValidateToken() {
        String hex = DigestUtils.md5Hex("100052");
        assertEquals(hex, "1l");
    }

    @Test
    private void testTask(){
        String sqlM = "select m from Material m where m.state=:state";
        int offset=0;
        Query queryM = em.createQuery(sqlM).setParameter("state",ItemState.出售中).setFirstResult(offset).setMaxResults(100);
        List<Material> materials = queryM.getResultList();
        while(materials.size()>0){
            queryM=em.createQuery(sqlM).setParameter("state",ItemState.出售中).setFirstResult(offset).setMaxResults(100);
            materials=queryM.getResultList();
            for(Material m : materials){
                System.out.println(m.getId());
            }
            em.clear();
            offset+=100;
        }
    }
}
