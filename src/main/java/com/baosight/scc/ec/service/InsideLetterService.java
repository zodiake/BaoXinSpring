package com.baosight.scc.ec.service;

import com.baosight.scc.ec.model.EcUser;
import com.baosight.scc.ec.model.InsideLetter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by sam on 2014/6/27.
 */
public interface InsideLetterService {

    Page<InsideLetter> findLettersByReceiver(EcUser receiveUser,Integer letterType,Integer letterStatus,Pageable pageable);

    Page<InsideLetter> findLettersBySender(EcUser sendUser,Integer letterType,Integer letterStatus,Pageable pageable);

    InsideLetter findLetterById(String id);

    InsideLetter save(InsideLetter insideLetter);

    void updateLetterStatus(String[] arr,Integer letterStatus);

    /*
    与该用户有关的站内信列表
     */
    Page<InsideLetter> findLettersAboutUser(EcUser user,Pageable pageable);

    /*
       用户交流信息详情列表
     */
    Page<InsideLetter> findLetterDetailsAboutUserTalks(EcUser user1,EcUser user2,Pageable pageable);

    /*
    统计用户未读站内信
     */
    public int countByReceiverUserAndLetterStatus(EcUser user,Integer status);

    /*
    修改站内信状态
     */
    public int updateInsideLetterStatusByReceiverAndSender(Integer status,EcUser receiver,EcUser sender);


    public int updateInsideLetterByReceiverOrSender(EcUser receiver,EcUser sender);

}
