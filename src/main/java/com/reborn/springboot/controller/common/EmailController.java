package com.reborn.springboot.controller.common;

import com.reborn.springboot.entity.Result;
import com.reborn.springboot.utils.RedisUtil;
import com.reborn.springboot.utils.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

@Controller
public class EmailController {

    @Value("${spring.mail.username}")
    private String sender;

    @Autowired
    private JavaMailSender javaMailSender;

    @RequestMapping("/sendVerifyCode")
    @ResponseBody
    public Result sendVerifyCode(@RequestParam(value = "email") String email){
        if (email == null){
            return ResultGenerator.getFailResult("邮箱不能为空");
        }
        String codeValue = getRandomCode();
        try {
            sendEmail(email,codeValue);
        }catch (MessagingException e){
            System.out.println("邮箱发送失败");
            e.printStackTrace();
            return ResultGenerator.getFailResult("验证码发送失败");
        }
        //存到redis中
        Jedis jedis = RedisUtil.getJedisConnection();
        jedis.setex("verify:"+email+":code",60,codeValue);
        jedis.close();
        return ResultGenerator.getSuccessResult("发送成功");
    }

    private void sendEmail(String email,String codeValue) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper msg = new MimeMessageHelper(mimeMessage,true);
        //发件人邮件
        msg.setFrom(sender);
        //收件人邮箱
        msg.setTo(email);
        //邮箱主题
        msg.setSubject("博客邮箱验证码");
        //邮箱内容
        msg.setText("<h2>亲爱的博客用户：</h2><h4>您的验证码是：<strong>"+codeValue+"</strong></h4>",true);
        //发送邮箱
        javaMailSender.send(mimeMessage);
    }

    private String getRandomCode() {
        int i ;
        String randomCode = "";
        for (int k=0;k<6;k++){
            i = (int) (Math.random()*10);
            randomCode = randomCode+i;
        }
        return randomCode;
    }
}
