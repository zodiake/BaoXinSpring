package com.baosight.scc.ec.utils.RedisConstant;

/**
 * Created by Administrator on 2014/10/15.
 */
public class ConstantKey {
    public static final String ANNOY = "annoy";
    public static final String CART = "ec:cart:";
    public static final String REDIS_ITEM = "ec:item:";
    public static final String REDIS_PRICE = "ec:price:";
    public static final String REDIS_SAMPLE = "ec:sample:";

    public static class ItemKey{
        public static final String id = "id";
        public static final String cover = "cover";
        public static final String providerId = "providerId";
        public static final String providerName = "providerName";
        public static final String name = "name";
        public static final String state = "state";
        public static final String url = "url";
    }
}
