package ltd.foogeoo.zhxy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import ltd.foogeoo.zhxy.pojo.LoginForm;
import ltd.foogeoo.zhxy.pojo.Teacher;

public interface TeacherService extends IService<Teacher> {
    Teacher login(LoginForm loginForm);

    Teacher getTeacherById(Long userId);
}
