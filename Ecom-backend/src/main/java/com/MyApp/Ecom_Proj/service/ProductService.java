package com.MyApp.Ecom_Proj.service;

import com.MyApp.Ecom_Proj.model.Product;
import com.MyApp.Ecom_Proj.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

//3
@Service
public class ProductService
{
    //4
    @Autowired
    private ProductRepo repo;

    //5
    public List<Product> getAllProducts()
    {
        return repo.findAll();
    }

    //11/22
    public Product addProduct(Product product, MultipartFile imageFile) throws IOException {
        //extract the image  mata data and convert to byte
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageType(imageFile.getContentType());
        product.setImageData(imageFile.getBytes());
        return repo.save(product);
    }
//    //12
//    public void addProducts(List<Product> products)
//    {
//        repo.saveAll(products);
//    }

    //15
    public Product getProductById(int id)
    {
        //19
        //we should set with status code for proper implimentation
        return repo.findById(id).orElse(null);
    }

    //25
    public Product updateProduct(int id, Product product, MultipartFile imageFile) throws IOException {
        product.setImageName(imageFile.getOriginalFilename());
        product.setImageData(imageFile.getBytes());
        product.setImageType(imageFile.getContentType());
        return repo.save(product);
    }

    //27
    public void deleteProduct(int id)
    {
        repo.deleteById(id);
    }

    //29
    public List<Product> searchProduct(String keyword)
    {
        return repo.searchProducts(keyword);
    }
}
