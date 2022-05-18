package ltd.foogeoo.zhxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import ltd.foogeoo.zhxy.pojo.Clazz;
import ltd.foogeoo.zhxy.service.ClazzService;
import ltd.foogeoo.zhxy.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "班级管理器")
@RestController
@RequestMapping("/sms/clazzController")
public class ClazzController {
    @Autowired
    private ClazzService clazzService;

    @ApiOperation("获取所有班级")
    @GetMapping("/getClazzs")
    public Result getClazzs(){
        List<Clazz> clazzs = clazzService.getClazzs();
        return Result.ok(clazzs);

    }

    @ApiOperation("根据id列表删除班级")
    @DeleteMapping("/deleteClazz")
    public Result deleteClass(
            @ApiParam("id列表") @RequestBody List<Integer> ids
    ){
        clazzService.removeByIds(ids);
        return Result.ok();
    }


    @ApiOperation("添加或修改班级")
    @PostMapping("/saveOrUpdateClazz")
    public Result saveOrUpdateClazz(
           @ApiParam("JSON格式的班级信息") @RequestBody Clazz clazz
    ){
        clazzService.saveOrUpdate(clazz);
        return Result.ok();
    }

    @ApiOperation("分页带条件查询班级信息")
    @GetMapping("/getClazzsByOpr/{pageNo}/{pageSize}")
    public Result getClazzByOpr(
            @ApiParam("分页查询的页码") @PathVariable("pageNo") Integer pageNo,
            @ApiParam("分页查询的大小") @PathVariable("pageSize") Integer pageSize,
            @ApiParam("分页查询的条件") Clazz clazz
    ){
        Page<Clazz> page = new Page<>(pageNo,pageSize);
        IPage<Clazz> iPage = clazzService.getClazzsByOpr(page,clazz);
        return Result.ok(iPage);
    }

}
