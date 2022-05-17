package ltd.foogeoo.zhxy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ltd.foogeoo.zhxy.mapper.ClazzMapper;
import ltd.foogeoo.zhxy.pojo.Clazz;
import ltd.foogeoo.zhxy.service.ClazzService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("clazzServiceImpl")
@Transactional
public class ClazzServiceImpl extends ServiceImpl<ClazzMapper, Clazz> implements ClazzService {
}
