package ltd.foogeoo.zhxy.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.jsonwebtoken.Jwt;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import ltd.foogeoo.zhxy.pojo.Admin;
import ltd.foogeoo.zhxy.pojo.LoginForm;
import ltd.foogeoo.zhxy.pojo.Student;
import ltd.foogeoo.zhxy.pojo.Teacher;
import ltd.foogeoo.zhxy.service.AdminService;
import ltd.foogeoo.zhxy.service.StudentService;
import ltd.foogeoo.zhxy.service.TeacherService;
import ltd.foogeoo.zhxy.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author LISHU
 */
@Api(tags = "系统管理器")
@RestController
@RequestMapping("/sms/system")
public class SystemController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;
    @ApiOperation("修改密码")
    @PostMapping("/updatePwd/{oldPwd}/{newPwd}")
    public Result updatePwd(
            @ApiParam("请求头中的token") @RequestHeader("token") String token,
            @ApiParam("旧密码") @PathVariable("oldPwd") String oldPwd,
            @ApiParam("新密码") @PathVariable("newPwd") String newPwd
    ){
        boolean expiration = JwtHelper.isExpiration(token);
        if(expiration){
            return Result.fail().message("token失效，请重新登录后修改密码");
        }
        Long userId = JwtHelper.getUserId(token);
        Integer userType = JwtHelper.getUserType(token);
        oldPwd = MD5.encrypt(oldPwd);
        newPwd = MD5.encrypt(newPwd);
        switch (userType){
            case 1:
                QueryWrapper<Admin> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.eq("id",userId.intValue());
                queryWrapper1.eq("password",oldPwd);
                Admin admin = adminService.getOne(queryWrapper1);
                if(admin != null){
                    admin.setPassword(newPwd);
                    adminService.saveOrUpdate(admin);
                }else{
                    return Result.fail().message("原密码有误！");
                }
                break;
            case 2:
                QueryWrapper<Student> queryWrapper2 = new QueryWrapper<>();
                queryWrapper2.eq("id",userId.intValue());
                queryWrapper2.eq("password",oldPwd);
                Student student = studentService.getOne(queryWrapper2);
                if(student != null){
                    student.setPassword(newPwd);
                    studentService.saveOrUpdate(student);
                }else{
                    return Result.fail().message("原密码有误！");
                }
                break;
            case 3:
                QueryWrapper<Teacher> queryWrapper3 = new QueryWrapper<>();
                queryWrapper3.eq("id",userId.intValue());
                queryWrapper3.eq("password",oldPwd);
                Teacher teacher = teacherService.getOne(queryWrapper3);
                if(teacher != null){
                    teacher.setPassword(newPwd);
                    teacherService.saveOrUpdate(teacher);
                }else{
                    return Result.fail().message("原密码有误！");
                }
                break;
            default:
                break;
        }
        return Result.ok();
    }


    @ApiOperation("上传图片")
    @PostMapping("/headerImgUpload")
    public Result headerImgUpload(
            @ApiParam("头像文件") @RequestPart("multipartFile") MultipartFile multipartFile,
            HttpServletRequest request
            ){
        String uuid = UUID.randomUUID().toString().replace("-","").toLowerCase();
        String originalFilename = multipartFile.getOriginalFilename();
        int i = originalFilename.lastIndexOf(".");
        String newFileName = uuid.concat(originalFilename.substring(i));
        //保存文件
        String portraitPath = "E:/myJavaStudy/course/zhxy/target/classes/public/upload/".concat(newFileName);
        try {
            multipartFile.transferTo(new File(portraitPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //响应图片的路径
        String path = "upload/".concat(newFileName);
        return Result.ok(path);
    }

    @ApiOperation("通过token获取用户信息")
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
    @ApiOperation("获取验证码")
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
    @ApiOperation("登录")
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
