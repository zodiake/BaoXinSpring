--测试用户
INSERT INTO t_ec_EcUser (id, name, companyName, companyAddress) VALUES ('100047', 'tom', '沪上纺织精品', '宛平南路600号');
INSERT INTO T_ec_EcUser (id, name) VALUES ('100048', 'mary');
INSERT INTO T_ec_EcUser (id, name) VALUES ('100049', 'sam');

--测试面料分类
INSERT INTO T_ec_fabricCategory (id, name, isValid) VALUES ('1a000727-880b-4504-b237-34d5ac11945a', '染色类', 0);
INSERT INTO T_ec_fabricCategory (id, name, parent_id, isValid) VALUES ('749206dc-a3a5-442f-8149-f35bc4f0f747', '蜡染', '1a000727-880b-4504-b237-34d5ac11945a', 0);
INSERT INTO T_ec_fabricCategory (id, name, parent_id, isValid) VALUES ('e6320adf-2516-4a77-a077-67339fb95cb9', '涂染', '1a000727-880b-4504-b237-34d5ac11945a', 0);
INSERT INTO T_ec_fabricCategory (id, name, parent_id, isValid) VALUES ('57cb6f6b-ed5e-408c-8263-470aaa4daff7', '刮染', '1a000727-880b-4504-b237-34d5ac11945a', 0);
INSERT INTO T_ec_fabricCategory (id, name, parent_id, isValid) VALUES ('156722f3-f625-467e-8f03-fdfeb426c42a', '染色+印花', '1a000727-880b-4504-b237-34d5ac11945a', 0);
INSERT INTO T_ec_fabricCategory (id, name, parent_id, isValid) VALUES ('210ab9aa-775c-4060-a385-4eafdad2d2c0', '常规染色', '1a000727-880b-4504-b237-34d5ac11945a', 0);
INSERT INTO T_ec_fabricCategory (id, name, isValid) VALUES ('697b90ec-578c-41f5-a9c9-547c1d7bfb40', '色织类', 0);
INSERT INTO T_ec_fabricCategory (id, name, parent_id, isValid) VALUES ('63806d98-82f9-48e8-8193-8a555faa41cb', '条纹', '697b90ec-578c-41f5-a9c9-547c1d7bfb40', 0);
INSERT INTO T_ec_fabricCategory (id, name, parent_id, isValid) VALUES ('c83c8893-0cbc-4d91-bd0e-3820427c029a', '格子', '697b90ec-578c-41f5-a9c9-547c1d7bfb40', 0);
INSERT INTO T_ec_fabricCategory (id, name, parent_id, isValid) VALUES ('6245914e-58b0-4f4d-999d-2aabbc42f313', '斜纹', '697b90ec-578c-41f5-a9c9-547c1d7bfb40', 0);
INSERT INTO T_ec_fabricCategory (id, name, parent_id, isValid) VALUES ('8694a785-b596-4d4b-853a-50fca6a5518a', '乱纹', '697b90ec-578c-41f5-a9c9-547c1d7bfb40', 0);

--辅料分类测试数据
INSERT INTO T_ec_MaterialCategory (id, name, isValid) VALUES ('c2da322a-cfc7-469f-bf82-8aa1653713dc', '衬料', 0);
INSERT INTO T_ec_MaterialCategory (id, name, parent_id, isValid) VALUES ('d8190677-1e8a-4772-a623-b6036680979f', '棉衬', 'c2da322a-cfc7-469f-bf82-8aa1653713dc', 0);
INSERT INTO T_ec_MaterialCategory (id, name, parent_id, isValid) VALUES ('3aec3ca2-fb2e-487d-b7ef-f08dcf5933a7', '麻衬', 'c2da322a-cfc7-469f-bf82-8aa1653713dc', 0);
INSERT INTO T_ec_MaterialCategory (id, name, parent_id, isValid) VALUES ('51002fd0-2a5b-4f40-bc57-629a2ed41641', '马尾衬', 'c2da322a-cfc7-469f-bf82-8aa1653713dc', 0);
INSERT INTO T_ec_MaterialCategory (id, name, parent_id, isValid) VALUES ('35c54c95-6e51-4dd0-ac75-d1c2f433a9c7', '树脂衬', 'c2da322a-cfc7-469f-bf82-8aa1653713dc', 0);
INSERT INTO T_ec_MaterialCategory (id, name, parent_id, isValid) VALUES ('68c8f824-5a0c-4933-9866-0c3cfd460742', '涤纶衬', 'c2da322a-cfc7-469f-bf82-8aa1653713dc', 0);
INSERT INTO T_ec_MaterialCategory (id, name, parent_id, isValid) VALUES ('039e4ed9-5ee5-4602-8183-02bbc82eb3b3', '粘合衬/热熔衬', 'c2da322a-cfc7-469f-bf82-8aa1653713dc', 0);
INSERT INTO T_ec_MaterialCategory (id, name, isValid) VALUES ('7e9ea339-cdbe-46bc-8c71-ad18154c140b', '里料', 0);
INSERT INTO T_ec_MaterialCategory (id, name, parent_id, isValid) VALUES ('372ae361-9974-41b4-9880-fc39705196d8', '细布', '7e9ea339-cdbe-46bc-8c71-ad18154c140b', 0);
INSERT INTO T_ec_MaterialCategory (id, name, parent_id, isValid) VALUES ('d0ed9985-8492-4511-89e7-0e43b8a4d5de', '市布', '7e9ea339-cdbe-46bc-8c71-ad18154c140b', 0);
INSERT INTO T_ec_MaterialCategory (id, name, parent_id, isValid) VALUES ('091ad72b-55a7-4d51-be1d-c41de5668a80', '绒布', '7e9ea339-cdbe-46bc-8c71-ad18154c140b', 0);
INSERT INTO T_ec_MaterialCategory (id, name, parent_id, isValid) VALUES ('c1fea49e-4072-47fe-a314-3ee6f1b6e01e', '小纺', '7e9ea339-cdbe-46bc-8c71-ad18154c140b', 0);
INSERT INTO T_ec_MaterialCategory (id, name, parent_id, isValid) VALUES ('d02c08f8-8886-4843-a699-bad201c7354b', '软缎', '7e9ea339-cdbe-46bc-8c71-ad18154c140b', 0);
INSERT INTO T_ec_MaterialCategory (id, name, parent_id, isValid) VALUES ('ebb0839b-69d7-430c-9d37-a6ccc9b6f52f', '羽纱', '7e9ea339-cdbe-46bc-8c71-ad18154c140b', 0);
INSERT INTO T_ec_MaterialCategory (id, name, parent_id, isValid) VALUES ('7020a1b2-06b8-4eb4-9a3d-fe73fffcc0f8', '锦纶', '7e9ea339-cdbe-46bc-8c71-ad18154c140b', 0);

--测试item
INSERT INTO T_ec_Item (id, customId, name, createdBy, createdTime, state)
VALUES ('1', '123', '极品面料', '100047', '2013-1-1 14:12:15', '出售中');
INSERT INTO T_ec_Item (id, customId, name, createdBy, createdTime, state)
VALUES ('2', '234', '测试辅料1', '100047', '2013-1-1 14:13:15', '出售中');
INSERT INTO T_ec_Item (id, customId, name, createdBy, createdTime, state)
VALUES ('3', '345', '测试辅料2', '100047', '2013-1-1 14:14:15', '出售中');
INSERT INTO T_ec_item (id, name, customId, createdBy, createdTime, state)
VALUES ('4', 'item4', '789', '100047', '2013-1-1 14:15:15', '出售中');
INSERT INTO T_ec_item (id, name, customId, createdBy, createdTime, state)
VALUES ('5', 'item5', '901', '100047', '2013-1-1 14:16:15', '出售中');
INSERT INTO T_ec_item (id, name, customId, createdBy, createdTime, state)
VALUES ('6', 'item6', '134', '100047', '2013-1-1 14:17:15', '出售中');
INSERT INTO T_ec_item (id, name, customId, createdBy, createdTime, state)
VALUES ('7', 'item7', '789', '100047', '2013-1-1 14:15:15', '出售中');
INSERT INTO T_ec_item (id, name, customId, createdBy, createdTime, state)
VALUES ('8', 'item8', '901', '100047', '2013-1-1 14:16:15', '出售中');
INSERT INTO T_ec_item (id, name, customId, createdBy, createdTime, state)
VALUES ('9', 'item9', '134', '100047', '2013-1-1 14:17:15', '出售中');

--测试用2号用户发布的商品
INSERT INTO T_ec_item (id, name, customId, createdBy, createdTime, state)
VALUES ('10', 'favourItem10', '134', '100048', '2013-1-1 14:17:15', '出售中');
INSERT INTO T_ec_item (id, name, customId, createdBy, createdTime, state)
VALUES ('11', 'favourItem11', '134', '100048', '2013-1-1 14:17:16', '出售中');
INSERT INTO T_ec_item (id, name, customId, createdBy, createdTime, state)
VALUES ('12', 'favourItem12', '134', '100048', '2013-1-1 14:17:17', '出售中');
INSERT INTO T_ec_item (id, name, customId, createdBy, createdTime, state)
VALUES ('13', 'favourItem13', '134', '100048', '2013-1-1 14:17:18', '出售中');

--测试用3号用户发布的商品
INSERT INTO T_ec_item (id, name, customId, createdBy, createdTime, state)
VALUES ('14', 'favourItem14', '134', '100049', '2013-1-1 14:17:15', '出售中');
INSERT INTO T_ec_item (id, name, customId, createdBy, createdTime, state)
VALUES ('15', 'favourItem15', '134', '100049', '2013-1-1 14:17:16', '出售中');
INSERT INTO T_ec_item (id, name, customId, createdBy, createdTime, state)
VALUES ('16', 'favourItem16', '134', '100049', '2013-1-1 14:17:17', '出售中');
INSERT INTO T_ec_item (id, name, customId, createdBy, createdTime, state)
VALUES ('17', 'favourItem17', '134', '100049', '2013-1-1 14:17:18', '出售中');

--测试面料成分
INSERT INTO T_ec_FabricSource (id, name) VALUES ('1', '天然纤维');
INSERT INTO T_ec_FabricSource (id, name, parent_id) VALUES ('2', '棉', '1');
INSERT INTO T_ec_FabricSource (id, name, parent_id) VALUES ('3', '木棉', '1');
INSERT INTO T_ec_FabricSource (id, name, parent_id) VALUES ('4', '羊毛', '1');
INSERT INTO T_ec_FabricSource (id, name, parent_id) VALUES ('5', '兔毛', '1');
INSERT INTO T_ec_FabricSource (id, name, parent_id) VALUES ('6', '真丝', '1');
INSERT INTO T_ec_FabricSource (id, name, parent_id) VALUES ('7', '绢丝', '1');
INSERT INTO T_ec_FabricSource (id, name, parent_id) VALUES ('8', '秘鲁棉', '1');
INSERT INTO T_ec_FabricSource (id, name, parent_id) VALUES ('9', '有机棉', '1');
INSERT INTO T_ec_FabricSource (id, name) VALUES ('10', '化学纤维');
INSERT INTO T_ec_FabricSource (id, name, parent_id) VALUES ('11', '涤纶', '10');
INSERT INTO T_ec_FabricSource (id, name, parent_id) VALUES ('12', '氨纶', '10');
INSERT INTO T_ec_FabricSource (id, name, parent_id) VALUES ('13', '腈纶', '10');
INSERT INTO T_ec_FabricSource (id, name, parent_id) VALUES ('14', '锦纶', '10');
INSERT INTO T_ec_FabricSource (id, name, parent_id) VALUES ('15', '丙纶', '10');
INSERT INTO T_ec_FabricSource (id, name, parent_id) VALUES ('16', '维纶', '10');

--测试面料
INSERT INTO T_ec_Fabric (id, category_id, source_id, detailSource_id, first_category_id) VALUES
  ('1', '749206dc-a3a5-442f-8149-f35bc4f0f747', '1', '3', '1a000727-880b-4504-b237-34d5ac11945a');
INSERT INTO T_ec_Fabric (id, category_id) VALUES ('2', '749206dc-a3a5-442f-8149-f35bc4f0f747');
INSERT INTO T_ec_Fabric (id, category_id) VALUES ('3', '749206dc-a3a5-442f-8149-f35bc4f0f747');
INSERT INTO T_ec_Fabric (id, category_id) VALUES ('4', 'e6320adf-2516-4a77-a077-67339fb95cb9');
INSERT INTO T_ec_Fabric (id, category_id) VALUES ('5', '57cb6f6b-ed5e-408c-8263-470aaa4daff7');

INSERT INTO T_ec_Fabric (id, category_id) VALUES ('10', '57cb6f6b-ed5e-408c-8263-470aaa4daff7');
INSERT INTO T_ec_Fabric (id, category_id) VALUES ('11', '57cb6f6b-ed5e-408c-8263-470aaa4daff7');
INSERT INTO T_ec_Fabric (id, category_id) VALUES ('12', '57cb6f6b-ed5e-408c-8263-470aaa4daff7');
INSERT INTO T_ec_Fabric (id, category_id) VALUES ('13', '57cb6f6b-ed5e-408c-8263-470aaa4daff7');

--测试辅料
INSERT INTO T_ec_Material (id, category_id, firstCategory_id, materialType, materialMeasureType)
VALUES ('6', 'd8190677-1e8a-4772-a623-b6036680979f', 'c2da322a-cfc7-469f-bf82-8aa1653713dc', '现货', '米1');
INSERT INTO T_ec_Material (id, category_id, firstCategory_id) VALUES ('7', 'd8190677-1e8a-4772-a623-b6036680979f', 'c2da322a-cfc7-469f-bf82-8aa1653713dc');
INSERT INTO T_ec_Material (id, category_id, firstCategory_id) VALUES ('8', 'd8190677-1e8a-4772-a623-b6036680979f', 'c2da322a-cfc7-469f-bf82-8aa1653713dc');
INSERT INTO T_ec_Material (id, category_id, firstCategory_id) VALUES ('9', 'd8190677-1e8a-4772-a623-b6036680979f', 'c2da322a-cfc7-469f-bf82-8aa1653713dc');

INSERT INTO T_ec_Material (id, category_id, firstCategory_id) VALUES ('14', 'd8190677-1e8a-4772-a623-b6036680979f', 'c2da322a-cfc7-469f-bf82-8aa1653713dc');
INSERT INTO T_ec_Material (id, category_id, firstCategory_id) VALUES ('15', 'd8190677-1e8a-4772-a623-b6036680979f', 'c2da322a-cfc7-469f-bf82-8aa1653713dc');
INSERT INTO T_ec_Material (id, category_id, firstCategory_id) VALUES ('16', 'd8190677-1e8a-4772-a623-b6036680979f', 'c2da322a-cfc7-469f-bf82-8aa1653713dc');
INSERT INTO T_ec_Material (id, category_id, firstCategory_id) VALUES ('17', 'd8190677-1e8a-4772-a623-b6036680979f', 'c2da322a-cfc7-469f-bf82-8aa1653713dc');
--测试辅料供货方式
INSERT INTO t_ec_materialProvideType (material_id, id, name) VALUES ('6', '1', '现货');

--测试辅料适用范围
INSERT INTO t_ec_materialScope (material_id, id, name) VALUES ('6', '1', 'type1');


--测试面料主要使用方式
INSERT INTO t_ec_fabric_mainUse (fabric_id, id, name, orderNum) VALUES ('1', '1', '男装', 1);
INSERT INTO t_ec_fabric_mainUse (fabric_id, id, name, orderNum) VALUES ('1', '2', '女装', 1);

--
INSERT INTO T_ec_item_seasonType (fabric_id, id, type) VALUES ('1', '1', '春季');
INSERT INTO T_ec_item_seasonType (fabric_id, id, type) VALUES ('1', '2', '夏季');

--测试面料图片位置
INSERT INTO T_ec_CultureImage (item_id, location, orderNum)
VALUES ('1', '/resources/pic/logo.jpg', 1);
INSERT INTO T_ec_CultureImage (item_id, location, orderNum)
VALUES ('1', '/resources/pic/logo.jpg', 2);
INSERT INTO T_ec_CultureImage (item_id, location, orderNum)
VALUES ('1', '/resources/pic/logo.jpg', 3);
INSERT INTO T_ec_CultureImage (item_id, location, orderNum)
VALUES ('1', '/resources/pic/logo.jpg', 4);

--测试面料价格
INSERT INTO T_ec_fabric_range (fabric_id, unit_from, price) VALUES ('1', 5, 20);
INSERT INTO T_ec_fabric_range (fabric_id, unit_from, price) VALUES ('1', 10, 15);
INSERT INTO T_ec_fabric_range (fabric_id, unit_from, price) VALUES ('1', 20, 10);
INSERT INTO T_ec_fabric_range (fabric_id, unit_from, price) VALUES ('2', 30, 40);
INSERT INTO T_ec_fabric_range (fabric_id, unit_from, price) VALUES ('2', 40, 35);
INSERT INTO T_ec_fabric_range (fabric_id, unit_from, price) VALUES ('3', 30, 40);
INSERT INTO T_ec_fabric_range (fabric_id, unit_from, price) VALUES ('3', 50, 70);
INSERT INTO T_ec_fabric_range (fabric_id, unit_from, price) VALUES ('4', 40, 35);
INSERT INTO T_ec_fabric_range (fabric_id, unit_from, price) VALUES ('5', 30, 40);
INSERT INTO T_ec_fabric_range (fabric_id, unit_from, price) VALUES ('5', 40, 35);

--测试辅料价格
INSERT INTO T_ec_material_range (material_id, unit_from, price) VALUES ('6', 5, 20);
INSERT INTO T_ec_material_range (material_id, unit_from, price) VALUES ('6', 10, 15);
INSERT INTO T_ec_material_range (material_id, unit_from, price) VALUES ('6', 20, 10);


--评价测试数据
INSERT INTO T_ec_Comment (id, item_id, user_id, createdTime, type, content, attitude, satisfied, deliverySpeed)
VALUES ('1', '2', '100047', '2014-5-19 12:23:34', '好评', '11', 3, 4, 5);
INSERT INTO T_ec_Comment (id, item_id, user_id, createdTime, type, content, attitude, satisfied, deliverySpeed)
VALUES ('2', '2', '100047', '2014-5-19 12:23:34', '中评', '22', 4, 5, 5);
INSERT INTO T_ec_Comment (id, item_id, user_id, createdTime, type, content, attitude, satisfied, deliverySpeed)
VALUES ('3', '2', '100047', '2014-5-19 12:23:34', '差评', '33', 5, 5, 5);
INSERT INTO T_ec_Comment (id, item_id, user_id, createdTime, type, content, attitude, satisfied, deliverySpeed)
VALUES ('4', '2', '100047', '2014-5-19 12:23:34', '好评', '44', 4, 3, 5);
INSERT INTO T_ec_Comment (id, item_id, user_id, createdTime, type, content, attitude, satisfied, deliverySpeed)
VALUES ('5', '2', '100047', '2014-5-19 12:23:34', '中评', '55', 2, 2, 5);
INSERT INTO T_ec_Comment (id, item_id, user_id, createdTime, type, content, attitude, satisfied, deliverySpeed)
VALUES ('6', '2', '100047', '2014-5-19 12:23:34', '差评', '66', 3, 5, 5);
INSERT INTO T_ec_Comment (id, item_id, user_id, createdTime, type, content, attitude, satisfied, deliverySpeed)
VALUES ('7', '1', '100047', '2014-5-19 12:23:34', '好评', '11', 3, 4, 5);
INSERT INTO T_ec_Comment (id, item_id, user_id, createdTime, type, content, attitude, satisfied, deliverySpeed)
VALUES ('8', '1', '100047', '2014-5-19 12:23:34', '中评', '22', 4, 5, 5);
INSERT INTO T_ec_Comment (id, item_id, user_id, createdTime, type, content, attitude, satisfied, deliverySpeed)
VALUES ('9', '1', '100047', '2014-5-19 12:23:34', '差评', '33', 5, 5, 5);
INSERT INTO T_ec_Comment (id, item_id, user_id, createdTime, type, content, attitude, satisfied, deliverySpeed)
VALUES ('10', '1', '100047', '2014-5-19 12:23:34', '好评', '44', 4, 3, 5);
INSERT INTO T_ec_Comment (id, item_id, user_id, createdTime, type, content, attitude, satisfied, deliverySpeed)
VALUES ('11', '1', '100047', '2014-5-19 12:23:34', '中评', '55', 2, 2, 5);
INSERT INTO T_ec_Comment (id, item_id, user_id, createdTime, type, content, attitude, satisfied, deliverySpeed)
VALUES ('12', '1', '100047', '2014-5-19 12:23:34', '差评', '66', 3, 5, 5);

INSERT INTO T_ec_orderItem (id, orderNo, buyer_id, seller_id, createdTime, status, receiveTime)
VALUES ('o1', 'eeee1', '100047', '100048', '2014-5-22 12:23:33', 'FINISH', '2014-6-22 12:23:33');
INSERT INTO T_ec_orderItem (id, orderNo, buyer_id, seller_id, createdTime, status, receiveTime)
VALUES ('o2', 'eeee2', '100047', '100048', '2014-5-22 12:23:33', 'FINISH', '2014-6-22 12:23:33');

INSERT INTO T_ec_OrderLine (id, sum, quantity, orderItem_id, item_id, price)
VALUES ('011', '111', '1', 'o1', '2', 3.23);
INSERT INTO T_ec_OrderLine (id, sum, quantity, orderItem_id, item_id, price)
VALUES ('012', '112', '2', 'o1', '6', 232.23);
INSERT INTO T_ec_OrderLine (id, sum, quantity, orderItem_id, item_id, price)
VALUES ('022', '111', '1', 'o2', '4', 232.32);

--测试收货地址
INSERT INTO T_ec_address (id, user_id, state, city, zipCode, street, receiverName, mobile, zipPhone, phone, childPhone, createdTime, updatedTime)
VALUES ('1', '100047', '上海', '上海', '203323', '浦东新区浦电路370号', 'Charles', '1862229323', '021', '23839434', '2343', '2014-05-28',
        '2014-05-28');
INSERT INTO T_ec_address (id, user_id, state, city, zipCode, street, receiverName, mobile, zipPhone, phone, childPhone, createdTime, updatedTime)
VALUES ('2', '100047', '上海', '上海', '203323', '浦东新区浦电路370号', 'Sam', '1862229342', '021', '23839434', '2222', '2014-04-28',
        '2014-04-28');
UPDATE T_ec_EcUser
SET defaultAddress_id = '1'
WHERE id = '100047';
--求购单测试
INSERT INTO t_ec_demandorder (id, address_id, demandType, user_id, title, createdTime, validDateTo)
VALUES ('1', '1', '服务', '100047', 'demandOrder', '2014-6-12 12:00:12', '2014-6-30 12:00:12');

--测试调样单

--关注的商品
INSERT INTO t_ec_favouriteItems (user_id, item_id, createdTime) VALUES ('100047', '2', '2014-4-4 12:12:12');
INSERT INTO t_ec_favouriteItems (user_id, item_id, createdTime) VALUES ('100047', '3', '2014-4-5 12:12:12');
INSERT INTO t_ec_favouriteItems (user_id, item_id, createdTime) VALUES ('100047', '4', '2014-4-6 12:12:12');

--测试店铺
INSERT INTO t_ec_shop (id, user_id) VALUES ('1', '100048');
INSERT INTO t_ec_shop (id, user_id) VALUES ('2', '100049');

--测试收藏的店铺
INSERT INTO t_ec_favouriteShops (shop_id, user_id, createdTime) VALUES ('1', '1', '2014-4-6 12:12:12');
INSERT INTO t_ec_favouriteShops (shop_id, user_id, createdTime) VALUES ('2', '1', '2014-4-5 12:12:12');

--测试广告栏位
INSERT INTO T_ec_ad_position (id, positionNo, name, description, isValid)
VALUES ('wewew', 'ewewewewe', 'wewwewe', 'dsadasdsadsda', 0);
--测试广告栏位
INSERT INTO T_ec_ad (id, position_id, title, link, coverPath, isValid)
VALUES ('asdsadasd', 'wewew', 'fsdfsdfsdfsd', 'asdsadsadsadsa', 'xcvxcvxcvxvcxv', 0);

--卖家评价数据
INSERT INTO T_ec_sellerComment (id, item_id, user_id, type, content, createdTime, order_id)
VALUES ('1', '1', '100047', '好评', '11', '2014-6-4 12:23:00', 'o1');
INSERT INTO T_ec_sellerComment (id, item_id, user_id, type, content, createdTime, order_id)
VALUES ('2', '1', '100047', '好评', '22', '2014-6-4 12:24:00', 'o2');
INSERT INTO T_ec_sellerComment (id, item_id, user_id, type, content, createdTime)
VALUES ('7', '1', '100047', '中评', '77', '2014-6-4 12:29:00');
INSERT INTO T_ec_sellerComment (id, item_id, user_id, type, content, createdTime)
VALUES ('8', '1', '100047', '中评', '88', '2014-6-4 12:23:01');
INSERT INTO T_ec_sellerComment (id, item_id, user_id, type, content, createdTime)
VALUES ('9', '1', '100047', '中评', '99', '2014-6-4 12:23:02');
INSERT INTO T_ec_sellerComment (id, item_id, user_id, type, content, createdTime)
VALUES ('10', '1', '100047', '好评', '99', '2014-6-4 12:23:03');
INSERT INTO T_ec_sellerComment (id, item_id, user_id, type, content, createdTime)
VALUES ('11', '1', '100047', '好评', '99', '2014-6-4 12:23:04');

--用户常用辅料分类
INSERT INTO T_ec_user_PreferMaterialCategory (user_id, category_id) VALUES ('100047', 'd8190677-1e8a-4772-a623-b6036680979f');
INSERT INTO T_ec_user_PreferMaterialCategory (user_id, category_id) VALUES ('100047', '3aec3ca2-fb2e-487d-b7ef-f08dcf5933a7');
INSERT INTO T_ec_user_PreferMaterialCategory (user_id, category_id) VALUES ('100047', '51002fd0-2a5b-4f40-bc57-629a2ed41641');
INSERT INTO T_ec_user_PreferMaterialCategory (user_id, category_id) VALUES ('100047', '68c8f824-5a0c-4933-9866-0c3cfd460742');

--用户常用面料分类
INSERT INTO T_ec_user_PreferFabricCategory (user_id, category_id) VALUES ('100047', '63806d98-82f9-48e8-8193-8a555faa41cb');
INSERT INTO T_ec_user_PreferFabricCategory (user_id, category_id) VALUES ('100047', 'c83c8893-0cbc-4d91-bd0e-3820427c029a');
INSERT INTO T_ec_user_PreferFabricCategory (user_id, category_id) VALUES ('100047', '8694a785-b596-4d4b-853a-50fca6a5518a');
INSERT INTO T_ec_user_PreferFabricCategory (user_id, category_id) VALUES ('100047', '156722f3-f625-467e-8f03-fdfeb426c42a');

--店铺动态评分
INSERT INTO T_ec_dimension_rate (user_id, attitude, deliverySpeed, satisfied)
VALUES ('100048', 3, 4, 5);

--买家信用累计
INSERT INTO T_ec_sellerCredit (user_id, type, weekCount, oneMonthCount, sixMonthBeforeCount, sixMonthCount, total)
VALUES ('100047', '好评', 1, 3, 5, 7, 20);
INSERT INTO T_ec_sellerCredit (user_id, type, weekCount, oneMonthCount, sixMonthBeforeCount, sixMonthCount, total)
VALUES ('100047', '中评', 1, 3, 5, 7, 20);
INSERT INTO T_ec_sellerCredit (user_id, type, weekCount, oneMonthCount, sixMonthBeforeCount, sixMonthCount, total)
VALUES ('100047', '差评', 1, 3, 5, 7, 20);

--综合评分
INSERT INTO T_ec_Composite_Score (user_id, score)
VALUES ('100048', '5');

--站内信
insert into T_ec_insideLetter(id,title,content,sender_id,receiver_id,letterStatus,letterType)
values ('1','aaaa1','aaaaaaaaaaaaaa1','100047','100048',0,1);
insert into T_ec_insideLetter(id,title,content,sender_id,receiver_id,letterStatus,letterType)
values ('2','aaaa1','aaaaaaaaaaaaaa2','100047','100048',0,1);
insert into T_ec_insideLetter(id,title,content,sender_id,receiver_id,letterStatus,letterType)
values ('3','aaaa1','aaaaaaaaaaaaaa3','100048','100047',0,1);
insert into T_ec_insideLetter(id,title,content,sender_id,receiver_id,letterStatus,letterType)
values ('4','aaaa1','aaaaaaaaaaaaaa4','100048','100047',2,1);
insert into T_ec_insideLetter(id,title,content,sender_id,receiver_id,letterStatus,letterType)
values ('5','aaaa5','aaaaaaaaaaaaaa5','100048','100049',2,1);
insert into T_ec_insideLetter(id,title,content,sender_id,receiver_id,letterStatus,letterType)
values ('6','aaaa6','aaaaaaaaaaaaaa6','100049','100048',2,1);
insert into T_ec_insideLetter(id,title,content,sender_id,receiver_id,letterStatus,letterType)
values ('7','aaaa7','aaaaaaaaaaaaaa7','100047','100049',2,1);
insert into T_ec_insideLetter(id,title,content,sender_id,receiver_id,letterStatus,letterType)
values ('8','aaaa8','aaaaaaaaaaaaaa8','100047','100049',2,1);
insert into T_ec_insideLetter(id,title,content,sender_id,receiver_id,letterStatus,letterType)
values ('9','aaaa9','aaaaaaaaaaaaaa9','100049','100047',2,1);