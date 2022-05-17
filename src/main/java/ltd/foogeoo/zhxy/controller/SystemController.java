package ltd.foogeoo.zhxy.controller;

import io.jsonwebtoken.Jwt;
import ltd.foogeoo.zhxy.pojo.Admin;
import ltd.foogeoo.zhxy.pojo.LoginForm;
import ltd.foogeoo.zhxy.pojo.Student;
import ltd.foogeoo.zhxy.pojo.Teacher;
import ltd.foogeoo.zhxy.service.AdminService;
import ltd.foogeoo.zhxy.service.StudentService;
import ltd.foogeoo.zhxy.service.TeacherService;
import ltd.foogeoo.zhxy.util.CreateVerifiCodeImage;
import ltd.foogeoo.zhxy.util.JwtHelper;
import ltd.foogeoo.zhxy.util.Result;
import ltd.foogeoo.zhxy.util.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author LISHU
 */
@RestController
@RequestMapping("/sms/system")
public class SystemController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;

    @GetMapping("/getInfo")
    public Result getInfoByToken(@RequestHeader("token") String token){

        boolean expiraion = JwtHelper.isExpiration(token);
        if(expiraion){
            return Result.build(null, ResultCodeEnum.TOKEN_ERROR);
        }
        // 从token中解析出 用户ID 和 用户的类型
        Long userId = JwtHelper.getUserId(token);
        Integer userType = JwtHelper.getUserType(token);
        Map<String,Object> map = new LinkedHashMap<>();
        switch (userType){
            case 1:
                Admin admin = adminService.getAdminById(userId);
                map.put("userType",1);
                map.put("user",admin);
                break;
            case 2:
                Student student = studentService.getStudentById(userId);
                map.put("userType",2);
                map.put("user",student);
                break;
            case 3:
                Teacher teacher = teacherService.getTeacherById(userId);
                map.put("userType",3);
                map.put("user",teacher);
                break;
            default:
                break;
        }
        return Result.ok(map);
    }

    /**
     * 获取在验证码图片
     * @param request
     * @param response
     */
    @GetMapping("/getVerifiCodeImage")
    public void getVerifiCodeImage(HttpServletRequest request, HttpServletResponse response){
        //获取图片
        BufferedImage verifiCodeImage = CreateVerifiCodeImage.getVerifiCodeImage();
        //获取图片上的验证码
        String verifiCode = new String(CreateVerifiCodeImage.getVerifiCode());
        //将验证码文本放到session域中，为下一次验证做准备
        HttpSession session = request.getSession();
        session.setAttribute("verifiCode",verifiCode);
        //将验证码图片响应给浏览器
        try {
            ImageIO.write(verifiCodeImage,"JPEG",response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 用户登录
     * @param loginForm 登录用户信息
     * @param request
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody LoginForm loginForm,HttpServletRequest request){
        //验证码校验
        HttpSession session = request.getSession();
        String sessionVerifiCode = (String)session.getAttribute("verifiCode");
        String loginVerifiCode = loginForm.getVerifiCode();
        if("".equals(sessionVerifiCode) || null == sessionVerifiCode){
            return Result.fail().message("验证码失效，请刷新后重试");
        }
        if (!sessionVerifiCode.equalsIgnoreCase(loginVerifiCode)) {
            return Result.fail().message("验证码输入有误，请重试");
        }
        //从session域中移出现有验证码
        session.removeAttribute("verifiCode");
        //按用户类型进行校验
            //准备map对象存放响应的数据
        Map<String,Object> map = new LinkedHashMap<>();
       switch(loginForm.getUserType()){
           case 1:
               try {
                   Admin admin = adminService.login(loginForm);
                   if(null != admin){
                       // 将token保存到map中
                       map.put("token", JwtHelper.createToken(admin.getId().longValue(),1));
                   }else{
                       throw new RuntimeException("用户名或密码有误");
                   }
                   return Result.ok(map);
               } catch (RuntimeException e) {
                   e.printStackTrace();
                   return Result.fail().message(e.getMessage());
               }
           case 2:
               try {
                   Student student = studentService.login(loginForm);
                   if(null != student){
                       map.put("token", JwtHelper.createToken(student.getId().longValue(),2));
                   }else{
                       throw new RuntimeException("用户名或密码有误");
                   }
                   return Result.ok(map);
               } catch (RuntimeException e) {
                   e.printStackTrace();
                   return Result.fail().message(e.getMessage());
               }
           case 3:
               try {
                   Teacher teacher = teacherService.login(loginForm);
                   if(null != teacher){
                       map.put("token", JwtHelper.createToken(teacher.getId().longValue(),3));
                   }else{
                       throw new RuntimeException("用户名或密码有误");
                   }
                   return Result.ok(map);
               } catch (RuntimeException e) {
                   e.printStackTrace();
                   return Result.fail().message(e.getMessage());
               }
           default:
               break;
       }
       return Result.fail().message("查无此用户");
    }
}
