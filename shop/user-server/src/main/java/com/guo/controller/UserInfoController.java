package com.guo.controller;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.guo.bean.dao.UserInfoDao;
import com.guo.bean.mapper.UserInfoMapper;
import com.guo.bean.mapper.UserMapper;
import com.guo.bean.vo.ResponseResult;
import com.guo.bean.vo.UserInfoVo;
import com.guo.exception.TotalException;
import com.guo.service.RedisService;
import com.guo.service.UserInfoService;
import com.guo.service.UserService;
import com.guo.utils.FileUtil;
import com.guo.utils.StateEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/user/info")
@CrossOrigin
public class UserInfoController {

    @Autowired
    RedisService redisService;

    @Autowired
    UserInfoService userInfoService;


    @RequestMapping("/get/permission")
    public ResponseResult getPermission(HttpServletRequest request){
        String token = request.getHeader("token");
        String userPermissionByToken = redisService.getUserPermissionByToken(token);
        return ResponseResult.success(userPermissionByToken);
    }

    @RequestMapping("/information")
    public ResponseResult getUserInformation(HttpServletRequest request){
        String token = request.getHeader("token");
        String phone = redisService.getUserPhoneByToken(token);
        UserInfoDao userInfo = userInfoService.getUserInfo(phone);
        if(userInfo==null){
            return ResponseResult.failed(StateEnum.USER_ERROR_INFO.getCode(),StateEnum.USER_ERROR_INFO.getMessage() );
        }
        return ResponseResult.success(userInfo);
    }

    @PostMapping("/uploadImg")
    public ResponseResult upload(String phone, MultipartFile file) throws IOException {
        if(file==null){
            return ResponseResult.failed("0","上传的文件为空");
        }
        StringBuilder stringBuilder = new StringBuilder(60);
        String url = "/content/";
        File file1 = new File("/content");
        if(!file1.exists()){
            file1.mkdirs();
        }
        UUID uuid = UUID.randomUUID();
        String uuids = uuid.toString()
                .replace("-","");
        String fileExtension = FileUtil.getFileExtension(file);
        stringBuilder.append(url);
        stringBuilder.append(uuids);
        stringBuilder.append(fileExtension);
        try {
            InputStream inputStream = file.getInputStream();
            File tofile = new File(stringBuilder.toString());
            OutputStream outputStream = new FileOutputStream(tofile);
            int readlen;
            byte[] bytes = new byte[4096];
            while ((readlen = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, readlen);
            }
            inputStream.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String imageUrl = stringBuilder.toString();
        UserInfoDao userInfoDao = new UserInfoDao();
        userInfoDao.setImgUrl(imageUrl);
        userInfoDao.setPhone(phone);
        userInfoService.upDateUserImg(userInfoDao);

        return ResponseResult.success();

    }
    @GetMapping("/getphoto")
    public ResponseResult getImg(String phone, ServletOutputStream outputStream) throws IOException, TotalException {
        String skuDefaultImg = userInfoService.getUrl(phone);
        if(skuDefaultImg==null){
            return ResponseResult.success("用户没有上传图片");
        }
        try {
            InputStream fileInputStream = new FileInputStream(skuDefaultImg);
            int ch;
            while ((ch = fileInputStream.read()) != -1) {
                outputStream.write(ch);
            }
        } catch (IOException e) {
            throw new TotalException(StateEnum.FILE_FAILED.getCode(),StateEnum.FILE_FAILED.getMessage(),e.getMessage());
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }

        }
        return ResponseResult.success();
    }

    @PostMapping("/updateUserInfo")
    public ResponseResult updateUserInfo(HttpServletRequest request, UserInfoVo userInfoVo){
        String token = request.getHeader("token");
        String phone = redisService.getUserPhoneByToken(token);
        userInfoVo.setPhone(phone);
        userInfoService.upDateUser(userInfoVo);
        return ResponseResult.success();
    }



}
