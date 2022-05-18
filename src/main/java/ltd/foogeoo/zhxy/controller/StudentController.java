package ltd.foogeoo.zhxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import ltd.foogeoo.zhxy.pojo.Student;
import ltd.foogeoo.zhxy.service.StudentService;
import ltd.foogeoo.zhxy.util.MD5;
import ltd.foogeoo.zhxy.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "学生管理器")
@RestController
@RequestMapping("/sms/studentController")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @ApiOperation("删除学生信息")
    @DeleteMapping("/delStudentById")
    public Result delStudnetById(
            @ApiParam("学生ID列表") @RequestBody List<Integer> ids
    ){
        studentService.removeByIds(ids);
        return Result.ok();
    }

    @ApiOperation("添加或修改学生")
    @PostMapping("/addOrUpdateStudent")
    public Result addOrUpdateStudent(
           @ApiParam("JSON格式的学生类") @RequestBody Student student
    ){
        Integer id = student.getId();
//        添加用户时将密码加密
        if(null == id || 0 == id){
            student.setPassword(MD5.encrypt(student.getPassword()));
        }
        studentService.saveOrUpdate(student);
        return Result.ok();

    }

    @ApiOperation("分页带条件查询学生信息")
    @GetMapping("/getStudentByOpr/{pageNo}/{pageSize}")
    public Result getStudentByOpr(
            @ApiParam("分页查询的页码") @PathVariable("pageNo") Integer pageNo,
            @ApiParam("分页查询的大小") @PathVariable("pageSize") Integer pageSize,
            @ApiParam("查询条件") Student student
    ){
        Page<Student> page = new Page<>(pageNo,pageSize);
        IPage<Student> iPage = studentService.getStudentByOpr(page,student);
        return Result.ok(iPage);
    }
}
