package com.example.mysecurity.aspect;

import com.example.mysecurity.entity.SardlineUserBehaviour;
import com.example.mysecurity.service.SardlineUserBehaviourService;
import com.example.mysecurity.service.SardlineUserService;
import jdk.nashorn.internal.ir.CallNode;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Aspect
@Component
public class LogAspect {
    @Autowired
    private SardlineUserBehaviourService sardlineUserBehaviourService;
    @Autowired
    private SardlineUserService sardlineUserService;

//    @Autowired
//    HttpServletRequest request;


    @Pointcut("execution(* com.example.mysecurity.controller..*(..))")
    public void controller(){}
    @Pointcut("execution(* com.example.mysecurity.controller.CommonController.getSelectIds())")
    public void skipController(){}

    @Before("controller()&&!skipController()")
    public void insertLog(){

        SardlineUserBehaviour sardlineUserBehaviour = new SardlineUserBehaviour();
        //获取request
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        //获取路径、提交类型
        String requestURI = request.getRequestURI();
        String method = request.getMethod();

        //获取Authentication--->principal--->username
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //获取username、userId
        String username = principal.getUsername();

        sardlineUserBehaviour.setUserId(sardlineUserService.queryByName(username).getUserId());
        sardlineUserBehaviour.setUserName(username);
        sardlineUserBehaviour.setUserUrl(requestURI);
        sardlineUserBehaviour.setUserApiType(method);
        sardlineUserBehaviour.setTime(new Date());
        String describe = sardlineUserBehaviour.getUserName()+":用户操作了:"+sardlineUserBehaviour.getUserUrl();
        sardlineUserBehaviour.setDescribe(describe);
        sardlineUserBehaviourService.insert(sardlineUserBehaviour);
//        List<SardlineUserBehaviour> list = sardlineUserBehaviourService.queryAllByLimit(0,50);
        /*for (SardlineUserBehaviour t:list
             ) {
            System.out.println(t.getUserId()+";"+t.getUserName()+";"+t.getUserUrl()+";"+t.getUserApiType()+";"+t.getTime());
        }*/
    }

}
