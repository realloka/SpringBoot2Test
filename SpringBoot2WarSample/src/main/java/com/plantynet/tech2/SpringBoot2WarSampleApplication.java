package com.plantynet.tech2;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan(basePackages = "com.plantynet.tech2.dao")
@EnableTransactionManagement // 트랜잭션 매니저 사용
@EnableAsync // Async 사용
@EnableAspectJAutoProxy // <aop:aspectj-autoproxy/>
public class SpringBoot2WarSampleApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(SpringBoot2WarSampleApplication.class, args);
        
        // 공개키 방식(RSA) -> default encryptor
        //# a)개인키 생성 
        //# openssl genpkey -algorithm RSA -out private_key.pem -pkeyopt rsa_keygen_bits:2048
        //# b)공개키 생성
        //# openssl rsa -pubout -in private_key.pem -out public_key.pem
        /*
        SimpleAsymmetricConfig config = new SimpleAsymmetricConfig();
        config.setKeyFormat(KeyFormat.PEM);
        config.setPublicKey("-----BEGIN PUBLIC KEY-----\n" +
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAw7JMkrAbbulTWJpyrNnu\n" + 
                "vHqjMFI3mhyNV64agP5KKfRyTSOS7myDIhG1cdxmLkzxB8ACHedvIOQ1SG2RWFjF\n" + 
                "Fk6IwUITi4MTZJUuHnEUqTOH3N7gODnf3tMWvoMAfrMJvgmYL81scJFrDcRV8Se7\n" + 
                "NFK8byO06QczGvZAYu6FCopeTGTZNyxEOL7BjXn9HkeGsv5y8RH2LoH55d4kIbwx\n" + 
                "u8W75xHV1qP8Zasxet9oKZ16nAnCMvanimekYjNpn1vbqk6p/dNXanoDRUv9E9La\n" + 
                "X9zVzh01NNhwtAq/KLxXTGbcmYXRLxJMyXwCXrDbunlhhMWmlWpXUaO7x9W3F2lp\n" + 
                "eQIDAQAB\n" +
                "-----END PUBLIC KEY-----\n");
        StringEncryptor encryptor = new SimpleAsymmetricStringEncryptor(config);
        String message = "wineus";
        String encrypted = encryptor.encrypt(message);
        System.out.println("공개키에 의한 암호화");
        System.out.printf("enc = " + encrypted);
        */
        
        System.out.println("접속 계정 : tech_sup2 / tech_sup2");
    }
}
