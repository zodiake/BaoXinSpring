package com.baosight.scc.ec.service.jpa;

import com.baosight.scc.ec.model.IPConfig;
import com.baosight.scc.ec.repository.IPConfigRepository;
import com.baosight.scc.ec.service.IPConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sam on 2014/9/4.
 */
@Service
@Transactional
public class IPConfigServiceImpl implements IPConfigService {

    @Autowired
    private IPConfigRepository ipConfigRepository;
    @PersistenceContext
    private EntityManager em;

    private Logger logger = LoggerFactory.getLogger(IPConfigServiceImpl.class);

    @Override
    @Transactional(readOnly = true)
    public boolean isTargetHost() {
        IPConfig ipConfig = ipConfigRepository.findByFlag(1);
       /* IPConfig ipConfig = null;
        String sql = "select u from IPConfig u where u.flag=1";
        Query query = em.createQuery(sql);
        List<IPConfig> list = new ArrayList<IPConfig>();
        list = query.getResultList();
        ipConfig=list.get(0);*/
        try {
            InetAddress netAddress = InetAddress.getLocalHost();
            String ip = netAddress.getHostAddress();
            logger.info("当前主机的ip："+ip);
            logger.info("执行定时任务的主机ip："+ipConfig.getIp());
            if(ip.equals(ipConfig.getIp())){
                return true;
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return false;
    }

}
