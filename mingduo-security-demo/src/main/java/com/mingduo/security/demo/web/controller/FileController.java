package com.mingduo.security.demo.web.controller;

import com.mingduo.security.demo.dto.FileInfo;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 文件管理
 * @author : weizc
 * @description:
 * @since 2019/10/17
 */
@RequestMapping("/file")
@RestController
public class FileController {


    private final static String FOLDER = System.getProperty("user.dir") + "\\src\\main\\java\\com\\mingduo\\security\\demo\\web\\controller";

    private final static String DEMO_FOLDER = System.getProperty("user.dir") + "\\mingduo-security-demo\\src\\main\\java\\com\\mingduo\\security\\demo\\web\\controller";


    @PostMapping
    public FileInfo upload(MultipartFile file) throws IOException {
        System.out.println(file.getName());
        System.out.println(file.getOriginalFilename());
        System.out.println(file.getSize());


        File localFile = new File(FOLDER, System.currentTimeMillis() + ".txt");
        file.transferTo(localFile);

        return new FileInfo(localFile.getAbsolutePath());
    }

    @GetMapping("/{id}")
    public void download(@PathVariable String id, HttpServletResponse response) {
        try (
                InputStream inputStream = new FileInputStream(new File(DEMO_FOLDER, id + ".txt"));
                ServletOutputStream outputStream = response.getOutputStream();
        ) {
            response.setContentType("application/x-download");
            response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=test.txt");

            IOUtils.copy(inputStream, outputStream);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
