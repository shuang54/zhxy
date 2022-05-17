package ltd.foogeoo.zhxy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ltd.foogeoo.zhxy.mapper.AdminMapper;
import ltd.foogeoo.zhxy.pojo.Admin;
import ltd.foogeoo.zhxy.service.AdminService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author LISHU
 * admin服务实现类
 */
@Service("adminServiceImpl")
@Transactional
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
}
