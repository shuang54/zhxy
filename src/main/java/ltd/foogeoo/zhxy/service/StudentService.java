package ltd.foogeoo.zhxy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import ltd.foogeoo.zhxy.pojo.LoginForm;
import ltd.foogeoo.zhxy.pojo.Student;

public interface StudentService extends IService<Student> {
    Student login(LoginForm loginForm);

    Student getStudentById(Long userId);
}
