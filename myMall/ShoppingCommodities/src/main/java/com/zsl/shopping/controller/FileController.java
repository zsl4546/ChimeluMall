package com.zsl.shopping.controller;

import com.alibaba.fastjson.JSONObject;
import com.zsl.RequestAndResponse.DeleteCommodityRequest;
import com.zsl.RequestAndResponse.DeleteCommodityResponse;
import com.zsl.UpdateService;
import com.zsl.entitys.Commodity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;

@RestController
@RequestMapping("/file")
@Slf4j
public class FileController {

    @Reference
    private UpdateService updateService;

    @Value("${uploadFilePath}")
    private String uploadFilePath;

    @Value("${downloadFilePath}")
    private String downloadFilePath;

    @Value("${deleteFilePath}")
    private String deleteFilePath;

    @PostMapping("/upload")
    public String fileUpload(@RequestParam("file") MultipartFile files[]) {
        JSONObject object = new JSONObject();
        for (int i = 0; i < files.length; i++) {
            String fileName = files[i].getOriginalFilename();  // 文件名
            File dest = new File(uploadFilePath + '/' + fileName);
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            try {
                files[i].transferTo(dest);
            } catch (Exception e) {
                log.error("{}", e);
                object.put("success", 2);
                object.put("result", "程序错误，请重新上传");
                return object.toString();
            }
        }
        object.put("success", 1);
        object.put("result", "文件上传成功");
        return object.toString();
    }

    @RequestMapping("/download")
//    @CrossOrigin(allowedHeaders = "*",origins = "http://localhost:8081, http://localhost:8082")
    public String fileDownLoad(HttpServletResponse response, @RequestParam("fileName") String fileName) {
        File file = new File(downloadFilePath +'/' + '/' + fileName);
        System.out.println('-' + fileName);
        if (!file.exists()) {
            return "下载文件不存在";
        }
        response.reset();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));) {
            byte[] buff = new byte[1024];
            OutputStream os = response.getOutputStream();
            int i = 0;
            while ((i = bis.read(buff)) != -1) {
                os.write(buff, 0, i);
                os.flush();
            }
        } catch (IOException e) {
            log.error("{}", e);
            return "下载失败";
        }
        return "下载成功";
    }

    @DeleteMapping("/delete")
    public DeleteCommodityResponse deleteCommodity(Commodity commodity) throws Exception {
        DeleteCommodityRequest request = new DeleteCommodityRequest();
        request.setId(commodity.getId());
        deleteFile(commodity.getFileName());
        return updateService.deleteCommodity(request);
    }

    public void deleteFile(String fileName) {
        System.out.println(fileName);
        File file = new File(deleteFilePath+"/"+fileName);
        //判断文件不为null或文件目录存在
        if (!file.exists()) {
            log.info("暂无文件");
            return;
        }

        file.delete();
    }

    public static boolean downloadFromUrl(String url, String fileName, String dir) {
        try {
            URL httpurl = new URL(url);
            System.out.println(fileName);
            File file = new File(dir + '/' + fileName);

            //目录不存在 则创建
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (file.exists()) {
                file.delete();
                log.info("文件重复，替换掉" + file.toPath().toString());
            }
            FileUtils.copyURLToFile(httpurl, file);
        } catch (Exception e) {
            log.error("{}", e);
            return false;
        }
        log.info("保存成功：" + dir + '/' + fileName);
        return true;
    }

}
