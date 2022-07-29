package com.example.demo.controller;

import com.example.demo.common.Pagination;
import com.example.demo.common.ReponseData;
import com.example.demo.common.ResponseDataPagination;
import com.example.demo.contance.PagingConstants;
import com.example.demo.model.Product;
import com.example.demo.model.ProductReponse;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private ProductService productService;

    @Autowired
    private ClienProductService clienProductService;

    @Autowired
    SequenceGeneratorService sequenceGeneratorService;

    @GetMapping("/product")
    public ResponseEntity<List<Product>> getAllProduct() {
        List<Product> products = productService.getList();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable(value = "id") long id) {
        Product product = productService.getProductById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping("/product")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        product.setId(sequenceGeneratorService.generateSequence(product.SEQUENCE_NAME));
        return new ResponseEntity<>(productService.save(product), HttpStatus.CREATED);
    }


    @GetMapping("/export")
    public ResponseEntity<Resource> exportListProduct() {
        try {
            List<String> listHeader = Arrays.asList("No", "productName", "price", "description");
            DateFormat dateFormatter = new SimpleDateFormat("ddMMyy");
            String currentDateTime = dateFormatter.format(new Date());
            List<Product> productList = productService.getList();
            ExcelUtils excelUtils = new ExportBillService("danhsachProduct" + currentDateTime, listHeader, productList);
            excelUtils.export();
            ByteArrayResource resource = new ByteArrayResource(excelUtils.exportExcel());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=Danhsachproduct_" + currentDateTime + ".xlsx")
                    .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    .body(resource);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


    @GetMapping("/page")
    public ReponseData getListProductList(@RequestParam(value = "page", required = false, defaultValue = PagingConstants.PAGE_DEFAULT) int page,
                                          @RequestParam(value = "size", required = false, defaultValue = PagingConstants.SIZE_DEFAULT) int size) {

        ResponseDataPagination responseDataPagination = new ResponseDataPagination();
        Pagination pagination = new Pagination();
        try {
            int pageReq = page >= 1 ? page - 1 : page;
            Pageable pageable = PageRequest.of(pageReq, size);
            Page<ProductReponse> productReponsePage = clienProductService.getListProductPage(pageable);
            responseDataPagination.setData(productReponsePage.getContent());
            pagination.setCurrentPage(page);
            pagination.setPageSize(size);
            pagination.setTotalPage(productReponsePage.getTotalPages());
            pagination.setTotalRecords(productReponsePage.getTotalElements());
            responseDataPagination.setPagination(pagination);
            responseDataPagination.setStatus("success");
            return responseDataPagination;
        } catch (Exception ex) {
            responseDataPagination.setStatus("error");
            return responseDataPagination;
        }
    }

    @PutMapping("/product/{id}")
    public ReponseData updateProduct(@PathVariable(value = "id", required = false) long id,
                                     @RequestBody Product product) {
        ReponseData reponseData = new ReponseData();
        boolean update = productService.updateProduct(id, product);
        if (update) {
            reponseData.setStatus("success");
        } else {
            reponseData.setStatus("error");
        }
        return reponseData;
    }


}
