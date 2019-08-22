package com.plantynet.common.util.encrypt;

import java.security.MessageDigest;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.plantynet.common.exception.ProcessException;

public final class EncryptUtil
{
    public static final String ENCRYPT_ALGORITHM = "AES";
    
    //public static final String ENCRYPT_KEY = "Plantynet";
    
    private EncryptUtil()
    {
        throw new AssertionError();
    }
    
    /**
     * 대칭키 암호화
     * @param text
     * @return
     * @throws Exception
     */
    public static String encryptAes(String text, String key)
    {
        String encrypted = null;
        
        try
        {
            SecretKeySpec ks = new SecretKeySpec(generateKey(key), ENCRYPT_ALGORITHM);
            Cipher cipher = Cipher.getInstance(ENCRYPT_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, ks);
            byte[] encryptedBytes = cipher.doFinal(text.getBytes("UTF-8"));
            
            Encoder encoder = Base64.getEncoder();
            encrypted = encoder.encodeToString(encryptedBytes);
        }
        catch (Exception e)
        {
            throw new ProcessException("", e);
        }
        
        return encrypted;
    }
    
    /**
     * 대칭키 복호화
     * @param text
     * @return
     * @throws Exception
     */
    public static String decryptAes(String text, String key)
    {
        String decrypted = null;
        
        try
        {
            SecretKeySpec ks = new SecretKeySpec(generateKey(key), ENCRYPT_ALGORITHM);
            Cipher cipher = Cipher.getInstance(ENCRYPT_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, ks);
            
            Decoder decoder = Base64.getDecoder();
            
            byte[] decryptedBytes = cipher.doFinal(decoder.decode(text));
            decrypted = new String(decryptedBytes, "UTF-8");
        }
        catch (Exception e)
        {
            throw new ProcessException("", e);
        }
        
        return decrypted;
    }
    
    /**
     * 대칭키 생성
     * @param key
     * @return
     * @throws Exception
     */
    private static byte[] generateKey(String key)
    {
        byte[] desKey = new byte[16];
        byte[] bkey = key.getBytes();
        
        if (bkey.length < desKey.length)
        {
            System.arraycopy(bkey, 0, desKey, 0, bkey.length);
            
            for (int i = bkey.length; i < desKey.length; i++)
            {
                desKey[i] = 0;
            }
        }
        else
        {
            System.arraycopy(bkey, 0, desKey, 0, desKey.length);
        }
        
        return desKey;
    }
    
    /**
     * 비대칭키 SHA 512 암호화 (복호화 되지 않음) : base64 encoding
     * @param text
     * @return
     * @throws Exception
     */
    public static String encryptSha512(String text)
    {
        String encrypted = null;
        
        try
        {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.reset();//update한 것을 원래대로 바꿔줌. 여기서는 없어도 영향 없음.
            md.update(text.getBytes("UTF-8"));
            
            byte[] digested = md.digest();
            
            Encoder encoder = Base64.getEncoder();
            encrypted = encoder.encodeToString(digested);
        }
        catch (Exception e)
        {
            throw new ProcessException("", e);
        }
        
        return encrypted;
    }
    
    
    /*public static void main(String[] args)
    {
        System.out.println(EncryptUtil.encryptAes("1111한글", "Plantynet"));
        System.out.println(decryptAes("x0XSQnPn1PYUzrDZzhdmhQ==", "Plantynet"));
        System.out.println(EncryptUtil.encryptSha512("1111"));
    }*/

    /**
     * SHA256 hash : hexa encoding
     * @param input
     * @return
     */
    /*public static String getSha256(String input)
    {
        String toReturn = null;
        try 
        {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.reset();
            digest.update(input.getBytes("UTF-8"));
            toReturn = String.format("%064x", new BigInteger(1, digest.digest()));//byte 길이에 따라 64(sha256 -> 32byte이므로 hexa 64글자)로 할지 결정해야 함. 64보다 큰 경우 앞의 0이 사라짐
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        
        return toReturn;
    }*/
}