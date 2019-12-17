package com.tjch.cms.service;

import com.tjch.cms.base.ResponseBase;
import com.tjch.cms.exception.BusinessException;
import com.tjch.cms.model.FileInfoModel;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.*;



public interface FileInfoService {
    /**
     * 上传文件
     *
     * @param file
     * @return
     * @throws BusinessException
     */
     ResponseBase upload(MultipartFile file) throws BusinessException;


    /**
     * 文件下载
     *
     * @param fileName
     * @param res
     * @throws BusinessException
     * @throws UnsupportedEncodingException
     */
     void downloadFile(String fileName, HttpServletResponse res) throws BusinessException, UnsupportedEncodingException ;
    /**
     * 文件查看
     */
    FileInfoModel getImage(String fileName) throws IOException ;

    /**
     * 根据资源id查询文件信息
     * @param resourceId
     * @return
     * @throws BusinessException
     */
    ResponseBase findFileList(String resourceId) throws BusinessException ;

    /**
     * 逻辑删除文件
     * @param fileName
     * @return
     * @throws BusinessException
     */
    ResponseBase deleteFile(String fileName) throws BusinessException;

     void deleteValidFalse() throws IOException;
}
