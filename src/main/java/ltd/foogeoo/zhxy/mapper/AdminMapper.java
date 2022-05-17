package ltd.foogeoo.zhxy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ltd.foogeoo.zhxy.pojo.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author LISHU
 */
@Repository
public interface AdminMapper extends BaseMapper<Admin> {
}
