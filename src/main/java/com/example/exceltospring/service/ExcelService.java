package com.example.exceltospring.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class ExcelService {

    public void combineExcelFilesSideBySide(String file1Path, String file2Path, String outputFile) throws IOException {
        // Open the first Excel file
        FileInputStream file1InputStream = new FileInputStream(file1Path);
        Workbook workbook1 = new XSSFWorkbook(file1InputStream);

        // Open the second Excel file
        FileInputStream file2InputStream = new FileInputStream(file2Path);
        Workbook workbook2 = new XSSFWorkbook(file2InputStream);

        // Create a new workbook for the output
        Workbook outputWorkbook = new XSSFWorkbook();

        // Assume both Excel files have only one sheet; adjust as needed
        Sheet sheet1 = workbook1.getSheetAt(0);
        Sheet sheet2 = workbook2.getSheetAt(0);
        Sheet outputSheet = outputWorkbook.createSheet("Merged Data");

        // Calculate the number of columns in the first sheet to determine the starting column for the second sheet
        int lastColumn = getMaxColumn(sheet1);

        // Copy rows and cells from the first sheet to the output sheet
        copyRows(sheet1, outputSheet, 0, 0);

        // Copy rows and cells from the second sheet to the output sheet, starting from the column right after the last column of the first sheet
        copyRows(sheet2, outputSheet, lastColumn, 0);

        // Write the output to a file
        FileOutputStream outputStream = new FileOutputStream(outputFile);
        outputWorkbook.write(outputStream);

        // Close all resources
        file1InputStream.close();
        file2InputStream.close();
        outputStream.close();
        workbook1.close();
        workbook2.close();
        outputWorkbook.close();
    }


    private void copyRows(Sheet sourceSheet, Sheet destinationSheet, int startColumn, int startRow) {
        for (int i = sourceSheet.getFirstRowNum(); i <= sourceSheet.getLastRowNum(); i++) {
            Row sourceRow = sourceSheet.getRow(i);
            Row destinationRow = destinationSheet.getRow(i + startRow);
            if (destinationRow == null) {
                destinationRow = destinationSheet.createRow(i + startRow);
            }

            if (sourceRow != null) {
                for (int j = sourceRow.getFirstCellNum(); j < sourceRow.getLastCellNum(); j++) {
                    Cell sourceCell = sourceRow.getCell(j);
                    Cell destinationCell = destinationRow.createCell(j + startColumn);

                    if (sourceCell != null) {
                        // Copy the cell value. More complex copying (styles, types, etc.) would require additional logic.
                        destinationCell.setCellValue(sourceCell.toString());
                    }
                }
            }
        }
    }

    private int getMaxColumn(Sheet sheet) {
        int maxColumn = 0;
        for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row != null && row.getLastCellNum() > maxColumn) {
                maxColumn = row.getLastCellNum();
            }
        }
        return maxColumn;
    }
}
