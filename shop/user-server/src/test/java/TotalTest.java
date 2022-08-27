import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guo.UserMainApplication;
import com.guo.bean.dao.UserCollectDao;
import com.guo.bean.dao.UserDao;
import com.guo.bean.mapper.UserCollectMapper;
import com.guo.exception.TotalException;
import com.guo.service.UserService;
import com.guo.utils.DozerUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;

/**
 * @author guokaifeng
 * @createDate: 2022/5/6
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = UserMainApplication.class)
public class TotalTest {

    @Autowired
    UserService userService;

    @Autowired
    UserCollectMapper userCollectMapper;


    @Test
    public void haha() throws TotalException {
//        UserCollectDao userCollectDao = userCollectMapper.selectById("17759048528");
//        System.out.println(userCollectDao);
//        QueryWrapper<UserCollectDao> queryWrapper = new QueryWrapper<>();
//        queryWrapper.select("user_id", "my_index","oid");
//        queryWrapper.eq("user_id","17759048528");
//        List<UserCollectDao> userCollectDao1 = userCollectMapper.selectList(queryWrapper);
//       userCollectDao1.get(0);
    }

    @Test
    public void haha2() throws ClassNotFoundException {
//        Page page  =new Page(0,5);
//        QueryWrapper<UserCollectDao> queryWrapper = new QueryWrapper<>();
//        queryWrapper.select("user_id", "index","oid");
//        queryWrapper.eq("user_id","17759048528");
//        Page page1 = userCollectMapper.selectMapsPage(page, queryWrapper);
////        System.out.println(page1);
//        List records = page1.getRecords();
//        List<Object> parse = JSON.parseArray(JSON.toJSONString(records));

        


//        System.out.println(clazz);
    }

    @Test
    public void demo3(){
//        QueryWrapper<UserCollectDao> queryWrapper = new QueryWrapper<>();
//        Page<UserCollectDao> page = new Page(Long.parseLong("0"),Long.parseLong("5"));
//        queryWrapper.select("user_id","oid","my_index");
//        queryWrapper.eq("user_id","17759048528");
//        Page<UserCollectDao> userCollectDao1 = userCollectMapper.selectPage(page,queryWrapper);
//        for (UserCollectDao record : userCollectDao1.getRecords()) {
//            System.out.println(record);
//        }
    }

}
