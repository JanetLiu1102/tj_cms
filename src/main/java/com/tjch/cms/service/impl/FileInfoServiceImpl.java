package com.tjch.cms.service.impl;

import com.tjch.cms.base.BaseApiService;
import com.tjch.cms.base.ResponseBase;
import com.tjch.cms.config.URLConstant;
import com.tjch.cms.dao.FileInfoMapper;
import com.tjch.cms.dao.FileInfoUserMapper;
import com.tjch.cms.exception.BusinessException;
import com.tjch.cms.model.FileInfoModel;
import com.tjch.cms.pojo.FileInfo;
import com.tjch.cms.service.FileInfoService;
import com.tjch.cms.utils.DateUtils;
import com.tjch.cms.utils.FileUtils;
import com.tjch.cms.utils.ResponseInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * @author Dreamhai
 * @desc
 * @date 2018-06-08 11:20
 */
@Slf4j
@Service(value = "fileInfoService")
@Transactional
public class FileInfoServiceImpl extends BaseApiService implements FileInfoService {

    @Autowired
    private FileInfoUserMapper fileInfoUserMapper;
    @Autowired
    private FileInfoMapper fileInfoMapper;

    @Override
    public ResponseBase upload(MultipartFile file) throws BusinessException {
        ResponseBase res = new ResponseBase();
        //基础路径  E:/springboot-upload/image/
        String basePath = URLConstant.FILE_PATH;
        //获取文件保存路径 \20180608\113339\
        String folder = FileUtils.getFolder();
        // 获取前缀为"FL_" 长度为20 的文件名  FL_eUljOejPseMeDg86h.png
        String fileName = FileUtils.getFileName() + FileUtils.getFileNameSub(file.getOriginalFilename());

        try {
            // E:\springboot-upload\image\20180608\113339
            Path filePath = Files.createDirectories(Paths.get(basePath,folder));
            log.info("path01-->{}", filePath);

            //写入文件  E:\springboot-upload\image\20180608\113339\FL_eUljOejPseMeDg86h.png
            Path fullPath = Paths.get(basePath, folder, fileName);
            log.info("fullPath-->{}", fullPath);
            // E:\springboot-upload\image\20180608\113339\FL_eUljOejPseMeDg86h.png
            Files.write(fullPath, file.getBytes(), StandardOpenOption.CREATE);

            //保存文件信息
            FileInfo fileInfo = new FileInfo();
            fileInfo.setFileOriginName(file.getOriginalFilename());
            fileInfo.setFileType(file.getContentType());
            fileInfo.setSize(file.getSize());
            fileInfo.setMd5(FileUtils.md5File(fullPath.toString()));
            fileInfo.setFileName(fileName);
            fileInfo.setValid((byte)1);
            fileInfo.setIsDelete((byte)0);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            fileInfo.setUploadTime(df.format(new Date()));
            fileInfo.setFilePath(filePath.toString());
            fileInfoMapper.insert(fileInfo);
            FileInfoModel model = new FileInfoModel();
            model.setFileName(fileName);
            model.setFileOriginName(file.getOriginalFilename());
            model.setFilePath(filePath.toString());
            res.setData(model);
        } catch (Exception e) {
            Path path = Paths.get(basePath, folder);
            log.error("写入文件异常,删除文件。。。。", e);
            try {
                Files.deleteIfExists(path);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            res.setCode(500);
            res.setMsg("上传失败");
            return res;
        }
        res.setCode(500);
        res.setMsg("成功");
        return res;
    }

    @Override
    public void downloadFile(String fileName, HttpServletResponse res) throws BusinessException, UnsupportedEncodingException {

        if (fileName == null) {
            throw new BusinessException("1001", "文件名不能为空");
        }

        // 通过文件名查找文件信息
        FileInfo fileInfo = fileInfoUserMapper.findByFileName(fileName);
        log.info("fileInfo-->{}", fileInfo);
        if (fileInfo == null) {
            throw new BusinessException("2001", "文件名不存在");
        }


        int index=fileName.indexOf(".");

        String fileSuffix =fileName.substring(index);

            //设置响应头
            res.setContentType("application/force-download");// 设置强制下载不打开
            res.addHeader("Content-Disposition", "attachment;fileName=" +
                    new String(fileInfo.getFileOriginName().getBytes("gbk"), "iso8859-1"));// 设置文件名
            res.setHeader("Context-Type", "application/xmsdownload");
        //判断文件是否存在
        File file = new File(Paths.get(fileInfo.getFilePath(), fileName).toString());
        if (file.exists()) {
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os = res.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
                log.info("下载成功");
            } catch (Exception e) {
                e.printStackTrace();
                throw new BusinessException("9999", e.getMessage());
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public FileInfoModel getImage(String fileName) throws IOException {
        log.info("fileName-->{}", fileName);
        FileInfo fileInfo =fileInfoUserMapper.findByFileNameAndValid(fileName, true);
        if (fileInfo == null) {
            return null;
        }
        Path path = Paths.get(fileInfo.getFilePath(), fileInfo.getFileName());
        log.info("path-->{}", path);
        FileInfoModel model = new FileInfoModel();
        model.setContent(Files.newInputStream(path));
        model.setFilePath(fileInfo.getFilePath());
        model.setFileOriginName(fileInfo.getFileOriginName());
        model.setFileName(fileInfo.getFileName());
        model.setMd5(fileInfo.getMd5());
        return model;
    }

    @Override
    public ResponseBase findFileList(String resourceId) throws BusinessException {
        if (resourceId == null){
            throw new BusinessException("500","资源id不能为空");
        }
        ResponseBase res = new ResponseBase();
                res.setData(fileInfoUserMapper.findByResourceId(resourceId));
                res.setCode(200);
                res.setMsg("查询成功");
        // 根据资源id查询文件信息
        return res;
    }

    @Override
    public ResponseBase deleteFile(String fileName) throws BusinessException {
        ResponseBase res = new ResponseBase();
        if (fileName ==  null){
            throw new BusinessException("1001","文件名不能为空");
        }
        FileInfo fileInfo = fileInfoUserMapper.findByFileName(fileName);
        if (fileInfo == null){
            throw new BusinessException("2001","文件名:"+fileName+" 不存在");
        }
        // 逻辑删除文件
        fileInfo.setIsDelete((byte)1);
        fileInfo.setValid((byte)0);
        fileInfo.setDeleteTime(DateUtils.getDateString(new Date()));
        fileInfoMapper.updateByPrimaryKeySelective(fileInfo);
        res.setData(fileInfo);
        return res;
    }

    @Override
    public void deleteValidFalse() {
//定时删除无效图片信息
        List<FileInfo> fileInfos = fileInfoUserMapper.findByValid(false);
        if(fileInfos.size()>0){
            for(FileInfo file:fileInfos){
                Path path = Paths.get(file.getFilePath(), file.getFileName());
                try {
                    Files.deleteIfExists(path);
                    Path path1 = Paths.get(file.getFilePath());
                    Files.deleteIfExists(path1);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            fileInfoUserMapper.deleteAll(fileInfos);
        }
        log.info("本次删除数据:{}",fileInfos);
    }
}
