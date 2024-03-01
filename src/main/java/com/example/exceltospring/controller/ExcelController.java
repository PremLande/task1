package com.example.exceltospring.controller;

import com.example.exceltospring.entity.User;
import com.example.exceltospring.repository.UserRepo;
import com.example.exceltospring.service.DatabaseService;
import com.example.exceltospring.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class ExcelController {

    @Autowired
    private ExcelService excelService;
    @Autowired
    private DatabaseService databaseService;

    @Autowired
    private UserRepo repo;

    @GetMapping("/excel")
    public String excelPage(Model model) {
        try {
            excelService.combineExcelFilesSideBySide("/home/root-pc/Downloads/exceltospring/account.xlsx","/home/root-pc/Downloads/exceltospring/MOCK_DATA.xlsx","/home/root-pc/Downloads/exceltospring/combined.xlsx");
            //model.addAttribute("excelData", data);
            databaseService.importExcelToDatabase("/home/root-pc/Downloads/exceltospring/combined.xlsx");
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception
        }
        return "excel";
    }

    @GetMapping("/users")





        public List<User> getAllUsers() {
            return repo.findAll();
        }
    }


