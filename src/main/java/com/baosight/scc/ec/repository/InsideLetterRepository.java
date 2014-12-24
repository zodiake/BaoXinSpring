package com.baosight.scc.ec.repository;

import com.baosight.scc.ec.model.EcUser;
import com.baosight.scc.ec.model.InsideLetter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by sam on 2014/6/27.
 */
public interface InsideLetterRepository extends PagingAndSortingRepository<InsideLetter,String> {

    Page<InsideLetter> findByReceiveUserAndLetterTypeAndLetterStatusNot(EcUser receiveUser,Integer letterType,Integer letterStatus,Pageable pageable);

    Page<InsideLetter> findBySendUserAndLetterTypeAndLetterStatusNot(EcUser sendUser,Integer letterType,Integer letterStatus,Pageable pageable);

    int countByReceiveUserAndLetterStatus(EcUser user,Integer status);

}
