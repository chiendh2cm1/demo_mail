package com.example.demo.controller;

import com.example.demo.common.Pagination;
import com.example.demo.common.ReponseData;
import com.example.demo.common.ResponseDataPagination;
import com.example.demo.contance.PagingConstants;
import com.example.demo.model.Product;
import com.example.demo.model.ProductReponse;
import com.example.demo.service.*;
import org.bouncycastle.asn1.ocsp.ResponseData;
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
import java.util.*;

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
        long a = productService.getTimeEndInDay();
        System.out.println(a);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable(value = "id") long id) {
        Product product = productService.getProductById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping("/product")
    public ReponseData createProduct(@RequestBody Product product) {
        ReponseData reponseData = new ReponseData();
        try {
            product.setId(sequenceGeneratorService.generateSequence(Product.SEQUENCE_NAME));
            reponseData.setData(productService.save(product));
            reponseData.setStatus("success");
        } catch (Exception ex) {
            reponseData.setStatus("error");
        }
        return reponseData;
    }


    @GetMapping("/export")
    public ResponseEntity<Resource> exportListProduct() {
        System.out.println("test lỗi");
        System.out.println("test cuoi");
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
        try {
            boolean update = productService.updateProduct(id, product);
            if (update) {
                reponseData.setStatus("success");
            } else {
                reponseData.setStatus("error");
            }
        } catch (Exception ex) {
            reponseData.setEx("error");
        }
        return reponseData;
    }

    @DeleteMapping("product/{id}")
    public ReponseData deleteProduct(@PathVariable(value = "id", required = false) long id) {
        ReponseData reponseData = new ReponseData();
        try {
            productService.deleleProduct(id);
            reponseData.setStatus("success");
        } catch (Exception ex) {
            reponseData.setEx("error");
        }
        return reponseData;
    }
}
