package com.taotao.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * Created by XDStation on 2016/7/24 0024.
 */
public interface PictureService {
    Map uploadFile(MultipartFile file);
}
