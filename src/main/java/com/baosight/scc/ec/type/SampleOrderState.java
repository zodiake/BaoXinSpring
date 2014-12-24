package com.baosight.scc.ec.type;

/**
 * Created by ThinkPad on 2014/5/8.
 */
public enum SampleOrderState {
    WAIT_GOODS_SEND,  //等待卖家发货
    BUYER_CANCEL,   //买家取消交易
    SELLER_CANCEL,  //卖家取消交易
    GOODS_DELIVER,  //已发货
    GOODS_RECEIVE  //已确认收货
}
