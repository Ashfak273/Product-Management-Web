package com.MyApp.Ecom_Proj.controller;

import com.MyApp.Ecom_Proj.model.Product;
import com.MyApp.Ecom_Proj.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

//6
@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductController
{
    //7
    //constructor injection
    @Autowired
    private ProductService service;

    //8
    //17
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts()
    {
        return new ResponseEntity<>(service.getAllProducts(), HttpStatus.OK);
    }

    //9/21 add one product , ResponseEntity<?> is used to return any type of response
    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product,
                                        @RequestPart MultipartFile imageFile)
    {
        try
        {
            Product product1 = service.addProduct(product, imageFile);
            return new ResponseEntity<>(product1, HttpStatus.CREATED);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //23
    @GetMapping("/product/{id}/image")
    public ResponseEntity<byte[]> getImageById(@PathVariable int id)
    {
       Product product = service.getProductById(id);
       byte[] imageFile = product.getImageData();

       return ResponseEntity.ok()
               .contentType(MediaType.valueOf(product.getImageType()))
               .body(imageFile);
    }


//    //10 add multiple products List
//    @PostMapping("/all/products")
//    public void addProducts(@RequestBody List<Product> products)
//    {
//        service.addProducts(products);
//    }

    //15
    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id)
    {
        ///18
        Product product = service.getProductById(id);
        if (product != null)
        {
            return new ResponseEntity<>(product, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //24
    @PutMapping("/product/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable int id, @RequestPart Product product,
                                                @RequestPart MultipartFile imageFile)
    {
        Product product1 = null;
        try
        {
            product1 = service.updateProduct(id, product, imageFile);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        if (product1 != null)
        {
            return new ResponseEntity<>("Product updated successfully", HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }
    }

    //26
    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id)
    {
        Product product = service.getProductById(id);
        if (product != null)
        {
            service.deleteProduct(id);
            return new ResponseEntity<>("Product deleted successully", HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }
    }

    //28
    @GetMapping("/products/search")
    public ResponseEntity<List<Product>> searchProduct(@RequestParam String keyword)
    {
        List<Product> products = service.searchProduct(keyword);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
