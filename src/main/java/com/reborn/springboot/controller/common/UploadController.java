package com.reborn.springboot.controller.common;

import com.reborn.springboot.entity.Result;
import com.reborn.springboot.utils.ResultGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.processing.FilerException;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Controller
@RequestMapping("/admin/upload")
public class UploadController {

    private static final String filePath = "/static/upload/img/";

    @RequestMapping("/file")
    @ResponseBody
    public Result uploadFile(HttpServletRequest request, @RequestParam("file") MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String subfixName = fileName.substring(fileName.lastIndexOf("."));
        StringBuffer sb = new StringBuffer();
        String referncedId = UUID.randomUUID().toString().replace("-", "");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        sb.append(sdf.format(new Date())).append("-"+referncedId).append(subfixName);
        String newFileName = sb.toString();
        String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();
        File fileDirectory = new File(path,filePath);
        File newFile = new File(fileDirectory,newFileName);
        //System.out.println(request.getSession().getServletContext().getRealPath("/"));
        try {
            if (!fileDirectory.exists()){
                if (!fileDirectory.mkdirs()){
                    throw new IOException("文件夹创建失败，路径为"+fileDirectory.getAbsolutePath());
                }
            }
            file.transferTo(newFile);
            String url = filePath.substring(filePath.indexOf("/",1))+newFileName;
            Result result = ResultGenerator.getSuccessResult("success");
            result.setData(url);
            return result;
        }catch (IOException e){
            e.printStackTrace();
            return ResultGenerator.getFailResult("上传失败");
        }
    }
}
