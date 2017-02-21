package com.czyy.controller;

import javax.servlet.http.HttpSession;
import com.czyy.WebSecurityConfig;
import com.czyy.model.User;
import com.czyy.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by 都航本地 on 2017/2/18.
 */
@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository ;
    @GetMapping("/loginGet")
    @ResponseBody
    public String loginPost(@RequestParam("id") String account,@RequestParam("password")  String password, HttpSession session) {
        String A = "" ;
        if (!"123456".equals(password) || !"admin".equals(account)) {
            A = "密码或账户错误" ;
            return A;
        }else{
            session.setAttribute(WebSecurityConfig.SESSION_KEY,account);
            A = "true" ;
            return A;
        }
        // 设置session

    }

    @GetMapping("/tijiao")
    @ResponseBody
    public String baoming(@RequestParam("name") String name, @RequestParam("studentid")
            String studentid, @RequestParam("college") String college, @RequestParam("classes") String classes ,@RequestParam("phone") String phone ) {
        User user = new User();
        String A = "true";
        List<User> userList= userRepository.findByName(name);
        List<User> userList2= userRepository.findByPhone(phone);
        if((userList != null && userList.size()>0) ||(userList2 != null && userList2.size()>0)){
            A ="false" ;
        }else if(name==""||phone==""||studentid==""||classes==""){
            A="kongbai" ;
        }else{
            user.setName(name);
            user.setPhone(phone);
            user.setNumber(studentid);
            user.setClasses(classes);
            user.setCollege(college);
            userRepository.save(user) ;
            A="true" ;
        }
        return A;

    }



    @ResponseBody
    @RequestMapping(value = "/look", method = RequestMethod.GET)
    public List<User> read(){
        return userRepository.findAll();
    }

    @ResponseBody
    @RequestMapping(value = "/redac", method = RequestMethod.GET)
    public List<User> readC(@RequestParam("college") String college){
        return userRepository.findByCollege(college);
    }
    @RequestMapping("/")
    public String login(){
        return "index" ;
    }

    @RequestMapping("/ht")
    public String ht(){
        return "admin" ;
    }

    @RequestMapping("/login")
    public String loginA(){
        return "login" ;
    }



}
