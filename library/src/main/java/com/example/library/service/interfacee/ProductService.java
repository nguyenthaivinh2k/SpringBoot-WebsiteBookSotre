package com.example.library.service.interfacee;

import com.example.library.dto.ProductDTO;
import com.example.library.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    List<ProductDTO>  findAll();
    Product save(MultipartFile imageProduct, ProductDTO productDTO);

    Product update(MultipartFile imageProduct, ProductDTO productDTO);

    void deleted(Long id);


    void finalDeleteItems(List<Long> itemIds);

    void deleteImages(List<Product> productList);

    void enable(Long id);

    ProductDTO getById(Long id);

    Page<Product> pageProduct(int pageNo);
    Page<Product> serachProduct(int pageNum, String keyWord);
    int existName(String name);
    // Customer
    List<Product> getAllProducts();
//    List<Product> listViewProducts();

    List<Product> getReletedProducts(Long id);

    Product getProductById(Long id);

    // customer
    List<Product> getProductsInCategory(Long categoryId);

    List<Product> filterHightPrice();

    List<Product> filterLowPrice();
}
