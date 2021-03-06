package com.io.ikaonigiri.controller;

import com.google.gson.JsonObject;
import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@RestController
public class ImageUploadController {

    @PostMapping(value = "/uploadImage", produces = "application/json")
    public JsonObject uploadSummernoteImageFile(@RequestParam("file") MultipartFile multipartFile){

        JsonObject jsonObject = new JsonObject();

        String fileRoot = "C:\\summernote_image\\"; //저장될 경로
        String originalFileName = multipartFile.getOriginalFilename();
        String extension = originalFileName.substring(originalFileName.lastIndexOf('.'));

        // 랜덤 UUID+ 확장자로 저장될 파일이름
        String savedFileName = UUID.randomUUID() + extension;

        File targetFile = new File(fileRoot + savedFileName);

        try {

            InputStream fileStream = multipartFile.getInputStream();
            FileUtils.copyInputStreamToFile(fileStream, targetFile); //파일 저장
            jsonObject.addProperty("url","/summernoteImage/"+savedFileName);
            jsonObject.addProperty("responseCode","success");

        }catch (IOException e){

            FileUtils.deleteQuietly(targetFile);	// 실패시 저장된 파일 삭제
            jsonObject.addProperty("responseCode", "error");
            e.printStackTrace();

        }

        return jsonObject;
    }
}
