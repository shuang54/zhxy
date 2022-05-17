package ltd.foogeoo.zhxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import ltd.foogeoo.zhxy.pojo.Grade;
import ltd.foogeoo.zhxy.service.GradeService;
import ltd.foogeoo.zhxy.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Api(tags = "年级控制器")
@RestController
@RequestMapping("/sms/gradeController")
public class GradeController {
    @Autowired
    private GradeService gradeService;

    @ApiOperation("根据id列表删除年级")
    @DeleteMapping("/deleteGrade")
    public Result deleteGrade( @ApiParam("id列表，JSON格式") @RequestBody List<Integer> ids){
        gradeService.removeByIds(ids);
        return Result.ok();
    }
    /**
     * 新建或更新年级数据
     * @param grade 年级信息
     * @return 返回成功的结果
     */
    @ApiOperation("根据id是否存在，更新或删除年级数据")
    @PostMapping("/saveOrUpdateGrade")
    public Result saveOrUpdateGrade(
        @ApiParam("年级数据") @RequestBody Grade grade
    ){
        //调用服务层保存数据
        gradeService.saveOrUpdate(grade);
        return Result.ok();
    }

    /**
     * 查询分页
     * @param pageNo 分页页码
     * @param pageSize 分页大小
     * @param gradeName 分页条件
     * @return 成功的Result结果
     */
    @ApiOperation("查询分页")
    @GetMapping("/getGrades/{pageNo}/{pageSize}")
    public Result getGrades(
         @ApiParam("分页页码")   @PathVariable("pageNo") Integer pageNo,
         @ApiParam("分页大小")   @PathVariable("pageSize") Integer pageSize,
         @ApiParam("分页条件")   String gradeName
   ){
        //分页，带条件查询
        Page<Grade> page = new Page<>(pageNo,pageSize);
        //调用服务层
        IPage<Grade> pageRs = gradeService.getGradeByOpr(page,gradeName);
        //返回
        return Result.ok(pageRs);
    }
}
