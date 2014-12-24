CREATE TABLE T_ec_EcUser (
  ID                CHAR(36) NOT NULL,
  USERTYPE          CHAR(1),
  DEFAULTADDRESS_ID CHAR(36),
  NAME              VARCHAR(20),
  PASSWORD          VARCHAR(20),
  CREATEDTIME       TIMESTAMP,
  COMPANYNAME       VARCHAR(20),
  COMPANYADDRESS    VARCHAR(60),
  URL               VARCHAR(30),
  MOBILE            VARCHAR(20),
  PHONE             VARCHAR(20),
  FOCUS             VARCHAR(50),
  COMPANYTYPE       VARCHAR(10),
  SIGNUPAMOUNT      VARCHAR(10),
  ANNUALAMOUNT      VARCHAR(10),
  PEOPLENUM         VARCHAR(10),
  PRIMARY KEY (id)
);

CREATE TABLE T_ec_address (
  ID           CHAR(36) NOT NULL,
  STATE        VARCHAR(10),
  CITY         VARCHAR(20),
  ZIPCODE      VARCHAR(10),
  STREET       VARCHAR(100),
  RECEIVERNAME VARCHAR(20),
  MOBILE       CHAR(11),
  ZIPPHONE     VARCHAR(4),
  PHONE        VARCHAR(8),
  CHILDPHONE   VARCHAR(6),
  USER_ID      CHAR(36),
  CREATEDTIME  TIMESTAMP,
  UPDATEDTIME  TIMESTAMP,
  PRIMARY KEY (id)
);

CREATE TABLE T_EC_SampleAddress (
  ID           CHAR(36) NOT NULL,
  STATE        VARCHAR(10),
  CITY         VARCHAR(20),
  ZIPCODE      VARCHAR(10),
  STREET       VARCHAR(100),
  RECEIVERNAME VARCHAR(20),
  MOBILE       CHAR(11),
  ZIPPHONE     VARCHAR(4),
  PHONE        VARCHAR(8),
  CHILDPHONE   VARCHAR(6),
  PRIMARY KEY (id)
);

CREATE TABLE T_EC_OrderAddress (
  ID           CHAR(36) NOT NULL,
  TITLE        VARCHAR(50),
  STATE        VARCHAR(10),
  CITY         VARCHAR(20),
  ZIPCODE      VARCHAR(10),
  STREET       VARCHAR(100),
  RECEIVERNAME VARCHAR(20),
  MOBILE       CHAR(11),
  ZIPPHONE     VARCHAR(4),
  PHONE        VARCHAR(8),
  CHILDPHONE   VARCHAR(6),
  PRIMARY KEY (id)
);

ALTER TABLE T_ec_EcUser ADD FOREIGN KEY (defaultAddress_id) REFERENCES T_ec_address (id);


CREATE TABLE t_ec_content (
  id      CHAR(36) not null,
  content VARCHAR(30000),
  primary key(id)
);

CREATE TABLE T_ec_Item (
  ID            CHAR(36) NOT NULL, --主键
  NAME          VARCHAR(32), --货品名称
  CUSTOMID      VARCHAR(32), --货品编号
  CONTENT       VARCHAR(30000), --详细信息
  VALIDDATEFROM TIMESTAMP, --有效期开始
  VALIDDATETO   TIMESTAMP, --有效期结束
  UPDATEDTIME   TIMESTAMP, --更新日期
  CREATEDTIME   TIMESTAMP, --创建日期
  COVERIMAGE    VARCHAR(60),
  BIDCOUNT      INT DEFAULT 0, --成交笔数
  TYPE          CHAR(1),
  CREATEDBY     CHAR(36),
  PRICE         DOUBLE,
  STATE         VARCHAR(20),
  PRIMARY KEY (id),
  content_id    CHAR(36),
  FOREIGN KEY (content_id) REFERENCES t_ec_content (id)
);

CREATE TABLE T_ec_MaterialCategory (
  ID          CHAR(36) NOT NULL,
  NAME        VARCHAR(20),
  PARENT_ID   CHAR(36),
  CREATEDBY   CHAR(36),
  CREATEDTIME TIMESTAMP,
  UPDATEDBY   CHAR(36),
  UPDATEDTIME TIMESTAMP,
  ISVALID     INT DEFAULT 0,
  FOREIGN KEY (parent_id) REFERENCES T_ec_MaterialCategory (id),
  PRIMARY KEY (id)
);

CREATE TABLE T_ec_FabricCategory (
  ID          CHAR(36) NOT NULL,
  NAME        VARCHAR(20),
  PARENT_ID   CHAR(36),
  CREATEDBY   CHAR(36),
  CREATEDTIME TIMESTAMP,
  UPDATEDBY   CHAR(36),
  UPDATEDTIME TIMESTAMP,
  ISVALID     INT DEFAULT 0,
  FOREIGN KEY (parent_id) REFERENCES T_ec_FabricCategory (id),
  PRIMARY KEY (id)
);

--原料成分
CREATE TABLE T_ec_FabricSource (
  ID          CHAR(36) NOT NULL,
  NAME        VARCHAR(20),
  PARENT_ID   CHAR(36),
  CREATEDBY   CHAR(36),
  UPDATEDBY   CHAR(36),
  CREATEDTIME TIMESTAMP,
  UPDATEDTIME TIMESTAMP,
  ISVALID     INT DEFAULT 0,
  FOREIGN KEY (parent_id) REFERENCES T_ec_FabricSource (id),
  PRIMARY KEY (id)
);


CREATE TABLE T_ec_Fabric (
  ID                       CHAR(36) NOT NULL, --外键主键
  FABRICSEASONTYPE         VARCHAR(10), --适用季节
  INGREDIENT               VARCHAR(40), --成分及含量
  YARN                     VARCHAR(40), --纱织
  DENSITY                  VARCHAR(20), --密度
  FABRICWIDTHTYPE          VARCHAR(30), --幅宽
  FABRICHEIGHTTYPE         VARCHAR(30), --克重
  FABRICTECHNOLOGYID       VARCHAR(36), --染整工艺大类
  FABRICSECONDTECHNOLOGYID VARCHAR(36), --染整工艺小类
  ITEMNUMBER               VARCHAR(30), --货号
  FABRICPROVIDETYPE        VARCHAR(30), --供货方式
  SHIPINTERVAL             INT DEFAULT 0, --发货时间多少天
  FABRICMEASURETYPE        VARCHAR(10), --计量单位
  AVAILABLESUM             DOUBLE DEFAULT 0, --可售总量
  CATEGORY_ID              CHAR(36), --分类
  FIRST_CATEGORY_ID        CHAR(36), --分类
  SOURCE_ID                CHAR(36),
  DETAILSOURCE_ID          CHAR(36),
  FOREIGN KEY (id) REFERENCES T_ec_Item (id),
  FOREIGN KEY (category_id) REFERENCES T_ec_fabriccategory (id),
  FOREIGN KEY (source_id) REFERENCES T_ec_FabricSource (id),
  FOREIGN KEY (detailSource_id) REFERENCES T_ec_FabricSource (id),
  PRIMARY KEY (id)
);

CREATE TABLE T_ec_Material (
  ID                  CHAR(36) NOT NULL,
  MATERIALTYPE        VARCHAR(30), --类型
  WIDTHANDSIZE        VARCHAR(30), --重量厚薄
  MATERIALSCOPE       VARCHAR(30), --适用范围
  PROVIDERTYPE        VARCHAR(30), --供货方式
  MATERIALMEASURETYPE VARCHAR(30), --计量单位
  CATEGORY_ID         CHAR(36), --分类
  FIRSTCATEGORY_ID    CHAR(36),
  AVAILABLESUM        DOUBLE,
  SHIPINTERVAL        INT,
  FOREIGN KEY (ID) REFERENCES T_ec_item (ID),
  FOREIGN KEY (category_id) REFERENCES T_ec_MaterialCategory (id),
  PRIMARY KEY (id)
);


CREATE TABLE T_ec_fabric_range (
  FABRIC_ID CHAR(36) NOT NULL,
  UNIT_FROM DOUBLE   NOT NULL DEFAULT 0,
  PRICE     DOUBLE DEFAULT 0,
  PRIMARY KEY (fabric_id, unit_from),
  FOREIGN KEY (fabric_id) REFERENCES T_ec_fabric (id)
);

CREATE TABLE T_ec_material_range (
  MATERIAL_ID CHAR(36) NOT NULL,
  UNIT_FROM   DOUBLE   NOT NULL,
  PRICE       DOUBLE,
  PRIMARY KEY (material_id, unit_from),
  FOREIGN KEY (MATERIAL_ID) REFERENCES T_ec_material (id)
);

CREATE TABLE T_ec_CultureImage (
  ITEM_ID     CHAR(36)      NOT NULL,
  LOCATION    VARCHAR(100)  NOT NULL,
  ORDERNUM    INT DEFAULT 0 NOT NULL,
  CREATEDTIME TIMESTAMP,
  UPDATEDTIME TIMESTAMP,
  PRIMARY KEY (item_id, location, orderNum),
  FOREIGN KEY (item_id) REFERENCES T_ec_item (id)
);

CREATE TABLE T_ec_pattern (
  ITEM_ID CHAR(36) NOT NULL,
  NAME    CHAR(36) NOT NULL,
  ID      VARCHAR(30),
  PRIMARY KEY (item_id, name),
  FOREIGN KEY (item_id) REFERENCES T_ec_item (id)
);

CREATE TABLE T_ec_item_color (
  ITEM_ID  CHAR(36)    NOT NULL,
  COLOR_ID VARCHAR(36) NOT NULL,
  PRIMARY KEY (item_id, color_id),
  FOREIGN KEY (item_id) REFERENCES T_ec_item (id)
);

CREATE TABLE T_ec_SampleOrder (
  ID           CHAR(36) NOT NULL,
  orderNo      CHAR(36),
  USER_ID      CHAR(36),
  ADDRESS_ID   CHAR(36),
  PROVIDER_ID  CHAR(36),
  STATE        VARCHAR(20),
  CREATEDTIME  TIMESTAMP,
  UPDATEDTIME  TIMESTAMP,
  DELIVERYTIME TIMESTAMP,
  remark       VARCHAR(500),
  FOREIGN KEY (user_id) REFERENCES T_ec_EcUser (id),
  FOREIGN KEY (provider_id) REFERENCES T_ec_EcUser (id),
  FOREIGN KEY (address_id) REFERENCES T_EC_SampleAddress (id),
  PRIMARY KEY (id)
);
CREATE TABLE T_ec_SampleLine (
  ID             CHAR(36) NOT NULL,
  ITEM_ID        CHAR(36),
  SAMPLEORDER_ID CHAR(36),
  FOREIGN KEY (item_id) REFERENCES T_ec_item (id),
  FOREIGN KEY (SAMPLEORDER_ID) REFERENCES T_ec_SampleOrder (id),
  PRIMARY KEY (id)
);

CREATE TABLE T_ec_fabric_mainUse (
  FABRIC_ID CHAR(36) NOT NULL,
  ID        CHAR(36) NOT NULL,
  NAME      VARCHAR(40),
  ORDERNUM  INT DEFAULT 0,
  PRIMARY KEY (fabric_id, id),
  FOREIGN KEY (fabric_id) REFERENCES T_ec_Fabric (id)
);

CREATE TABLE T_ec_DemandOrder (
  ID               CHAR(36) NOT NULL,
  TITLE            VARCHAR(20),
  EXCEPTIONADDRESS VARCHAR(10),
  ADDRESS_ID       CHAR(36),
  DEMANDTYPE       VARCHAR(30),
  VALIDDATEFROM    TIMESTAMP,
  VALIDDATETO      TIMESTAMP,
  PROVIDETYPE      VARCHAR(10),
  UNIT             VARCHAR(20),
  PRICEFROM        DOUBLE DEFAULT 0,
  PRICETO          DOUBLE DEFAULT 0,
  DEMANDSUM        DOUBLE DEFAULT 0,
  USER_ID          CHAR(36),
  CREATEDTIME      TIMESTAMP,
  UPDATEDTIME      TIMESTAMP,
  DELIVERYDURATION INT DEFAULT 0,
  CONTENT          VARCHAR(2000),
  STATE            VARCHAR(10),
  FOREIGN KEY (address_id) REFERENCES T_ec_address (id),
  PRIMARY KEY (id)
);

CREATE TABLE T_ec_DemandOrderImage (
  DEMANDORDER_ID CHAR(36)    NOT NULL,
  LOCATION       VARCHAR(20) NOT NULL WITH DEFAULT '',
  ORDERNUM       INT DEFAULT 0,
  CREATEDTIME    TIMESTAMP,
  UPDATEDTIME    TIMESTAMP,
  FOREIGN KEY (demandOrder_id) REFERENCES T_ec_DemandOrder (id),
  PRIMARY KEY (demandOrder_id, location)
);

CREATE TABLE T_ec_OrderItem (
  ID              CHAR(36) NOT NULL,
  ORDERNO         CHAR(36),
  SUMMARY         DOUBLE DEFAULT 0,
  BUYER_ID        CHAR(36),
  SELLER_ID       CHAR(36),
  CREATEDTIME     TIMESTAMP,
  CANCELTIME      TIMESTAMP,
  DELIVERTIME     TIMESTAMP,
  RECEIVETIME     TIMESTAMP,
  ORDERADDRESS_ID CHAR(36),
  STATUS          VARCHAR(20),
  NEEDINVOICE     INT DEFAULT 1,
  FOREIGN KEY (orderAddress_id) REFERENCES T_EC_OrderAddress (id),
  PRIMARY KEY (id)
);

CREATE TABLE T_ec_OrderLine (
  ID           CHAR(36) NOT NULL,
  PRICE        DOUBLE DEFAULT 0,
  SUM          DOUBLE DEFAULT 0,
  QUANTITY     INT DEFAULT 0,
  ORDERITEM_ID CHAR(36),
  ITEM_ID      CHAR(36),
  FOREIGN KEY (orderItem_id) REFERENCES T_ec_OrderItem (id),
  FOREIGN KEY (item_id) REFERENCES T_ec_Item (id),
  PRIMARY KEY (id)
);

CREATE TABLE T_ec_user_PreferFabricCategory (
  USER_ID     CHAR(36) NOT NULL,
  CATEGORY_ID CHAR(36) NOT NULL,
  PRIMARY KEY (user_id, category_id),
  FOREIGN KEY (category_id) REFERENCES T_ec_FabricCategory (id)
);

CREATE TABLE T_ec_user_PreferMaterialCategory (
  USER_ID     CHAR(36) NOT NULL,
  CATEGORY_ID CHAR(36) NOT NULL,
  PRIMARY KEY (user_id, category_id),
  FOREIGN KEY (category_id) REFERENCES T_ec_MaterialCategory (id)
);

CREATE TABLE t_ec_fabric_color (
  ID        VARCHAR(36) NOT NULL,
  FABRIC_ID CHAR(36)    NOT NULL,
  RGB       CHAR(30),
  NAME      VARCHAR(30),
  hierarchy varchar(10),
  PRIMARY KEY (fabric_id, id),
  FOREIGN KEY (fabric_id) REFERENCES T_ec_Fabric (id)
);

CREATE TABLE t_ec_material_color (
  ID          VARCHAR(36) NOT NULL,
  MATERIAL_ID CHAR(36)    NOT NULL,
  NAME        VARCHAR(20),
  RGB         CHAR(30),
  hierarchy varchar(10),
  PRIMARY KEY (material_id, id),
  FOREIGN KEY (material_id) REFERENCES T_ec_Material (id)
);

CREATE TABLE t_ec_favouriteItems (
  USER_ID     CHAR(36) NOT NULL,
  ITEM_ID     CHAR(36) NOT NULL,
  CREATEDTIME TIMESTAMP,
  PRIMARY KEY (user_id, item_id),
  FOREIGN KEY (item_id) REFERENCES T_ec_Item (id)
);

CREATE TABLE t_ec_shop (
  ID      CHAR(36) NOT NULL,
  USER_ID CHAR(36),
  PRIMARY KEY (id)
);

CREATE TABLE t_ec_favouriteShops (
  SHOP_ID     CHAR(36) NOT NULL,
  USER_ID     CHAR(36) NOT NULL,
  CREATEDTIME TIMESTAMP,
  PRIMARY KEY (user_id, shop_id),
  FOREIGN KEY (shop_id) REFERENCES T_ec_Shop (id)

);
--买家评价表
CREATE TABLE T_ec_Comment (
  ID            CHAR(36) NOT NULL,
  ITEM_ID       CHAR(36),
  USER_ID       CHAR(36),
  CREATEDTIME   TIMESTAMP,
  UPDATEDTIME   TIMESTAMP,
  CONTENT       VARCHAR(1000),
  ATTITUDE      INT DEFAULT 0,
  DELIVERYSPEED INT DEFAULT 0,
  SATISFIED     INT DEFAULT 0,
  ORDER_ID      CHAR(36),
  TYPE          VARCHAR(8),
  FOREIGN KEY (item_id) REFERENCES T_ec_Item (id),
  FOREIGN KEY (order_id) REFERENCES T_ec_OrderItem (id),
  PRIMARY KEY (id)
);

--卖家评价表
CREATE TABLE T_ec_sellerComment (
  ID          CHAR(36) NOT NULL,
  ITEM_ID     CHAR(36),
  USER_ID     CHAR(36),
  CREATEDTIME TIMESTAMP,
  UPDATEDTIME TIMESTAMP,
  CONTENT     VARCHAR(200),
  ORDER_ID    CHAR(36),
  TYPE        VARCHAR(30),
  FOREIGN KEY (item_id) REFERENCES T_ec_Item (id),
  FOREIGN KEY (order_id) REFERENCES T_ec_OrderItem (id),
  PRIMARY KEY (id)
);

--站内信表
CREATE TABLE T_ec_insideLetter (
  ID           CHAR(36) NOT NULL,
  LETTERTYPE   INT DEFAULT 0, --站内信类型，0：系统通知，1：其他通知
  LETTERSTATUS INT DEFAULT 0, --站内信状态0：未读，1：已读，2：已删除
  CONTENT      VARCHAR(200), --内容
  TITLE        VARCHAR(20), --标题
  CREATEDTIME  TIMESTAMP DEFAULT current_timestamp, --创建时间
  SENDER_ID    CHAR(36), --发件人
  RECEIVER_ID  CHAR(36), --收件人
  PRIMARY KEY (id)
);
------------------------------------------------------------------------后台管理数据表--------------------------------------------------------------------------
CREATE TABLE T_ec_information_category (
  ID           CHAR(36) NOT NULL,
  CATEGORYNAME VARCHAR(36), --资讯分类名称
  DESCRIPTION VARCHAR(500), --说明
  CREATEDTIME  TIMESTAMP,
  ISVALID      INT DEFAULT 0,
  PRIMARY KEY (id)
);

CREATE TABLE T_ec_infoContent (
  id      CHAR(36) not null,
  content VARCHAR(30000),
  primary key(id)
);

CREATE TABLE T_ec_information (
  ID          CHAR(36) NOT NULL,
  CATEGORY_ID CHAR(36), --资讯分类id
  TITLE       VARCHAR(100), --资讯title
  BRIEFCONTENT     VARCHAR(500), --资讯简要
  CREATEDTIME TIMESTAMP,
  ISVALID     INT DEFAULT 0, --是否有效
  USER_ID     VARCHAR(36), --创建人
  CONTENT_ID char (36),
  FOREIGN KEY (CATEGORY_ID) REFERENCES T_ec_information_category (id),
  FOREIGN KEY (CONTENT_ID) REFERENCES T_ec_infoContent (id),
  PRIMARY KEY (id)
);

CREATE TABLE T_ec_ad_position (
  ID          CHAR(36) NOT NULL,
  POSITIONNO  VARCHAR(50), --栏位编号
  NAME        VARCHAR(100), --栏位名称
  DESCRIPTION VARCHAR(500), --说明
  CREATEDTIME TIMESTAMP,
  ISVALID     INT DEFAULT 0,
  PRIMARY KEY (id)
);

CREATE TABLE T_ec_ad (
  ID          CHAR(36) NOT NULL,
  POSITION_ID VARCHAR(50), --广告栏位id
  TITLE       VARCHAR(100), --广告title
  subTitle VARCHAR(200),
  desc VARCHAR(200),
  LINK        VARCHAR(500), --广告链接
  COVERPATH   VARCHAR(500), --封面图片路径
  CREATEDTIME TIMESTAMP,
  ISVALID     INT DEFAULT 0, --是否有效
  FOREIGN KEY (position_id) REFERENCES T_ec_ad_position (id),
  PRIMARY KEY (id)
);

------------------------------------------------------------------------定时任务数据表--------------------------------------------------------------------------
--卖家维度率表
CREATE TABLE T_ec_dimension_rate (
  ID            INT GENERATED ALWAYS AS IDENTITY ( START WITH 1 INCREMENT BY 1 ),
  USER_ID       CHAR(36), --卖家id
  ATTITUDE      DOUBLE DEFAULT 0, --服务态度
  DELIVERYSPEED DOUBLE DEFAULT 0, --发货速度
  SATISFIED     DOUBLE DEFAULT 0, --宝贝描述符合度
  CREATEDTIME   TIMESTAMP,
  PRIMARY KEY (id)
);

--买家累计信用
CREATE TABLE T_ec_sellerCredit (
  ID                  INT GENERATED ALWAYS AS IDENTITY ( START WITH 1 INCREMENT BY 1),
  USER_ID             CHAR(36), --卖家id
  TYPE                VARCHAR(30), --满意类型:好评、中评、差评
  WEEKCOUNT           INT DEFAULT 0, --最近一周次数
  ONEMONTHCOUNT       INT DEFAULT 0, --最近一个月次数
  SIXMONTHCOUNT       INT DEFAULT 0, --最近六个月次数
  SIXMONTHBEFORECOUNT INT DEFAULT 0, --六个月前次数
  TOTAL               INT DEFAULT 0, --一年的总次数
  CREATEDTIME         TIMESTAMP,
  PRIMARY KEY (id)
);

--综合评分
CREATE TABLE T_ec_Composite_Score (
  ID          INT GENERATED ALWAYS AS IDENTITY ( START WITH 1 INCREMENT BY 1),
  USER_ID     CHAR(36), --卖家id
  SCORE       DOUBLE DEFAULT 0,
  CREATEDTIME TIMESTAMP,
  PRIMARY KEY (id)
);

CREATE TABLE T_ec_item_seasonType (
  FABRIC_ID VARCHAR(36) NOT NULL,
  ID        VARCHAR(36) NOT NULL,
  TYPE      VARCHAR(36),
  PRIMARY KEY (fabric_id, id),
  FOREIGN KEY (fabric_id) REFERENCES T_ec_Fabric (id)
);

CREATE TABLE t_ec_materialScope (
  MATERIAL_ID VARCHAR(36) NOT NULL,
  ID          VARCHAR(36) NOT NULL,
  NAME        VARCHAR(36),
  PRIMARY KEY (material_id, id),
  FOREIGN KEY (material_id) REFERENCES T_ec_Material (id)
);

CREATE TABLE t_ec_fabricProvideType (
  FABRIC_ID VARCHAR(36) NOT NULL,
  ID        VARCHAR(36) NOT NULL,
  NAME      VARCHAR(36),
  PRIMARY KEY (fabric_id, id),
  FOREIGN KEY (fabric_id) REFERENCES T_ec_Fabric (id)
);

CREATE TABLE t_ec_materialProvideType (
  MATERIAL_ID VARCHAR(36) NOT NULL,
  ID          VARCHAR(36) NOT NULL,
  NAME        VARCHAR(36),
  PRIMARY KEY (material_id, id),
  FOREIGN KEY (material_id) REFERENCES T_ec_Material (id)
);

