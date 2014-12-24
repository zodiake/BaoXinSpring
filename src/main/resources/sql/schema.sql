CREATE TABLE T_ec_EcUser (
  id                CHAR(36) PRIMARY KEY, --主键
  userType          CHAR(1), --类型
  defaultAddress_id CHAR(36), --默认收货地址
  name              VARCHAR(10), --姓名
  password          VARCHAR(20), --密码
  createdTime       TIMESTAMP, --创建日期
  companyName       VARCHAR(20), --公司名称
  companyAddress    VARCHAR(20), --公司地址
  url               VARCHAR(30), --公司主页
  mobile            VARCHAR(20), --手机
  phone             VARCHAR(20), --座机
  focus             VARCHAR(50), --主营业务
  companyType       VARCHAR(10), --公司类型
  signUpAmount      VARCHAR(10), --注册资本
  annualAmount      VARCHAR(10), --年营业额
  peopleNum         VARCHAR(10)--员工人数
);

CREATE TABLE T_ec_address (
  id           CHAR(36) PRIMARY KEY,
  state        VARCHAR(10),
  city         VARCHAR(20),
  zipCode      VARCHAR(10),
  street       VARCHAR(50),
  receiverName VARCHAR(20),
  mobile       CHAR(11),
  zipPhone     VARCHAR(4),
  phone        VARCHAR(8),
  childPhone   VARCHAR(6),
  user_id      CHAR(36),
  createdTime  TIMESTAMP,
  updatedTime  TIMESTAMP
);

CREATE TABLE T_EC_SampleAddress (
  id           CHAR(36) PRIMARY KEY,
  title        VARCHAR(50),
  state        VARCHAR(10),
  city         VARCHAR(20),
  zipCode      VARCHAR(10),
  street       VARCHAR(50),
  receiverName VARCHAR(20),
  zipPhone     VARCHAR(4),
  phone        VARCHAR(8),
  childPhone   VARCHAR(6)
);

CREATE TABLE T_EC_OrderAddress (
  id           CHAR(36) PRIMARY KEY,
  title        VARCHAR(50),
  state        VARCHAR(10),
  city         VARCHAR(20),
  zipCode      VARCHAR(10),
  street       VARCHAR(50),
  receiverName VARCHAR(20),
  mobile       CHAR(11),
  zipPhone     VARCHAR(4),
  phone        VARCHAR(8),
  childPhone   VARCHAR(6)
);

ALTER TABLE T_ec_EcUser ADD FOREIGN KEY (defaultAddress_id) REFERENCES T_ec_address (id);

CREATE TABLE t_ec_content (
  id      CHAR(36) PRIMARY KEY,
  content VARCHAR(30000)
);

CREATE TABLE T_ec_Item (
  id            CHAR(36) PRIMARY KEY, --主键
  name          VARCHAR(32), --货品名称
  customId      VARCHAR(32), --货品编号
  validDateFrom TIMESTAMP, --有效期开始
  validDateTo   TIMESTAMP, --有效期结束
  updatedTime   TIMESTAMP, --更新日期
  createdTime   TIMESTAMP, --创建日期
  coverImage    VARCHAR(60),
  bidCount      INT DEFAULT 0, --成交笔数
  type          CHAR(1),
  createdBy     CHAR(36),
  price         DOUBLE,
  state         VARCHAR(5),
  content_id    CHAR(36),
  FOREIGN KEY (content_id) REFERENCES t_ec_content (id)
);


CREATE TABLE T_ec_MaterialCategory (
  id          CHAR(36) PRIMARY KEY,
  name        VARCHAR(20),
  parent_id   CHAR(36),
  createdBy   CHAR(36),
  createdTime TIMESTAMP,
  updatedBy   CHAR(36),
  updatedTime TIMESTAMP,
  isValid     INT DEFAULT 0,
  FOREIGN KEY (parent_id) REFERENCES T_ec_MaterialCategory (id)
);

CREATE TABLE T_ec_FabricCategory (
  id          CHAR(36) PRIMARY KEY,
  name        VARCHAR(20),
  parent_id   CHAR(36),
  createdBy   CHAR(36),
  createdTime TIMESTAMP,
  updatedBy   CHAR(36),
  updatedTime TIMESTAMP,
  isValid     INT DEFAULT 0,
  FOREIGN KEY (parent_id) REFERENCES T_ec_FabricCategory (id)
);

--原料成分
CREATE TABLE T_ec_FabricSource (
  id          CHAR(36) PRIMARY KEY,
  name        VARCHAR(20),
  parent_id   CHAR(36),
  createdBy   CHAR(36),
  updatedBy   CHAR(36),
  createdTime TIMESTAMP,
  updatedTime TIMESTAMP,
  isValid     INT DEFAULT 0,
  FOREIGN KEY (parent_id) REFERENCES T_ec_FabricSource (id)
);


CREATE TABLE T_ec_Fabric (
  id                       CHAR(36) PRIMARY KEY, --外键主键
  fabricSeasonType         VARCHAR(10), --适用季节
  ingredient               VARCHAR(40), --成分及含量
  yarn                     VARCHAR(40), --纱织
  density                  VARCHAR(20), --密度
  fabricWidthType          VARCHAR(10), --幅宽
  fabricHeightTYpe         VARCHAR(10), --克重
  fabricTechnologyId       VARCHAR(36), --染整工艺大类
  fabricSecondTechnologyId VARCHAR(36), --染整工艺小类
  itemNumber               VARCHAR(20), --货号
  fabricProvideType        VARCHAR(10), --供货方式
  shipInterval             INT DEFAULT 0, --发货时间多少天
  fabricMeasureType        VARCHAR(10), --计量单位
  availableSum             DOUBLE DEFAULT 0, --可售总量
  category_id              CHAR(36), --分类
  first_category_id        CHAR(36), --分类
  source_id                CHAR(36),
  detailSource_id          CHAR(36),
  FOREIGN KEY (id) REFERENCES T_ec_Item (id),
  FOREIGN KEY (category_id) REFERENCES T_ec_fabriccategory (id),
  FOREIGN KEY (source_id) REFERENCES T_ec_FabricSource (id),
  FOREIGN KEY (detailSource_id) REFERENCES T_ec_FabricSource (id)
);

CREATE TABLE T_ec_Material (
  id                  CHAR(36),
  materialType        VARCHAR(10), --类型
  widthAndSize        VARCHAR(10), --重量厚薄
  materialScope       VARCHAR(10), --适用范围
  providerType        VARCHAR(10), --供货方式
  materialMeasureType VARCHAR(10), --计量单位
  category_id         CHAR(36), --分类
  firstCategory_id    CHAR(36),
  availableSum        DOUBLE,
  SHIPINTERVAL        INT,
  FOREIGN KEY (ID) REFERENCES T_ec_item (ID),
  FOREIGN KEY (category_id) REFERENCES T_ec_MaterialCategory (id)
);

CREATE TABLE T_ec_fabric_range (
  fabric_id CHAR(36),
  unit_from DOUBLE DEFAULT 0,
  price     DOUBLE DEFAULT 0,
  PRIMARY KEY (fabric_id, unit_from),
  FOREIGN KEY (fabric_id) REFERENCES T_ec_fabric (id)
);

CREATE TABLE T_ec_material_range (
  material_id CHAR(36),
  unit_from   DOUBLE DEFAULT 0,
  price       DOUBLE DEFAULT 0,
  PRIMARY KEY (material_id, unit_from),
  FOREIGN KEY (material_id) REFERENCES T_ec_material (id)
);

CREATE TABLE T_ec_CultureImage (
  item_id     CHAR(36),
  location    VARCHAR(100),
  orderNum    INT DEFAULT 0,
  createdTime TIMESTAMP,
  updatedTime TIMESTAMP,
  PRIMARY KEY (item_id, location, orderNum),
  FOREIGN KEY (item_id) REFERENCES T_ec_item (id)
);

CREATE TABLE T_ec_pattern (
  item_id CHAR(36),
  name    CHAR(36),
  id      CHAR(36),
  PRIMARY KEY (item_id, name),
  FOREIGN KEY (item_id) REFERENCES T_ec_item (id)
);

CREATE TABLE T_ec_item_color (
  item_id  CHAR(36),
  color_id CHAR(36),
  PRIMARY KEY (item_id, color_id),
  FOREIGN KEY (item_id) REFERENCES T_ec_item (id)
);

CREATE TABLE T_ec_SampleOrder (
  id           CHAR(36) PRIMARY KEY,
  user_id      CHAR(32),
  address_id   CHAR(36),
  deliveryTime TIMESTAMP,
  provider_id  CHAR(36),
  remark       VARCHAR(200),
  state        VARCHAR(5),
  createdTime  TIMESTAMP,
  updatedTime  TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES T_ec_EcUser (id),
  FOREIGN KEY (provider_id) REFERENCES T_ec_EcUser (id),
  FOREIGN KEY (address_id) REFERENCES T_EC_SampleAddress (id)
);

CREATE TABLE T_ec_SampleLine (
  id         CHAR(36) PRIMARY KEY,
  item_id    CHAR(36),
  quantity   INT DEFAULT 0,
  sampleType VARCHAR(10),
  sample_id  CHAR(36),
  FOREIGN KEY (sample_id) REFERENCES T_ec_SampleOrder (id),
  FOREIGN KEY (item_id) REFERENCES T_ec_item (id)
);

CREATE TABLE T_ec_fabric_mainUse (
  fabric_id CHAR(36),
  id        CHAR(36),
  name      VARCHAR(10),
  orderNum  INT DEFAULT 0,
  PRIMARY KEY (fabric_id, id),
  FOREIGN KEY (fabric_id) REFERENCES T_ec_Fabric (id)
);

CREATE TABLE T_ec_DemandOrder (
  id               CHAR(36) PRIMARY KEY,
  title            VARCHAR(20),
  exceptionAddress VARCHAR(10),
  address_id       CHAR(36),
  demandType       VARCHAR(10),
  validDateFrom    TIMESTAMP,
  validDateTo      TIMESTAMP,
  provideType      VARCHAR(10),
  unit             VARCHAR(2),
  priceFrom        DOUBLE DEFAULT 0,
  priceTo          DOUBLE DEFAULT 0,
  demandSum        DOUBLE DEFAULT 0,
  user_id          CHAR(36),
  createdTime      TIMESTAMP,
  updatedTime      TIMESTAMP,
  deliveryDuration INT DEFAULT 0,
  content          VARCHAR(2000),
  state            VARCHAR(10),
  FOREIGN KEY (address_id) REFERENCES T_ec_address (id)
);

CREATE TABLE T_ec_DemandOrderImage (
  demandOrder_id CHAR(36),
  location       VARCHAR(20),
  orderNum       INT DEFAULT 0,
  createdTime    TIMESTAMP,
  updatedTime    TIMESTAMP,
  FOREIGN KEY (demandOrder_id) REFERENCES T_ec_DemandOrder (id),
  PRIMARY KEY (demandOrder_id, location)
);

CREATE TABLE T_ec_OrderItem (
  id              CHAR(36) PRIMARY KEY,
  orderNo         CHAR(36),
  summary         DOUBLE DEFAULT 0,
  buyer_id        CHAR(36),
  seller_id       CHAR(36),
  createdTime     TIMESTAMP,
  cancelTime      TIMESTAMP,
  deliverTime     TIMESTAMP,
  receiveTime     TIMESTAMP,
  orderAddress_id CHAR(36),
  status          VARCHAR(20),
  needInvoice     INT DEFAULT 1,
  FOREIGN KEY (orderAddress_id) REFERENCES T_EC_OrderAddress (id)
);

CREATE TABLE T_ec_OrderLine (
  id           CHAR(36) PRIMARY KEY,
  price        DOUBLE DEFAULT 0,
  sum          DOUBLE DEFAULT 0,
  quantity     INT DEFAULT 0,
  orderItem_id CHAR(36),
  item_id      CHAR(36),
  FOREIGN KEY (orderItem_id) REFERENCES T_ec_OrderItem (id),
  FOREIGN KEY (item_id) REFERENCES T_ec_Item (id)
);

CREATE TABLE T_ec_user_PreferFabricCategory (
  user_id     CHAR(36),
  category_id CHAR(36),
  PRIMARY KEY (user_id, category_id),
  FOREIGN KEY (category_id) REFERENCES T_ec_FabricCategory (id)
);

CREATE TABLE T_ec_user_PreferMaterialCategory (
  user_id     CHAR(36),
  category_id CHAR(36),
  PRIMARY KEY (user_id, category_id),
  FOREIGN KEY (category_id) REFERENCES T_ec_MaterialCategory (id)
);

CREATE TABLE t_ec_fabric_color (
  id        CHAR(36),
  fabric_id CHAR(36),
  rgb       CHAR(7),
  name      VARCHAR(20),
  PRIMARY KEY (fabric_id, id),
  FOREIGN KEY (fabric_id) REFERENCES T_ec_Fabric (id)
);

CREATE TABLE t_ec_material_color (
  id          CHAR(36),
  material_id CHAR(36),
  name        VARCHAR(20),
  rgb         CHAR(7),
  PRIMARY KEY (material_id, id),
  FOREIGN KEY (material_id) REFERENCES T_ec_Material (id)
);

CREATE TABLE t_ec_favouriteItems (
  user_id     CHAR(36),
  item_id     CHAR(36),
  createdTime TIMESTAMP,
  PRIMARY KEY (user_id, item_id),
  FOREIGN KEY (item_id) REFERENCES T_ec_Item (id)
);

CREATE TABLE t_ec_shop (
  id      CHAR(36) PRIMARY KEY,
  user_id CHAR(36)
);

CREATE TABLE t_ec_favouriteShops (
  shop_id     CHAR(36),
  user_id     CHAR(36),
  createdTime TIMESTAMP,
  PRIMARY KEY (user_id, shop_id),
  FOREIGN KEY (shop_id) REFERENCES T_ec_Shop (id)

);
--买家评价表
CREATE TABLE T_ec_Comment (
  id            CHAR(36) PRIMARY KEY,
  item_id       CHAR(36),
  user_id       CHAR(36),
  createdTime   TIMESTAMP,
  updatedTime   TIMESTAMP,
  content       VARCHAR(200),
  attitude      INT DEFAULT 0,
  deliverySpeed INT DEFAULT 0,
  satisfied     INT DEFAULT 0,
  order_id      CHAR(36),
  type          CHAR(4),
  FOREIGN KEY (item_id) REFERENCES T_ec_Item (id),
  FOREIGN KEY (order_id) REFERENCES T_ec_OrderItem (id)
);

--卖家评价表
CREATE TABLE T_ec_sellerComment (
  id          CHAR(36) PRIMARY KEY,
  item_id     CHAR(36),
  user_id     CHAR(36),
  createdTime TIMESTAMP,
  updatedTime TIMESTAMP,
  content     VARCHAR(200),
  order_id    CHAR(36),
  type        CHAR(4),
  FOREIGN KEY (item_id) REFERENCES T_ec_Item (id),
  FOREIGN KEY (order_id) REFERENCES T_ec_OrderItem (id)
);

--站内信表
CREATE TABLE T_ec_insideLetter (
  id           CHAR(36) PRIMARY KEY,
  letterType   INT DEFAULT 0, --站内信类型，0：系统通知，1：其他通知
  letterStatus INT DEFAULT 0, --站内信状态0：未读，1：已读，2：已删除
  content      VARCHAR(200), --内容
  title        VARCHAR(20), --标题
  createdTime  TIMESTAMP DEFAULT current_timestamp, --创建时间
  sender_id    VARCHAR(10), --发件人
  receiver_id  VARCHAR(10), --收件人
  FOREIGN KEY (sender_id) REFERENCES T_ec_EcUser (id),
  FOREIGN KEY (receiver_id) REFERENCES T_ec_EcUser (id)
);
------------------------------------------------------------------------后台管理数据表--------------------------------------------------------------------------
CREATE TABLE T_ec_information_category (
  id           CHAR(36) PRIMARY KEY,
  categoryName CHAR(36), --资讯分类名称
  createdTime  TIMESTAMP,
  isValid      INT DEFAULT 0
);

CREATE TABLE T_ec_information (
  id          CHAR(36) PRIMARY KEY,
  category_id CHAR(36), --资讯分类id
  title       VARCHAR(100), --资讯title
  content     VARCHAR(5000), --资讯正文
  coverPath   VARCHAR(500), --封面图片路径
  createdTime TIMESTAMP,
  isValid     INT DEFAULT 0, --是否有效
  user_id     VARCHAR(36), --创建人
  FOREIGN KEY (category_id) REFERENCES T_ec_information_category (id)
);
CREATE TABLE T_ec_ad_position (
  id          CHAR(36) PRIMARY KEY,
  positionNo  VARCHAR(50), --栏位编号
  name        VARCHAR(100), --栏位名称
  description VARCHAR(500), --说明
  createdTime TIMESTAMP,
  isValid     INT DEFAULT 0
);
CREATE TABLE T_ec_ad (
  id          CHAR(36) PRIMARY KEY,
  position_id VARCHAR(50), --广告栏位id
  title       VARCHAR(100), --广告title
  link        VARCHAR(500), --广告链接
  coverPath   VARCHAR(500), --封面图片路径
  createdTime TIMESTAMP,
  isValid     INT DEFAULT 0, --是否有效
  FOREIGN KEY (position_id) REFERENCES T_ec_ad_position (id)
);

------------------------------------------------------------------------定时任务数据表--------------------------------------------------------------------------
--卖家维度率表
CREATE TABLE T_ec_dimension_rate (
  id            INT GENERATED ALWAYS AS IDENTITY ( START WITH 1 INCREMENT BY 1 ),
  user_id       CHAR(36), --卖家id
  attitude      DOUBLE DEFAULT 0, --服务态度
  deliverySpeed DOUBLE DEFAULT 0, --发货速度
  satisfied     DOUBLE DEFAULT 0, --宝贝描述符合度
  createdTime   TIMESTAMP,
  PRIMARY KEY (id)
);

--买家累计信用
CREATE TABLE T_ec_sellerCredit (
  id                  INT GENERATED ALWAYS AS IDENTITY ( START WITH 1 INCREMENT BY 1),
  user_id             CHAR(36), --卖家id
  type                CHAR(4), --满意类型:好评、中评、差评
  weekCount           INT DEFAULT 0, --最近一周次数
  oneMonthCount       INT DEFAULT 0, --最近一个月次数
  sixMonthCount       INT DEFAULT 0, --最近六个月次数
  sixMonthBeforeCount INT DEFAULT 0, --六个月前次数
  total               INT DEFAULT 0, --一年的总次数
  createdTime         TIMESTAMP,
  PRIMARY KEY (id)
);

--综合评分
CREATE TABLE T_ec_Composite_Score (
  id          INT GENERATED ALWAYS AS IDENTITY ( START WITH 1 INCREMENT BY 1),
  user_id     CHAR(36), --卖家id
  score       DOUBLE DEFAULT 0,
  createdTime TIMESTAMP,
  PRIMARY KEY (id)
);

CREATE TABLE T_ec_item_seasonType (
  fabric_id VARCHAR(36),
  id        VARCHAR(36),
  type      VARCHAR(36),
  PRIMARY KEY (fabric_id, id),
  FOREIGN KEY (fabric_id) REFERENCES T_ec_Fabric (id)
);

CREATE TABLE t_ec_materialScope (
  material_id VARCHAR(36),
  id          VARCHAR(36),
  name        VARCHAR(36),
  PRIMARY KEY (material_id, id),
  FOREIGN KEY (material_id) REFERENCES T_ec_Material (id)
);

CREATE TABLE t_ec_fabricProvideType (
  fabric_id VARCHAR(36),
  id        VARCHAR(36),
  name      VARCHAR(36),
  PRIMARY KEY (fabric_id, id),
  FOREIGN KEY (fabric_id) REFERENCES T_ec_Fabric (id)
);

CREATE TABLE t_ec_materialProvideType (
  material_id VARCHAR(36),
  id          VARCHAR(36),
  name        VARCHAR(36),
  PRIMARY KEY (material_id, id),
  FOREIGN KEY (material_id) REFERENCES T_ec_Material (id)
);

alter table t_ec_demandOrder
add column coverImage varchar(60);

alter table t_ec_demandOrderimage alter column  location set data type varchar(100);

alter table t_ec_demandOrder
add column demandOrderNo varchar(20);

alter table t_ec_comment
add column hasName int default 0;

alter table t_ec_sellercomment
add column hasName int default 0;

alter table t_ec_comment
add column defaultShow int default 0;

alter table t_ec_sellercomment
add column defaultShow int default 0;

alter table t_ec_item
add column viewCount int default 0;

alter table t_ec_insideletter
add column flag int default 0;

alter table t_ec_insideletter
add column url varchar(100) ;

alter table T_ec_DemandOrder
alter providetype set data type varchar(20) ;

alter table t_ec_insideletter
add column refers varchar(20)

create table t_ec_ipConfig(
  id INT GENERATED ALWAYS AS IDENTITY ( START WITH 1 INCREMENT BY 1),
  ip varchar(60),
  createdTime timestamp ,
  flag int default 0,
  primary key(id)
);
