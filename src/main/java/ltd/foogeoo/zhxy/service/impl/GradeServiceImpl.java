package ltd.foogeoo.zhxy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ltd.foogeoo.zhxy.mapper.GradeMapper;
import ltd.foogeoo.zhxy.pojo.Grade;
import ltd.foogeoo.zhxy.service.GradeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("gradeServiceImpl")
@Transactional
public class GradeServiceImpl extends ServiceImpl<GradeMapper, Grade> implements GradeService {
}
