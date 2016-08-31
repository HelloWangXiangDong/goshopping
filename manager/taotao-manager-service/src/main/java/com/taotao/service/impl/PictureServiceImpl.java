package com.taotao.service.impl;

import com.taotao.common.utils.FtpUtil;
import com.taotao.common.utils.IDUtils;
import com.taotao.service.PictureService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by XDStation on 2016/7/24 0024.
 * 图片上传服务
 */
@Service
public class PictureServiceImpl implements PictureService{
    @Value("#{configProperties['FTP_ADDRESS']}")
    private String FTP_ADDRESS;
    @Value("#{configProperties['FTP_PORT']}")
    private Integer FTP_PORT;
    @Value("#{configProperties['FTP_USERNAME']}")
    private String FTP_USERNAME;
    @Value("#{configProperties['FTP_PASSWORD']}")
    private String FTP_PASSWORD;
    @Value("#{configProperties['FTP_BASEPATH']}")
    private String FTP_BASE_PATH;
    @Value("#{configProperties['IMAGE_BASE_URL']}")
    private String IMAGE_BASE_URL;
    @Override
    public Map uploadFile(MultipartFile file) {
        boolean flag = false;
        //生成一个新的文件名称
        //取原始文件名
        String oldName = file.getOriginalFilename();
        String imagePath = new DateTime().toString("yyyy-MM-dd")+"/";
        String newName = IDUtils.genImageName() + oldName.substring(oldName.indexOf("."));
        Map map = new HashMap();
        try {
            //图片上传
            flag = FtpUtil.uploadFile(
                            FTP_ADDRESS,
                            FTP_PORT,
                            FTP_USERNAME,
                            FTP_PASSWORD,
                            FTP_BASE_PATH,
                            imagePath,
                            newName,
                            file.getInputStream()
                    );
        } catch (IOException e) {
            map.put("error",1);
            map.put("message","服务器异常，图片上传失败！");
            e.printStackTrace();
            System.out.println("图片路径错误！");
        }
        if(!flag){
            map.put("error",1);
            map.put("message","上传失败！");
        }else{
            map.put("error",0);
            map.put("url",IMAGE_BASE_URL+"/"+imagePath+newName);
        }
        return map;
    }
}
