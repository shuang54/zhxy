package ltd.foogeoo.zhxy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import ltd.foogeoo.zhxy.pojo.Admin;
import ltd.foogeoo.zhxy.pojo.LoginForm;
import org.springframework.stereotype.Repository;

/**
 * @author LISHU
 */
public interface AdminService  extends IService<Admin> {
    Admin login(LoginForm loginForm);

    Admin getAdminById(Long userId);
}
