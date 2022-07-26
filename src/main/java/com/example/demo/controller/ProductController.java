package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepositoryIMPL;
import com.example.demo.service.ExcelUtils;
import com.example.demo.service.ExportBillService;
import com.example.demo.service.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class ProductController {

    @Autowired
    ProductRepositoryIMPL productRepositoryIMPL;
    @Autowired
    SequenceGeneratorService sequenceGeneratorService;

    @GetMapping("/product")
    public ResponseEntity<List<Product>> getAllProduct() {
        List<Product> products = productRepositoryIMPL.getAll();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable(value = "id") long id) {
        Product product = productRepositoryIMPL.getById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping("/product")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        product.setId(sequenceGeneratorService.generateSequence(product.SEQUENCE_NAME));
        return new ResponseEntity<>(productRepositoryIMPL.insert(product), HttpStatus.CREATED);
    }


    @GetMapping("/export")
    public ResponseEntity<Resource> exportListProduct() {
        try {
            List<String> listHeader = Arrays.asList("No", "productName", "price", "description");
            DateFormat dateFormatter = new SimpleDateFormat("ddMMyy");
            String currentDateTime = dateFormatter.format(new Date());
            List<Product> productList = productRepositoryIMPL.getAll();
            ExcelUtils excelUtils = new ExportBillService("danhsachProduct" + currentDateTime, listHeader, productList);
            excelUtils.export();
            ByteArrayResource resource = new ByteArrayResource(excelUtils.exportExcel());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=Danhsachproduct_" + currentDateTime + ".xlsx")
                    .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    .body(resource);
        }catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
