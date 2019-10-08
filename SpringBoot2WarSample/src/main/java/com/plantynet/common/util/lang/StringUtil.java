package com.plantynet.common.util.lang;

import java.util.regex.Pattern;

public final class StringUtil
{
    
    private StringUtil()
    {
        throw new AssertionError();
    }
    
    /**
     * 문자열의 Empty or Null 체크
     * @param str
     * @return
     */
    public static boolean isEmptyOrNull(String str)
    {
        //return (str == null || str.trim().equals(""));
        //수정
        return (str == null || str.length() == 0);
    }
    
    /**
     * 비밀번호 규칙 체크 StrUtil.isValidPassword(userPwd, "^(?=.*[a-zA-Z])(?=.*[-_!@#$%^&*+=])(?=.*[0-9]).{8,16}$", 8, 16)
     * 정규식, 최소/최대 길이, 같은 문자 3번 이상, 연속된 문자열
     * @param password 비밀번호 입력값
     * @param regExpStr 정규식
     * @param minLength 최소자리수
     * @param maxLength 최대자리수
     * @return
     */
    public static boolean isValidPassword(String password, String regExpStr, int minLength, int maxLength)
    {
        boolean rtnValue = false;
        
        //입력값 확인
        if(password == null || password.length() == 0)
        {
            System.err.println("패스워드 Valid 실패 - 입력값 누락");
            return false;
        }
        
        //앞뒤 공백 제거
        password = password.trim();
        
        //자리수 체크
        if(password.length() > maxLength || password.length() < minLength)
        {
            System.err.println("패스워드 Valid 실패 - 길이");
            return false;
        }
        
        //정규식 체크
        if(regExpStr != null && regExpStr.length() != 0)
        {
            if(Pattern.matches(regExpStr, password) == false)
            {
                System.err.println("패스워드 Valid 실패 - 영문 대/소문자, 숫자, 특수문자(-_!@#$%^&*+=) 조합으로 8~16자");
                return false;
            }
        }
        
        char[] ch = password.toCharArray();
        //같은 문자 3번 이상 사용 금지(역참조...  그룹으로 처리해야 함 => 특수문자 중복은 체크 안 함)
        //정규식 역참조... 자바에서 어떻게 쓰는지 모르겠음...
        for(int i=0; i<ch.length - 2; i++)
        {
            if( (int)ch[i] == (int)ch[i+1] && (int)ch[i] == (int)ch[i+2] ) 
            {
                System.err.println("패스워드 Valid 실패 - 같은 문자 3번 이상 사용");
                return false;
            }
        }
        
        //연속성 체크
        int incCount = 0;
        int decCount = 0;
        
        for(int i=0; i<ch.length - 1; i++)
        {
            if( (int)ch[i+1] - (int)ch[i] == 1 ) 
            {
                incCount++;
            }
            else 
            {
                incCount = 0;
            }
            
            if( (int)ch[i+1] - (int)ch[i] == -1 ) 
            {
                decCount++;
            }
            else 
            {
                decCount = 0;
            }
            
            if(incCount > 1 || decCount > 1 ) {
                System.err.println("패스워드 Valid 실패 - 연속된 문자열(증가, 또는 감소)");
                return false;
            }
        }
        
        rtnValue = true;
        
        return rtnValue;
    }
    
    /**
     * 랜덤 문자열 만들기
     * @param len
     * @return
     */
    public static String makeRandomStr(int len)
    {
        char[] charArray = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
                'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u',
                'v', 'w', 'x', 'y', 'x', 'A', 'B', 'C', 'D', 'E', 'F', 'G',
                'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
                'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4',
                '5', '6', '7', '8', '9' };
        
        StringBuffer sb = new StringBuffer();
        
        int i = 0;
        while (i < len)
        {
            sb.append(charArray[(int) (Math.random() * charArray.length)]);
            i++;
        }
        
        return sb.toString();
    }
    
    /**
     * 개인정보 보호 마스킹 처리
     * @param str 마스킹 처리할 원 문자열
     * @param type - name / phone / email / id (default: id)
     * @return
     */
    public static String strMasking(String str, String type) 
    {
        String returnStr = "";
        if (isEmptyOrNull(str)) return returnStr; 
        
        if(isEmptyOrNull(type)) type = "";
        else type = type.toLowerCase();
        
        int strLength = str.length();
        
        if("name".equals(type))
        {
            //2글자 이하인 경우는 그대로 출력, 3글자 이상인 경우는 가운데 마스킹
            for (int i = 0; i < strLength; i++)
            {
                if (i > 0 && i < strLength - 1)
                {
                    returnStr += "*";
                }
                else
                {
                    returnStr += str.substring(i, i + 1);
                }
            }
        }
        else if("phone".equals(type))
        {
            String[] phoneArray = str.split("-", 3);
            
            if(phoneArray.length == 3)
            {
                returnStr = phoneArray[0] + "-";
                for(int i = 0; i < phoneArray[1].length(); i++)
                {
                    returnStr += "*";
                }
                returnStr += "-" + phoneArray[2];
            }
            else
            {
                if(str.startsWith("02"))
                {//02xxxyyyy,02xxxxyyyy 
                    returnStr = str.substring(0, 2);
                    
                    for (int i = 2; i < strLength; i++)
                    {
                        if (i < strLength - 4)
                        {
                            returnStr += "*";
                        }
                        else
                        {
                            returnStr += str.substring(i, i + 1);
                        }
                    }
                }
                else if(strLength > 3)
                {//010xxxyyyy, 010xxxxyyyy
                    returnStr = str.substring(0, 3);
                    
                    for (int i = 3; i < strLength; i++)
                    {
                        if (i < strLength - 4)
                        {
                            returnStr += "*";
                        }
                        else
                        {
                            returnStr += str.substring(i, i + 1);
                        }
                    }
                }
                else
                {
                    returnStr = str;
                }
            }
        }
        else if("email".equals(type))
        {
            String[] mailPart = str.split("@", 2);
            
            returnStr = strMasking(mailPart[0], "id");
            if(mailPart.length == 2)
                returnStr = strMasking(mailPart[0], "id") + "@" + mailPart[1];
        }
        else if("id".equals(type))
        {
            //2글자 이하인 경우는 그대로 출력, 3글자 이상인 경우는 뒤 2개만 마스킹
            //3글자 이하인 경우는 그대로 출력, 4글자 이상인 경우는 뒤에서부터 세번째부터 2글자 마스킹
            for (int i = 0; i < strLength; i++)
            {
                //if (strLength > 2 && i > (strLength - 3) && i < strLength)
                if (strLength > 3 && i > (strLength - 4) && i < strLength - 1)
                {
                    returnStr += "*";
                }
                else
                {
                    returnStr += str.substring(i, i + 1);
                }
            }
        }
        
        return returnStr;
    }
    
    /**
     * 01012345678 -> 010-1234-5678로 변경
     * @param phoneNo
     * @return
     */
    public static String changePhoneFormat(String phoneNo)
    {
        if(phoneNo.startsWith("02"))
        {
            if(phoneNo.length() == 10)
            {
                return phoneNo.substring(0, 2) + "-" + phoneNo.substring(2, 6) +"-" + phoneNo.substring(6);
            }
            else if(phoneNo.length() == 9)
            {
                return phoneNo.substring(0, 2) + "-" + phoneNo.substring(2, 5) +"-" + phoneNo.substring(5);
            }
            else
            {
                return phoneNo;
            }
        }
        else
        {
            if(phoneNo.length() == 11)
            {
                return phoneNo.substring(0, 3) + "-" + phoneNo.substring(3, 7) +"-" + phoneNo.substring(7);
            }
            else if(phoneNo.length() == 10)
            {
                return phoneNo.substring(0, 3) + "-" + phoneNo.substring(3, 6) +"-" + phoneNo.substring(6);
            }
            else 
            {
                return phoneNo;
            }
        }
    }
    
    public static String test(String var)
    {
    	return var + " response";
    }
}