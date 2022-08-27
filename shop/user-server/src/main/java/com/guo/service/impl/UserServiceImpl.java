package com.guo.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guo.bean.dao.UserCollectDao;
import com.guo.bean.dao.UserHistoryDao;
import com.guo.bean.mapper.UserCollectMapper;
import com.guo.bean.mapper.UserHistoryMapper;
import com.guo.bean.mapper.UserMapper;
import com.guo.exception.TotalException;
import com.guo.service.RedisService;
import com.guo.service.UserService;
import com.guo.utils.StateEnum;
import com.guo.bean.vo.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.guo.bean.dao.UserDao;
import com.guo.bean.mapper.UserMapper;
import com.guo.bean.vo.UserLoginVO;
import com.guo.bean.vo.UserRegisterVO;
import com.guo.utils.*;

import javax.servlet.ServletOutputStream;
import java.io.*;
import java.util.List;
import java.util.UUID;

/**
 * @author guokaifeng
 * @createDate: 2022/5/6
 **/
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    UserCollectMapper userCollectMapper;


    @Autowired
    UserMapper userMapper;


    @Autowired
    UserHistoryMapper userHistoryMapper;

    @Autowired
    RedisService redisService;

    @Autowired
    RedisUtils redisUtils;

    @Override
    public void putUserCollect(String userId, String id,String index) throws TotalException {
            UserCollectDao userCollectDao = new UserCollectDao(userId,id,index,UUID.randomUUID().toString());
            userCollectMapper.insert(userCollectDao);
    }

    @Override
    public void putUserHistory(String userId, String id,String index) throws TotalException {
        UserHistoryDao userHistoryDao = new UserHistoryDao(userId,id,index, UUID.randomUUID().toString());
        userHistoryMapper.insert(userHistoryDao);
    }

    @Override
    public List<UserCollectDao> getUserCollect(String userId,String begin,String num) {
        QueryWrapper<UserCollectDao> queryWrapper = new QueryWrapper<>();
        Page<UserCollectDao> page = new Page(Long.parseLong(begin),Long.parseLong(num));
        queryWrapper.select("user_id","oid","my_index");
        queryWrapper.eq("user_id",userId);
        Page<UserCollectDao> userCollectDao1 = userCollectMapper.selectPage(page,queryWrapper);
        return userCollectDao1.getRecords();
    }


    @Override
    public List<UserHistoryDao> getUserHistory(String userId, String begin, String num) {
        QueryWrapper<UserHistoryDao> queryWrapper = new QueryWrapper<>();
        Page<UserHistoryDao> page = new Page(Long.parseLong(begin),Long.parseLong(num));
        queryWrapper.select("user_id","oid","my_index");
        queryWrapper.eq("user_id",userId);
        Page<UserHistoryDao> userHistoryDaoPage = userHistoryMapper.selectPage(page,queryWrapper);
        return userHistoryDaoPage.getRecords();
    }


    @Override
    public ResponseResult userLogin(UserLoginVO userLoginVO) throws TotalException {
        QueryWrapper<UserDao> queryWrapper = new QueryWrapper<>();
        String phone = userLoginVO.getPhone();
        queryWrapper.select("password", "salt","permission");
        queryWrapper.eq("phone", phone);
        UserDao userDao = userMapper.selectOne(queryWrapper);
        if (userDao == null) {
            throw new TotalException(StateEnum.USER_ERROR_WRONGPHONEORPASSWORD.getCode(),
                    StateEnum.USER_ERROR_WRONGPHONEORPASSWORD.getMessage(),
                    StateEnum.USER_ERROR_WRONGPHONEORPASSWORD.getMessage());
        }

        /**
         * 获取乱码和盐
         */

        String salt = userDao.getSalt();
        String password = userDao.getPassword();
        String permission = userDao.getPermission();
        String s = getJWTByPhone(phone);
        try{
            redisUtils.hPut(s,"permission",permission);
        }catch (Exception e){
            log.error("更新用户权限失败");
        }

        if (password == null || salt == null) {
            return ResponseResult.failed(StateEnum.USER_ERROR_NOREGISTER.getCode(),
                    StateEnum.USER_ERROR_NOREGISTER.getMessage());
        }
        String total = userLoginVO.getPassword() + salt;
        String pass = MD5Util.getMD5(total);
        if (!pass.equals(password)) {
            return ResponseResult.failed(StateEnum.USER_ERROR_WRONGPASSWORD.getCode(),
                    StateEnum.USER_ERROR_WRONGPASSWORD.getMessage());
        }
        return ResponseResult.success(s);
    }

    @Override
    public ResponseResult userRegister(UserRegisterVO userRegisterVO) {
        String regCode = userRegisterVO.getCode();
        String phone = userRegisterVO.getPhone();
        String salt = SaltUtil.getSalt(4);
        String name = userRegisterVO.getName();
        String total = userRegisterVO.getPassword() + salt;
        String resultPassword = MD5Util.getMD5(total);
        String correctCode = redisService.getCode(phone);
        if (!regCode.equals(correctCode)) {
            return ResponseResult.failed(StateEnum.USER_ERROR_WRONGCODE.getCode(),
                    StateEnum.USER_ERROR_WRONGCODE.getMessage());
        }
        /**
         * 判断是否注册
         */

        if (userMapper.checkIsRegister(phone) > 0) {
            return ResponseResult.failed(StateEnum.USER_ERROR_HASBEENREGISTER.getCode(),
                    StateEnum.USER_ERROR_HASBEENREGISTER.getMessage());
        }

        UserDao userDao = new UserDao();
        userDao.setSalt(salt);
        userDao.setPassword(resultPassword);
        userDao.setPhone(phone);
        userDao.setName(name);
        userMapper.insert(userDao);
        return ResponseResult.success();
    }

    @Override
    public String getJWTByPhone(String phone) throws TotalException {
        String jwt = JWTUtil.getJWT(phone);
        try {
            redisService.setToken(phone, jwt);
        } catch (Exception e) {
            throw new TotalException(StateEnum.USER_ERROR_FAILEDTOBINGDINGJWT.getCode(),
                    StateEnum.USER_ERROR_FAILEDTOBINGDINGJWT.getMessage(),
                    StateEnum.USER_ERROR_FAILEDTOBINGDINGJWT.getMessage());
        }
        return jwt;
    }

    @Override
    public String getLoginCode() {
        return null;
    }

    @Override
    public String uploadHeadShot(String url, String phone, MultipartFile file, String extendsion) throws IOException {
        String uuid = IdUtil.randomUUID();
        String profileName = url+"/"+phone+"/";
        File profile1 = new File(url);
        File profile2 = new File(profileName);

        if (!profile1.exists() || !profile2.exists()) {
            log.info("没有存储文件夹，尝试新建");
            if (profile1.mkdirs()) {
                log.info("存储文件夹新建成功");
            } else {
                log.error("存储文件夹新建失败");
                return "服务器存储失败";
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append(uuid);
        sb.append(".");
        sb.append(extendsion);
        String fileName = sb.toString();
        byte[] bytes = file.getBytes();
        String allURL = profileName+fileName;

        OutputStream outputStream = new FileOutputStream(allURL);
        outputStream.write(bytes);
        UpdateWrapper<UserDao> updateWrapper = new UpdateWrapper<UserDao>();
        updateWrapper.eq("phone","17759048528");
        UserDao userDao = new UserDao();
        userDao.setHeadshot(allURL);
        userMapper.update(userDao, updateWrapper);
        return allURL;

    }

    @Override
    public ServletOutputStream getUserHeadShotStream(String phone, ServletOutputStream outputStream) throws IOException {

        String userHeadShotUrl = getUserHeadShotUrl(phone);
        /**
         * 获取headshoturl
         */

        try {
            InputStream fileInputStream = new FileInputStream(userHeadShotUrl);
            int ch;
            while ((ch = fileInputStream.read()) != -1) {
                outputStream.write(ch);
            }
        } catch (IOException e) {
            log.error("url路径错误");
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }

        return outputStream;
    }

    @Override
    public List<UserCollectDao> getUserCollect(String phone) {
        QueryWrapper<UserCollectDao> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("user_id","oid","my_index");
        queryWrapper.eq("user_id",phone);
        List<UserCollectDao> userCollectDao1 = userCollectMapper.selectList(queryWrapper);
        return userCollectDao1;
    }

    @Override
    public void deleteCollect(String userId, String id) {
        QueryWrapper<UserCollectDao> queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id",userId);
        queryWrapper.eq("oid",id);
        userCollectMapper.delete(queryWrapper);
    }


    @Override
    public void deleteHistory(String userId, String id) {
        QueryWrapper<UserHistoryDao> queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id",userId);
        queryWrapper.eq("oid",id);
        userHistoryMapper.delete(queryWrapper);
    }



    public String getUserHeadShotUrl(String phone) {
        QueryWrapper<UserDao> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("headshot");
        queryWrapper.eq("phone",phone);
        UserDao userDao = userMapper.selectOne(queryWrapper);
        String url = userDao.getHeadshot();
        if(url==null){
            //返回无头像的存储地址
            return null;
        }
        return url;
    }
}
