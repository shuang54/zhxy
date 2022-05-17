package ltd.foogeoo.zhxy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ltd.foogeoo.zhxy.mapper.TeacherMapper;
import ltd.foogeoo.zhxy.pojo.Teacher;
import ltd.foogeoo.zhxy.service.TeacherService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("teacherServiceImpl")
@Transactional
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {
}
