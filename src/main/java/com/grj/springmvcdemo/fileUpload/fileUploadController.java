package com.grj.springmvcdemo.fileUpload;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.Iterator;

@Controller
@RequestMapping(value = "fileUpload")
public class fileUploadController {

    private static final Logger logger = LoggerFactory.getLogger(fileUploadController.class);

    /*
     * 通过流的方式上传文件
     * @RequestParam("file") 将name=file控件得到的文件封装成CommonsMultipartFile 对象
     */
    @RequestMapping(value = "cmrFileUpload", method = RequestMethod.POST)
    public String MultipartResolverFileUpload(
            @RequestParam(value = "file", required = false) CommonsMultipartFile file
            , Model model) throws IOException {

        long startTime = System.currentTimeMillis();
        System.out.println("fileName：" + file.getOriginalFilename());

        try {
            //获取输出流
            OutputStream os = new FileOutputStream(new Date().getTime() + file.getOriginalFilename());
            //获取输入流 CommonsMultipartFile 中可以直接得到文件的流
            InputStream is = file.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            int temp;
            //一个一个字节的读取并写入
            logger.debug("此处不进行输出文件, 只进行打印...");
            while ((temp = isr.read()) != (-1)) {
                os.write(temp);
                System.out.print((char) temp);
            }
            os.flush();
            os.close();
            is.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("方法一的运行时间：" + String.valueOf(endTime - startTime) + "ms");
        return "redirect:/success.jsp";
    }


    /*
     * 采用file.Transto 来保存上传的文件
     */
    @RequestMapping("transferTo")
    public String transferTo(
            @RequestParam(value="file", required = false) CommonsMultipartFile file
    ) throws IOException {
        long startTime = System.currentTimeMillis();
        System.out.println("fileName：" + file.getOriginalFilename());
        String path = this.getClass().getResource("/").getPath() + new Date().getTime() + file.getOriginalFilename();

        File newFile = new File(path);
        //通过CommonsMultipartFile的方法直接写文件（注意这个时候）
        file.transferTo(newFile);
        long endTime = System.currentTimeMillis();
        System.out.println("方法二的运行时间：" + String.valueOf(endTime - startTime) + "ms");
        return "redirect:/success.jsp";
    }


    /*
     *采用spring提供的上传文件的方法
     */
    @RequestMapping("springUpload")
    public String springUpload(HttpServletRequest request) throws IllegalStateException, IOException {
        long startTime = System.currentTimeMillis();
        //将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        //检查form中是否有enctype="multipart/form-data"
        if (multipartResolver.isMultipart(request)) {
            //将request变成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            //获取multiRequest 中所有的文件名
            Iterator iter = multiRequest.getFileNames();

            while (iter.hasNext()) {
                //一次遍历所有文件
                MultipartFile file = multiRequest.getFile(iter.next().toString());
                if (file != null) {
                    String path = this.getClass().getResource("").getPath() + new Date().getTime() + file.getOriginalFilename();
                    //上传
                    file.transferTo(new File(path));
                }

            }

        }
        long endTime = System.currentTimeMillis();
        System.out.println("方法三的运行时间：" + String.valueOf(endTime - startTime) + "ms");
        return "redirect:/success.jsp";
    }


}
