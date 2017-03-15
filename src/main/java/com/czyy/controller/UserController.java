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
    public String handleFileUpload(@RequestParam("file") MultipartFile file, Model model , HttpSession session) throws IOException {

        String filename = file.getOriginalFilename();  // 取得文件名
        filename = session.getAttribute(WebSecurityConfig.SESSION_KEY) + filename.substring(filename.lastIndexOf("."));
        String filepath = ROOT + "/" + filename;  //设置保存路径
        // 保存图片
        File f1 = new File(filepath);
        if (file.getSize() >= 1024000) {   //获得文件大小
            model.addAttribute("msg", "文件过大！");
            return "touxiang";
        }
        if (isImageFile(filename)) {
            if (f1.exists()) {
                f1.delete();
            }
            model.addAttribute("msg", "上传成功！");
            Files.copy(file.getInputStream(), Paths.get(ROOT, filename));
            return "touxiang";
        } else {
            model.addAttribute("msg", "上传的不是图像文件！");
            return "touxiang";
        }

    }

    private Boolean isImageFile(String fileName) {
        String[] img_type = new String[]{".jpg", ".jpeg", ".png", ".gif", ".bmp"};
        if (fileName == null) {
            return false;
        }
        fileName = fileName.toLowerCase();
        for (String type : img_type) {
            if (fileName.endsWith(type)) {
                return true;
            }
        }
        return false;
    }

    @GetMapping("/touxiang")
    public String sheZhiTouXiang(HttpSession session , Model model) {
        model.addAttribute("user",session.getAttribute(WebSecurityConfig.SESSION_KEY));
        return "touxiang";
    }


    @PostMapping("/loginGet")
    public String loginPost(@RequestParam("id") String account, @RequestParam("password") String password, Model model, HttpSession session) {
        if (loginCheck(account, password) == "Both wrong!") {
            model.addAttribute("msgall", "用户名和密码错误!!");
            return "login";
        } else if (loginCheck(account, password) == "id wrong!") {
            model.addAttribute("msgid", "用户名错误!!");
            return "login";
        } else if ((loginCheck(account, password) == "psw wrong!")) {
            model.addAttribute("msgpassword", "密码错误！！！");
            return "login";
        }
        if (loginCheck(account, password) == "true") {
            session.setAttribute(WebSecurityConfig.SESSION_KEY, account);
            System.out.println(session.getAttribute(WebSecurityConfig.SESSION_KEY));// 设置session
            return "redirect:/ht";
        }
        return "login";


    }

    // 校验登录 是否为 admin 123456
    private String loginCheck(String id, String password) {
        if (!"admin".equals(id) && !"123456".equals(password)) {
            return "Both wrong!";
        }
        if (!"admin".equals(id)) {
            return "id wrong!";
        }
        if (!"123456".equals(password)) {
            return "psw wrong!";
        }
        return "true";
    }




    @GetMapping("/tijiao")
    @ResponseBody
    public String baoming(@RequestParam("name") String name, @RequestParam("studentid")
            String studentid, @RequestParam("college") String college, @RequestParam("classes") String classes, @RequestParam("phone") String phone) {
        String A = "";
        if (baomingCheck(name, studentid, college, classes, phone) == "false") {
            A = "false";
        } else if (baomingCheck(name, studentid, college, classes, phone) == "kongbai") {
            A = "kongbai";
        } else if (baomingCheck(name, studentid, college, classes, phone) == "kongbai") {
            A = "kongbai";
        } else if (baomingCheck(name, studentid, college, classes, phone) == "true") {
            A = "true";
        }
        return A;


    }

    // 校验提交的报名表单
    private String baomingCheck(String name, String studentid, String college, String classes, String phone) {
        User user = new User();
        List<User> userList = userRepository.findByName(name);
        List<User> userList2 = userRepository.findByPhone(phone);
        if ((userList != null && userList.size() > 0) || (userList2 != null && userList2.size() > 0)) {
            return "false";
        } else if (name == "" || phone == "" || studentid == "" || classes == "") {
            return "kongbai";
        } else {
            user.setName(name);
            user.setPhone(phone);
            user.setNumber(studentid);
            user.setClasses(classes);
            user.setCollege(college);
            userRepository.save(user);
            return "true";
        }
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
