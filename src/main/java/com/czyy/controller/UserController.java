package com.czyy.controller;

import javax.servlet.http.HttpSession;
import com.czyy.WebSecurityConfig;
import com.czyy.model.User;
import com.czyy.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by 都航本地 on 2017/2/18.
 */
@Controller
public class UserController {
    public static final String ROOT = "C:\\Users\\58365\\IdeaProjects\\abccccccc\\src\\data\\pic";

    private final ResourceLoader resourceLoader;

    @Autowired
    public UserController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Autowired
    private UserRepository userRepository ;

    @GetMapping(value = "/pic/{filename:.+}")
    @ResponseBody
    public ResponseEntity<?> getFile(@PathVariable String filename) {
        try {
            return ResponseEntity.ok(resourceLoader.getResource("file:" + Paths.get(ROOT, filename).toString()));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/ttttt")
    public String handleFileUpload(@RequestParam("file") MultipartFile file ,Model model) throws IOException {
        String filename ="6.jpg";
        String filepath = ROOT + "/" +filename;
        // 保存图片
        File f1 = new File(filepath);
        if (f1.exists()){
            f1.delete();
        }
        Files.copy(file.getInputStream(), Paths.get(ROOT, filename));
        model.addAttribute("msg","上传成功！");
        return "touxiang";
    }

    @GetMapping("/touxiang")
    public String sheZhiTouXiang(){
        return "touxiang";
    }



    @PostMapping("/loginGet")
    public String loginPost(@RequestParam("id") String account, @RequestParam("password")  String password, Model model, HttpSession session) {
        if (!"admin".equals(account)&&!"123456".equals(password)){
            model.addAttribute("msgall","用户名和密码错误!!");
            return "login";
        }
        if(!"admin".equals(account)){
            model.addAttribute("msgid","用户名错误!!");
            return "login";
        }else if (!"123456".equals(password)){
            model.addAttribute("msgpassword","密码错误！！！");
            return "login";
        }else{
            session.setAttribute(WebSecurityConfig.SESSION_KEY,account);
            return "redirect:/ht" ;


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
