package com.baosight.scc.ec.web.listener;

import com.baosight.scc.ec.model.*;
import com.baosight.scc.ec.service.*;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.context.support.WebApplicationContextUtils;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;
import java.util.Map;

/**
 * Created by zodiake on 2014/6/6.
 */
public class ContextLoadListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        ServletContext context = sce.getServletContext();
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(context);
        FabricCategoryService fabricCategoryService = (FabricCategoryService)ctx.getBean("fabricCategoryServiceImpl");
        MaterialCategoryService materialCategoryService = (MaterialCategoryService)ctx.getBean("materialCategoryServiceImpl");
        FabricSourceService fabricSourceService = (FabricSourceService)ctx.getBean("fabricSourceServiceImpl");
        InformationCategoryService informationCategoryService = (InformationCategoryService)ctx.getBean("informationCategoryServiceImpl");
        InformationService informationService = (InformationService)ctx.getBean("informationServiceImpl");

        AdvertisementPositionService advertisementPositionService = (AdvertisementPositionService)ctx.getBean("advertisementPositionServiceImpl");
        AdvertisementService advertisementService = (AdvertisementService)ctx.getBean("advertisementServiceImpl");
        // 项目启动加载链接地址 added by Charles 2014-7-23 start...
        //获取配置文件中的url，页面获取的key与配置文件中的必须对应
        LinkConfigService linkConfigService = (LinkConfigService)ctx.getBean("linkConfigServiceImpl");
        Map<String,String> linkMap = linkConfigService.linkMap();
        //项目启动加载链接地址 added by Charles 2014-7-23 .end.
        //获取首页产品分类信息
        //面料产品分类
        List<FabricCategory> fabricCategoryList = fabricCategoryService.findFirstCategoryByIsValid(0);
        //辅料产品分类
        List<MaterialCategory> materialCategoryList = materialCategoryService.findFirstCategoryByIsValid(0);
        //面料原料分类
        List<FabricSource> fabricSourceList = fabricSourceService.findFirstCategoryByIsValid(0);

        Sort sort = new Sort(Sort.Direction.DESC, "createdTime");
        PageRequest pageRequest = new PageRequest(0, 5, sort);
        PageRequest fabricReportPageRequest = new PageRequest(0,6,sort);
        PageRequest bannerPageRequest = new PageRequest(0,12,sort);
        PageRequest shopPageRequest = new PageRequest(0,4,sort);
        InformationCategory notice = informationCategoryService.findByName("公告");
        InformationCategory info = informationCategoryService.findByName("资讯");
        InformationCategory masterView = informationCategoryService.findByName("大师访谈");
        //首页资讯列表
        List<Information> infoList = informationService.findTop5List(info,0,pageRequest);
        //首页公告列表
        List<Information> noticeList = informationService.findTop5List(notice,0,pageRequest);
        List<Information> masterViewList = informationService.findTop5List(masterView,0,pageRequest);
        //首页广告位
        AdvertisementPosition bannerPosition = advertisementPositionService.findByPositionNo("bannerOnHome");
        //首页新季推荐
        AdvertisementPosition newRecommendPosition = advertisementPositionService.findByPositionNo("newRecommendOnHome");
        //面料供应商
        AdvertisementPosition fabricShopPosition = advertisementPositionService.findByPositionNo("fabricShop");
        //辅料供应商
        AdvertisementPosition materialShopPosition = advertisementPositionService.findByPositionNo("materialShop");
        //面料快报-女装面料
        AdvertisementPosition ladiesFabricPosition = advertisementPositionService.findByPositionNo("ladiesFabric");
        //面料快报-男装面料
        AdvertisementPosition mensFabricPosition = advertisementPositionService.findByPositionNo("mensFabric");
        //综合求购-banner
        AdvertisementPosition demandBannerPosition = advertisementPositionService.findByPositionNo("demandBanner");
        //综合求购-求购推荐列表
        AdvertisementPosition newDemandsPosition = advertisementPositionService.findByPositionNo("newDemands");
        //综合求购-供应商列表
        AdvertisementPosition demandShopPosition = advertisementPositionService.findByPositionNo("demandShop");
        //平台广告加载
        List<Advertisement> bannerOnHome = advertisementService.findByAdvertisementPositionAndIsValid(bannerPosition,0,bannerPageRequest);
        List<Advertisement> newRecommendOnHome = advertisementService.findByAdvertisementPositionAndIsValid(newRecommendPosition,0,pageRequest);
        List<Advertisement> fabricShop = advertisementService.findByAdvertisementPositionAndIsValid(fabricShopPosition,0,shopPageRequest);
        List<Advertisement> materialShop = advertisementService.findByAdvertisementPositionAndIsValid(materialShopPosition,0,shopPageRequest);
        List<Advertisement> ladiesFabric = advertisementService.findByAdvertisementPositionAndIsValid(ladiesFabricPosition,0,fabricReportPageRequest);
        List<Advertisement> mensFabric = advertisementService.findByAdvertisementPositionAndIsValid(mensFabricPosition,0,fabricReportPageRequest);
        List<Advertisement> demandBanner = advertisementService.findByAdvertisementPositionAndIsValid(demandBannerPosition,0,bannerPageRequest);
        List<Advertisement> newDemands = advertisementService.findByAdvertisementPositionAndIsValid(newDemandsPosition,0,pageRequest);
        List<Advertisement> demandShop = advertisementService.findByAdvertisementPositionAndIsValid(demandShopPosition,0,fabricReportPageRequest);

        context.setAttribute("infoList",infoList);
        context.setAttribute("noticeList",noticeList);
        context.setAttribute("masterViewList",masterViewList);
        context.setAttribute("fabricCategoryList",fabricCategoryList);
        context.setAttribute("materialCategoryList",materialCategoryList);
        context.setAttribute("fabricSourceList",fabricSourceList);
        context.setAttribute("bannerOnHome",bannerOnHome);
        context.setAttribute("newRecommendOnHome",newRecommendOnHome);
        context.setAttribute("fabricShop",fabricShop);
        context.setAttribute("materialShop",materialShop);
        context.setAttribute("linkMap",linkMap);
        context.setAttribute("ladiesFabric",ladiesFabric);
        context.setAttribute("mensFabric",mensFabric);
        context.setAttribute("demandBanner",demandBanner);
        context.setAttribute("newDemands",newDemands);
        context.setAttribute("demandShop",demandShop);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
