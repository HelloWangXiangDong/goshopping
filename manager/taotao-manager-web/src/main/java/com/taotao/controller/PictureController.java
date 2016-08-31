package com.taotao.controller;

import com.taotao.common.utils.JsonUtils;
import com.taotao.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * Created by XDStation on 2016/7/24 0024.
 * 上传图片处理
 */
@Controller
public class PictureController {
    @Autowired
    private PictureService pictureService;
    @RequestMapping(value="/pic/upload")
    public @ResponseBody String uploadPic(MultipartFile uploadFile){
        String json = JsonUtils.objectToJson(pictureService.uploadFile(uploadFile));
        return json;
    }
}
