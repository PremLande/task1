package com.example.exceltospring.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
public class FileUploadController {

    @GetMapping("/")
    public String index() {
        return "upload";
    }

    @PostMapping("/upload")
    public String uploadFiles(@RequestParam("files") MultipartFile[] files, RedirectAttributes redirectAttributes) {
        // Process files
        // Redirect to download page or return merged Excel
        return "redirect:/download";
    }
    @PostMapping("/download")
    public void downloadMergedExcel(HttpServletResponse response) throws IOException {
        // Assume `mergedWorkbook` is your merged Excel workbook
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=merged.xlsx");
        //mergedWorkbook.write(response.getOutputStream());
        response.getOutputStream().flush();
    }

}
