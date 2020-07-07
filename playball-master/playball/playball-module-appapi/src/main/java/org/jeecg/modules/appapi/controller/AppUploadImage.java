package org.jeecg.modules.appapi.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.util.CommonUtils;
import org.jeecg.common.util.MinioUtil;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.appapi.entity.AppTeam;
import org.jeecg.modules.appapi.utils.UploadImageUtiles;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/appUpload")
public class AppUploadImage {
    /**
     * 上传
     * @param request
     */
    @PostMapping(value = "/uploadImage")
    public Result<JSONObject> uploadImage(HttpServletRequest request) {

        Result<JSONObject> result = new Result<JSONObject>();
        try{
            JSONObject obj = new JSONObject();
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            MultipartFile file = multipartRequest.getFile("file");// 获取上传文件对象
            String orgName = file.getOriginalFilename();// 获取文件名
            String filename = UploadImageUtiles.uploadImages(file,request.getParameter("imageName"));
            obj.put("filename",filename);
            result.setResult(obj);
            result.success("请求成功");
        }catch (Exception e){
            e.printStackTrace();
            result.error500("请求失败");
        }
        return result;
    }
}
