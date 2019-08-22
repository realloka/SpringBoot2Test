package com.plantynet.tech2.vo;

import com.plantynet.common.base.vo.BaseObject;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AccountVo extends BaseObject
{
    private String userId = "";
    private String userNm = "";
    private String loginId = "";
    private String loginPassword = "";
}