package com.guo.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.guo.bean.dto.UrlEnum;
import com.guo.bean.vo.ResponseResult;
import com.guo.bean.vo.UserLoginVO;
import com.guo.bean.vo.UserRegisterVO;
import com.guo.exception.TotalException;
import com.guo.service.RedisService;
import com.guo.service.UserService;
import com.guo.utils.FileUtil;
import com.guo.utils.RedisUtils;
import com.guo.utils.StateEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author guokaifeng
 * @createDate: 2022/4/4
 **/

@RestController
@RequestMapping("/api/user")
@CrossOrigin
@Slf4j
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    RedisService redisService;
    @Autowired
    RedisUtils redisUtils;


    @PostMapping("/passport/login")
    public ResponseResult<String> userLogin(@Valid UserLoginVO userLoginVO, BindingResult bindingResult) throws TotalException {
        if (bindingResult.hasErrors()) {
            String defaultMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            throw new TotalException(StateEnum.USER_ERROR_ERRORFORMAT.getCode(),
                    defaultMessage, StateEnum.USER_ERROR_ERRORFORMAT.getMessage());
        }

        ResponseResult<String> responseResult = userService.userLogin(userLoginVO);
        return responseResult;
        /**
         * 返回JWT
         */

    }

    /**
     * 用户注册
     *
     * @param userRegisterVO
     * @param bindingResult
     * @return
     * @throws TotalException
     */

    @PostMapping("/passport/register")
    public ResponseResult userRegister(@Valid UserRegisterVO userRegisterVO, BindingResult bindingResult) throws TotalException {
        if (bindingResult.hasErrors()) {
            String defaultMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            throw new TotalException(defaultMessage);
        }
        return userService.userRegister(userRegisterVO);
    }

    /**
     * @param response
     * @param phone
     * @return 验证码图片
     * @throws IOException
     */

    @GetMapping("/passport/sendCode/{phone}")
    public ResponseResult getVertifyCode(HttpServletResponse response, @PathVariable String phone) throws IOException {
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);
        OutputStream out = response.getOutputStream();
        try {
            lineCaptcha.write(out);
            redisService.storeCode(phone, lineCaptcha.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.failed(StateEnum.SERVICE_ERROR_FAILEDTOGETCODE.getCode(),
                    StateEnum.SERVICE_ERROR_FAILEDTOGETCODE.getMessage());
        } finally {
            out.close();
        }
        return ResponseResult.success();
    }

    @GetMapping("/passport/logout/{phone}")
    public ResponseResult userLogout(@RequestHeader String token) {
//        redisService.deleteKey(phone);
        redisUtils.delete(token);
        return ResponseResult.success();
    }

    @GetMapping("/nologin")
    public ResponseResult userNoLoginPage(HttpServletRequest httpServletRequest) throws TotalException {
        Object attribute = httpServletRequest.getAttribute("filter.error");
        TotalException totalException = (TotalException) attribute;
        if (totalException != null) {
            throw totalException;
        }
        return ResponseResult.failed(StateEnum.USER_ERROR_NOLOGIN.getCode(),
                StateEnum.USER_ERROR_NOLOGIN.getMessage());
    }

    /**
     * 用户头像上传
     *
     * @param
     * @return
     */
    @PostMapping("/upload")
    public ResponseResult userUploadFile(MultipartFile file, String phone) throws TotalException, IOException {
        if (file == null) {
            throw new TotalException(StateEnum.USER_ERROR_HEADSHOTISNULL.getCode(),
                    StateEnum.USER_ERROR_HEADSHOTISNULL.getMessage());
        }
        String fileExtension = FileUtil.getFileExtension(file);

        userService.uploadHeadShot(UrlEnum.USER_HEADSHOT.getUrl(), phone, file, fileExtension);

        return ResponseResult.success();
    }

    /**
     * 获得用户头像
     *
     * @param
     * @return
     */
    @GetMapping("/getheadshot")
    public ResponseResult getUserHeadShot(HttpServletResponse response, String phone) throws IOException {
        /**
         * 数据库查询用户头像存储地址
         */
        ServletOutputStream outputStream = response.getOutputStream();
        userService.getUserHeadShotStream(phone, outputStream);
        return ResponseResult.success();
    }


    @GetMapping("/demo")
    public String demo(String phone) {


        return "[{\"content\":\"猴痘的病因主要为感染猴痘病毒。病毒进入人体后，可通过血液广泛地播散到全身皮肤、黏膜等组织从而引起相应病变。密切接触啮齿类动物及儿童未接种牛痘疫苗为危险因素。\",\"title\":\"总述\"},{\"content\":\"猴痘病毒进入人体，可迅速到达淋巴组织，在内大量复制后入血，形成一次短暂的病毒血症。病毒通过血液感染全身单核-巨噬细胞，并继续复制、释放入血，导致第二次病毒血症。病毒继而通过血液更广泛地传播到全身皮肤、黏膜等组织，并引起相应的病变，如出现皮疹等。\",\"title\":\"基本病因\"},{\"content\":\"1、密切接触啮齿类动物。2、儿童未接种牛痘疫苗。\",\"title\":\"危险因素\"}]\n";
    }

}
