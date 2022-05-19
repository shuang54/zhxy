package ltd.foogeoo.zhxy.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import ltd.foogeoo.zhxy.pojo.Admin;
import ltd.foogeoo.zhxy.pojo.Student;
import ltd.foogeoo.zhxy.pojo.Teacher;
import ltd.foogeoo.zhxy.service.AdminService;
import ltd.foogeoo.zhxy.service.StudentService;
import ltd.foogeoo.zhxy.service.TeacherService;
import ltd.foogeoo.zhxy.util.JwtHelper;
import ltd.foogeoo.zhxy.util.MD5;
import ltd.foogeoo.zhxy.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "教师管理器")
@RestController
@RequestMapping("/sms/teacherController")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;
    @ApiOperation("获取分页信息")
    @GetMapping("/getTeachers/{pageNo}/{pageSize}")
    public Result getTeachers(
            @ApiParam("分页页码") @PathVariable("pageNo") Integer pageNo,
            @ApiParam("分页大小") @PathVariable("pageSize") Integer pageSize,
            @ApiParam("分页条件")Teacher teacher
            ){
        Page<Teacher> pageParam =  new Page<Teacher>(pageNo,pageSize);
        IPage<Teacher> iPage = teacherService.getTeacherByOpr(pageParam,teacher);
        return Result.ok(iPage);
    }

    @ApiOperation("添加或修改教师信息")
    @PostMapping("/saveOrUpdateTeacher")
    public Result saveOrUpdateTeacher(
            @ApiParam("JSNO格式的教师信息") @RequestBody Teacher teacher
    ){
        if (null == teacher.getId() || 0 == teacher.getId()){
            teacher.setPassword(MD5.encrypt(teacher.getPassword()));
        }
        teacherService.saveOrUpdate(teacher);
        return Result.ok();
    }

    @ApiOperation("通过ID列表删除教师")
    @DeleteMapping("/deleteTeacher")
    public Result deleteTeacher(
            @ApiParam("ID列表") @RequestBody List<Integer> ids
    ){
        teacherService.removeByIds(ids);
        return Result.ok();
    }


}
