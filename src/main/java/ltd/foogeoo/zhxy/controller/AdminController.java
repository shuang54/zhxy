package ltd.foogeoo.zhxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import ltd.foogeoo.zhxy.pojo.Admin;
import ltd.foogeoo.zhxy.pojo.Student;
import ltd.foogeoo.zhxy.service.AdminService;
import ltd.foogeoo.zhxy.util.MD5;
import ltd.foogeoo.zhxy.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "管理员控制器")
@RestController
@RequestMapping("/sms/adminController")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @ApiOperation("获取所有管理员信息")
    @GetMapping("/getAllAdmin/{pageNo}/{pageSize}")
    public Result getAllAdmin(
            @ApiParam("分页页码") @PathVariable("pageNo") Integer pageNo,
            @ApiParam("分页大小") @PathVariable("pageSize") Integer pageSize,
            @ApiParam("分页条件") String adminName
    ){
        Page<Admin>  pageParam = new Page<Admin> (pageNo,pageSize);
        IPage<Admin> iPage = adminService.getAdminsByOpr(pageParam,adminName);
        return Result.ok(iPage);
    }

    @ApiOperation("添加或修改管理员")
    @PostMapping("/saveOrUpdateAdmin")
    public Result saveOrUpdateAdmin(
            @ApiParam("JSON格式的管理员数据") @RequestBody Admin admin
            ){
        Integer id = admin.getId();
        if(null == id || 0 == id){
            admin.setPassword(MD5.encrypt(admin.getPassword()));
        }
        adminService.saveOrUpdate(admin);
        return Result.ok();
    }

    @ApiOperation("删除管理员")
    @DeleteMapping("/deleteAdmin")
    public Result deleteAdmin(
            @ApiParam("管理员ID列表") @RequestBody List<Integer> ids
            ){
        adminService.removeByIds(ids);
        return Result.ok();
    }
}
