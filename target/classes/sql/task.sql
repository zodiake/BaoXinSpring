-- 初始化维度数据(T_ec_dimension_rate)
insert into T_ec_dimension_rate(user_id,attitude,deliverySpeed,satisfied,createdTime)
select id,0 as attitude,0 as deliverySpeed,0 as satisfied,current timestamp as createdTime
from T_ec_EcUser a where not exists(select id from T_ec_dimension_rate b where a.id=b.user_id)

-- 初始化综合评分数据(T_ec_Composite_Score)
insert into T_ec_Composite_Score(user_id,score,createdTime)
select id,0 as score,current timestamp as createdTime from T_ec_EcUser a where not exists(
select id from T_ec_Composite_Score b where a.id=b.user_id)

-- 初始化买家累计信用(T_ec_sellerCredit)
insert into T_ec_sellerCredit(createdTime,user_id,type,weekCount,oneMonthCount,sixMonthCount,sixMonthBeforeCount,total)
select current timestamp as createdTime, a.user_id,a.type,sum(a.weekCount) as weekCount,sum(a.oneMonthCount) as oneMonthCount,sum(a.sixMonthCount) as sixMonthCount,
sum(a.sixMonthBeforeCount) as sixMonthBeforeCount,sum(a.total) as total
from (
select current timestamp as createdTime,id as user_id,'好评' as type,0 as weekCount,0 as oneMonthCount,0 as sixMonthCount,0 as sixMonthBeforeCount,0 as total
from T_ec_EcUser a where not exists(select id from T_ec_sellerCredit b where a.id = b.user_id)
union
select current timestamp as createdTime,id as user_id,'中评' as type,0 as weekCount,0 as oneMonthCount,0 as sixMonthCount,0 as sixMonthBeforeCount,0 as total
from T_ec_EcUser a where not exists(select id from T_ec_sellerCredit b where a.id = b.user_id)
union
select current timestamp as createdTime,id as user_id,'差评' as type,0 as weekCount,0 as oneMonthCount,0 as sixMonthCount,0 as sixMonthBeforeCount,0 as total
from T_ec_EcUser a where not exists(select id from T_ec_sellerCredit b where a.id = b.user_id)
) a group by type,user_id

/*
*更新维度(T_ec_dimension_rate)表数据
*/
-- 删除已经存在的维度数据(T_ec_dimension_rate)
delete from T_ec_dimension_rate a where exists (select * from (select d.createdBy as user_id,d.id from T_ec_Comment c join t_ec_item d on c.item_id=d.id where c.defaultShow=1) b where a.user_id = b.user_id )

-- 插入最新维度数据(T_ec_dimension_rate)
insert into T_ec_dimension_rate(user_id,attitude,deliverySpeed,satisfied,createdTime)
select createdBy as user_id, avg(attitude) as attitude,avg(deliverySpeed) as deliverySpeed,avg(satisfied) as satisfied,current timestamp as createTimed from T_ec_Comment c inner join T_ec_Item i on c.item_id = i.id
where timestampdiff(16,c.createdtime-current timestamp)<=180 and  c.defaultShow=1
group by i.createdBy

/*
*更新综合评分表(T_ec_Composite_Score)数据
*/
-- 删除已经存在的综合评分数据(T_ec_Composite_Score)
delete from T_ec_Composite_Score a where exists(select id from T_ec_dimension_rate b where a.user_id=b.user_id)

-- 插入最新综合评分数据(T_ec_Composite_Score)
insert into T_ec_Composite_Score(user_id,score,createdTime)
select user_id,sum(a.attitude+a.deliverySpeed+a.satisfied)/3 as score,current timestamp as createdTime
from T_ec_dimension_rate a group by a.user_id

/*
*更新买家累计信用表(T_ec_sellerCredit)数据
*/
-- 删除已经存在的买家累计信用(T_ec_sellerCredit)
delete from T_ec_sellerCredit a where exists (select * from (select d.createdBy as user_id,c.id from T_ec_comment c join t_ec_item d on c.item_id=d.id where c.defaultShow=1) b where a.user_id=b.user_id)

-- 插入最新买家累计信用(T_ec_sellerCredit)
insert into T_ec_sellerCredit(createdTime,user_id,type,weekCount,oneMonthCount,sixMonthCount,sixMonthBeforeCount,total)
select current timestamp as createdTime, a.createdBy,a.type,sum(a.weekCount) as weekCount,sum(a.oneMonthCount) as oneMonthCount,sum(a.sixMonthCount) as sixMonthCount,
sum(a.sixMonthBeforeCount) as sixMonthBeforeCount,sum(a.total) as total
 from (
select i.createdBy,c.type as type,count(c.type) as weekCount,0 as oneMonthCount,0 as sixMonthCount,0 as sixMonthBeforeCount,0 as total
from T_ec_comment c inner join T_ec_Item i on c.item_id=i.id
where 1=1 and timestampdiff(16,c.createdtime-current timestamp)<=7  and c.defaultShow=1
group by c.type,i.createdBy
union
select i.createdBy,c.type as type,0 as weekCount,count(c.type) as oneMonthCount,0 as sixMonthCount,0 as sixMonthBeforeCount,0 as total
from T_ec_comment c inner join T_ec_Item i on c.item_id=i.id
where 1=1 and timestampdiff(16,c.createdtime-current timestamp)<=30 and c.defaultShow=1
group by c.type,i.createdBy
union
select i.createdBy,c.type as type,0 as weekCount,0 as oneMonthCount,count(c.type) as sixMonthCount,0 as sixMonthBeforeCount,0 as total
from T_ec_comment c inner join T_ec_Item i on c.item_id=i.id
where 1=1 and timestampdiff(16,c.createdtime-current timestamp)<=180 and c.defaultShow=1
group by c.type,i.createdBy
union
select i.createdBy,c.type as type,0 as weekCount,0 as oneMonthCount,0 as sixMonthCount,count(c.type) as sixMonthBeforeCount,0 as total
from T_ec_comment c inner join T_ec_Item i on c.item_id=i.id
where 1=1 and timestampdiff(16,c.createdtime-current timestamp)<=360 and timestampdiff(16,c.createdtime-current timestamp)>180 and c.defaultShow=1
group by c.type,i.createdBy
union
select i.createdBy,c.type as type,0 as weekCount,0 as oneMonthCount,0 as sixMonthCount,0 as sixMonthBeforeCount,count(c.type) as total
from T_ec_comment c inner join T_ec_Item i on c.item_id=i.id
where 1=1 and timestampdiff(16,c.createdtime-current timestamp)<=360 and c.defaultShow=1
group by c.type,i.createdBy
 ) a group by type,createdBy
