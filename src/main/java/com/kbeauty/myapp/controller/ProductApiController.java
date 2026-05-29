package com.kbeauty.myapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kbeauty.myapp.entity.DTO;
import com.kbeauty.myapp.entity.Product;
import com.kbeauty.myapp.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductApiController {
	@Autowired
    private ProductService productService;

    @GetMapping("/all")
    public List<Product> getAllList() {
    	System.out.println("all 요청 ++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        // [REQ-04] Read-Replica 환경에서 이 API가 호출되도록 구성하면 성능이 최적화됩니다.
    	System.out.println(productService.getAllProducts().toString());
        return productService.getAllProducts();
    }

 // 상세페이지용 API
    @GetMapping("/{id}")
    public DTO getProductDetail(@PathVariable Long id) {
    	System.out.println("detail 요청 ++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    	System.out.println(productService.getProductDetail(id).toString());
        return productService.getProductDetail(id);
    }
}