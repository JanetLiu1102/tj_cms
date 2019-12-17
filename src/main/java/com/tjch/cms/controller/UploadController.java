/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: UploadController
 * Author:   Administrator
 * Date:     2019/12/9 14:02
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.tjch.cms.controller;

import com.tjch.cms.base.ResponseBase;
import com.tjch.cms.exception.BusinessException;
import com.tjch.cms.model.FileInfoModel;
import com.tjch.cms.service.FileInfoService;
import com.tjch.cms.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author Administrator
 * @create 2019/12/9
 * @since 1.0.0
 */
@RestController
@RequestMapping("/upload")
@CrossOrigin
public class UploadController {

    @Autowired
    private FileInfoService fileInfoService;

    /**
     * 文件上传
     * 1. 文件上传后的文件名
     * 2. 上传后的文件路径 , 当前年月日时 如:2018060801  2018年6月8日 01时
     * 3. file po 类需要保存文件信息 ,旧名 ,大小 , 上传时间 , 是否删除 ,
     *
     * @param file
     * @param request
     * @return
     */
    @PostMapping("uploadFile")
    public ResponseBase uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        ResponseBase res = new ResponseBase();
        // 判断文件是否为空
        if (file.isEmpty()) {
            res.setMsg("文件不能为空");
            return res;
        }
        try {
            return fileInfoService.upload(file);
        } catch (BusinessException e) {
            res.setCode(500);
            res.setMsg(e.getCode());
            return res;
        }
    }

    /**
     * 文件下载
     * @param fileName
     * @param res
     */
    @RequestMapping(value = "/downloadFile" ,method = RequestMethod.GET)
    public void downloadFile(@RequestParam("fileName") String fileName, HttpServletResponse res) {
        try {
            fileInfoService.downloadFile(fileName, res);
        } catch (BusinessException e) {
            e.getCode();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件查看
     */
    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> view(@RequestParam("fileName") String fileName){
        FileInfoModel fileModel = null;
        try {
            fileModel = fileInfoService.getImage(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (fileModel == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        HttpHeaders header = new HttpHeaders();
        if (FileUtils.match(fileModel.getFileName(), "jpg", "png", "gif", "bmp", "tif")) {
            header.setContentType(MediaType.IMAGE_JPEG);
        } else if (FileUtils.match(fileModel.getFileName(), "html", "htm")) {
            header.setContentType(MediaType.TEXT_HTML);
        } else if (FileUtils.match(fileModel.getFileName(), "pdf")) {
            header.setContentType(MediaType.APPLICATION_PDF);
        } else {
            header.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        }
        header.add("X-Filename", fileModel.getFileName());
        header.add("X-MD5", fileModel.getMd5());

        return new ResponseEntity<>(new InputStreamResource(fileModel.getContent()), header, HttpStatus.OK);
    }

    /**
     * 文件列表查询
     */
    @RequestMapping(value = "/find")
    public ResponseBase findList(@RequestParam("resourceId") String resourceId) {
        ResponseBase res = new ResponseBase();
        try {
            return fileInfoService.findFileList(resourceId);
        }catch (BusinessException e){
            res.setCode(500);
            res.setMsg("查询失败");
            return res;
        }
    }

    /**
     * 逻辑删除文件
     */
    @RequestMapping(value = "/deleteFile",method = RequestMethod.GET)
    public ResponseBase deleteFile(@RequestParam("fileName") String fileName) {
        ResponseBase res = new ResponseBase();

        try {
            return fileInfoService.deleteFile(fileName);
        }catch (BusinessException e){
            res.setCode(500);
            res.setMsg("删除失败");
            return res;
        }
    }

}
