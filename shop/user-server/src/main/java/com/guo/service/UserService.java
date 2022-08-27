package com.guo.service;

import com.guo.bean.dao.UserCollectDao;
import com.guo.bean.dao.UserHistoryDao;
import com.guo.bean.vo.UserLoginVO;
import com.guo.bean.vo.UserRegisterVO;
import com.guo.exception.TotalException;
import com.guo.bean.vo.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @author guokaifeng
 * @createDate: 2022/5/6
 **/

public interface UserService {
    void putUserCollect(String userId,String id,String index) throws TotalException;
    List<UserCollectDao> getUserCollect(String userId, String begin, String num);
    ResponseResult userLogin(UserLoginVO userLoginVO) throws TotalException;
    ResponseResult userRegister(UserRegisterVO userRegisterVO);
    String getJWTByPhone(String phone) throws TotalException;
    String getLoginCode();
    String uploadHeadShot(String url, String phone, MultipartFile file, String extendsion) throws IOException;
    ServletOutputStream getUserHeadShotStream(String phone, ServletOutputStream outputStream) throws IOException;
    List<UserCollectDao> getUserCollect(String phone);
    void deleteCollect(String userId,String id);
    List<UserHistoryDao> getUserHistory(String userId, String begin, String num);
    void putUserHistory(String userId,String id,String index) throws TotalException;
    void deleteHistory(String userId,String id);
}
