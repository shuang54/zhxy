package ltd.foogeoo.zhxy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ltd.foogeoo.zhxy.mapper.StudentMapper;
import ltd.foogeoo.zhxy.pojo.Student;
import ltd.foogeoo.zhxy.service.StudentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("studentServiceImpl")
@Transactional
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
}
