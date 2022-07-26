package com.example.demo.service;

import com.example.demo.model.Product;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;

public class ExportBillService extends ExcelUtils {
    public ExportBillService(String sheetName, List<String> listHeaders, Object listData) {
        super(sheetName, listHeaders, listData);
    }

    @Override
    public void writeDataLines() {
        int rowCount = 1;
        CellStyle style = cellStyle();
        CellStyle styleNumber = cellStyleNumber();
        int number = 1;
        List<Product> products = (List<Product>) listData;
        for (Product product : products) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            //no
            createCell(row, columnCount++, number, styleNumber);
            //product name
            createCell(row, columnCount++, product.getProduct_name(), style);
            //product price
            createCell(row, columnCount++, product.getPrice(), styleNumber);
            //product description
            createCell(row, columnCount++, product.getDescription(), style);
            number++;
        }
    }
}
