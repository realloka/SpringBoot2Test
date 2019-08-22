package com.plantynet.tech2.dao;

import com.plantynet.tech2.vo.AccountVo;

public interface AccountDao
{
    /*인터페이스는 public abstract 생략됨*/
    /**
     * 로그인
     * @return
     */
    AccountVo selectStdUserLogin(String loginId);
}