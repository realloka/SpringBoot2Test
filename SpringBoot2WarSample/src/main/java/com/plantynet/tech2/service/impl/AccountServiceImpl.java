package com.plantynet.tech2.service.impl;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.plantynet.tech2.dao.AccountDao;
import com.plantynet.tech2.vo.AccountVo;

@Service
public class AccountServiceImpl implements UserDetailsService
{
    //@Autowired -> 생성자 주입
    private AccountDao accountDao;
    
    //@Autowired -> spring 4.3+ 생성자 하나인 경우 자동으로 설정됨
    public AccountServiceImpl(AccountDao accountDao)
    {
        this.accountDao = accountDao;
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        //아래 페이지를 이용해서 추가 정보를 저장하는 방법도 있다...
        //https://4urdev.tistory.com/54?category=761752
        
        AccountVo accountVo = accountDao.selectStdUserLogin(username);
        if(accountVo == null) 
        {
            throw new UsernameNotFoundException(username);
        }
        
        return new User(accountVo.getLoginId(), accountVo.getLoginPassword(), authorites());
    }
    
    //User 객체의세번째인자USER라는ROLE을가짂사용자이다라고설정하는부분(권한을 다양하게 줄 수 있다...)
    private Collection<? extends GrantedAuthority> authorites()
    {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
    }
}
