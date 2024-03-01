package com.example.exceltospring.service;

import com.example.exceltospring.entity.User;
import com.example.exceltospring.repository.UserRepo;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.IOException;

@Service
public class DatabaseService {

    @Autowired
    private UserRepo userRepository;

    @Transactional
    public void importExcelToDatabase(String excelFilePath) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(excelFilePath);
        Workbook workbook = new XSSFWorkbook(fileInputStream);
        Sheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet) {

            String id = row.getCell(0,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
            String firstName = row.getCell(1,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
            String lastName = row.getCell(2,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
            String band = row.getCell(3,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();
            String rating = row.getCell(4,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue();

            // Create a new User entity and set its properties from the Excel row
            User user = new User();
            user.setId(id);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setBand(band);
            user.setRating(rating);


            // Set other properties...

            // Save the entity to the database
            userRepository.save(user);
        }

        workbook.close();
        fileInputStream.close();
    }
}

