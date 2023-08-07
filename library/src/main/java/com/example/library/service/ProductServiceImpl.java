package com.example.library.service;

import com.example.library.ClassOfConst.Constant;
import com.example.library.dto.ProductDTO;
import com.example.library.model.Product;
import com.example.library.repository.ProductRepository;
import com.example.library.service.interfacee.ProductService;
import com.example.library.utils.ImageUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository repository;
    @Autowired
    ImageUpload imageUpload;

    Sort desc = Sort.by(Sort.Direction.DESC, "product_id");
//    Sort sortByIdAndStatus = Sort.by(
//            Sort.Order.desc("id"),
//            Sort.Order.desc("status")
//    );

    public String formatPrice(double price) {
        Locale vietnamLocale = new Locale("vi", "VN");
        DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(vietnamLocale);
        decimalFormat.applyPattern("#,###");
        String formattedValue = decimalFormat.format(price);
        return formattedValue;
    }

    @Override
    public List<ProductDTO> findAll() {
        List<ProductDTO> productDTOList = new ArrayList<>();
        List<Product> products = repository.findAll();
        for (Product product : products) {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(product.getId());
            productDTO.setName(product.getName());
            productDTO.setDescription(product.getDescription());
            productDTO.setCostPrice(product.getCostPrice());
            productDTO.setSalePrice(product.getSalePrice());
            productDTO.setCurrentQuantity(product.getCurrentQuantity());
            productDTO.setCategory(product.getCategory());
            productDTO.setImage(product.getImage());
            productDTO.set_activated(product.is_activated());
            productDTO.set_deleted(product.is_deleted());
            productDTOList.add(productDTO);
        }
        return productDTOList;
    }

    @Override
    public Product save(MultipartFile imageProduct, ProductDTO productDTO) {
        try {
            Product product = new Product();
            if (imageProduct.isEmpty()) {
                product.setImage("productDefault.jpg");
            } else {
                imageUpload.uploadImage(imageProduct);
//                product.setImage(Base64.getEncoder().encodeToString(imageProduct.getBytes()));
                product.setImage(imageProduct.getOriginalFilename());
            }
            product.setName(productDTO.getName());
            product.setDescription(productDTO.getDescription());
            product.setCategory(productDTO.getCategory());
            product.setCostPrice(productDTO.getCostPrice());
            product.setSalePrice(productDTO.getSalePrice());
            product.setCurrentQuantity(productDTO.getCurrentQuantity());
            product.set_activated(true);
            product.set_deleted(false);
            return repository.save(product);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Product update(MultipartFile imageProduct, ProductDTO productDTO) {
        try {
            Product product = repository.getById(productDTO.getId());

            if (imageProduct.isEmpty()) {
                product.setImage(product.getImage());
            } else {
                List<Product> deleteImage = new ArrayList<>();
                deleteImage.add(product);
                deleteImages(deleteImage);
                imageUpload.uploadImage(imageProduct);
//                product.setImage(Base64.getEncoder().encodeToString(imageProduct.getBytes()));
                product.setImage(imageProduct.getOriginalFilename());
            }
            product.setName(productDTO.getName());
            product.setDescription(productDTO.getDescription());
            product.setCategory(productDTO.getCategory());
            product.setCurrentQuantity(productDTO.getCurrentQuantity());
            product.setSalePrice(productDTO.getSalePrice());
            product.setCostPrice(productDTO.getCostPrice());
            return repository.save(product);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleted(Long id) {
        Product product = repository.getById(id);
        product.set_deleted(true);
        product.set_activated(false);
        repository.save(product);
    }

    @Override
    public void finalDeleteItems(List<Long> itemIds) {
        List<Product> productList = new ArrayList<>();
        productList = repository.findAllById(itemIds);
//        List<ProductDTO> products = repository.findAllById(itemIds);
//        for (ProductDTO product : products) {
//            ProductDTO productDTO = new ProductDTO();
//            productDTO.setImage(product.getImage());
//            productDTOList.add(productDTO);
//        }
        try {
            repository.deleteAllByIdInBatch(itemIds);
            deleteImages(productList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteImages(List<Product> productList) {
        try {
            for (int i = 0; i < productList.size(); i++) {
                if (productList.get(i).getImage().equals(Constant.NAME_OF_IMAGE_PRODUCT_DEFAULT)) continue;
                Path filePath = Paths.get(Constant.UPLOAD_FOLDER + "/" + productList.get(i).getImage());
                Path filePath2 = Paths.get(Constant.UPLOAD_FOLDER_2 + "/" + productList.get(i).getImage());
                Files.deleteIfExists(filePath);
                Files.deleteIfExists(filePath2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void enable(Long id) {
        Product product = repository.getById(id);
        product.set_deleted(false);
        product.set_activated(true);
        repository.save(product);
    }

    @Override
    public ProductDTO getById(Long id) {
        Product product = repository.getById(id);
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setImage(product.getImage());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setCategory(product.getCategory());
        productDTO.setCurrentQuantity(product.getCurrentQuantity());
        productDTO.setSalePrice(product.getSalePrice());
        productDTO.setCostPrice(product.getCostPrice());
        productDTO.set_activated(product.is_activated());
        productDTO.set_deleted(product.is_deleted());
        return productDTO;
    }

    @Override
    public Page<Product> pageProduct(int pageNum) {
        Pageable pageable = PageRequest.of(pageNum - 1, Constant.PRODUCT_PER_PAGE);
        return repository.pageProduct(pageable);
    }

    @Override
    public Page<Product> serachProduct(int pageNum, String keyWord) {
        Pageable pageable = PageRequest.of(pageNum - 1, Constant.PRODUCT_PER_PAGE);
        Page<Product> page = repository.searchProduct(keyWord, pageable);
        return page;
    }
    @Override
    public int existName(String name) {
        List<Product> list = repository.findAll();
        int count = 0;
        for (Product product : list) {
            if (product.getName().equalsIgnoreCase(name))
                count++;
        }
//        System.out.println(count); // khi update thì bằng 2 thì trùng với sản phẩm khác vì nó tính cả nó nữa
        // khi tạo mới thì bằng 1 là trùng
        return count;
    }

    @Override
    public List<Product> getAllProducts() {
        return repository.getAllProducts();
    }

//    @Override
//    public List<Product> listViewProducts() {
//        return repository.listViewProducts();
//    }

    @Override
    public List<Product> getReletedProducts(Long id) {
        return repository.getReletedProducts(id);
    }

    @Override
    public Product getProductById(Long id) {
        return repository.getById(id);
    }

    @Override
    public List<Product> getProductsInCategory(Long categoryId) {
        return repository.getProductsInCategory(categoryId);
    }@Override
    public List<Product> filterHightPrice(){
        return repository.filterHightPrice();
    }@Override
    public List<Product> filterLowPrice(){
        return repository.filterLowPrice();
    }
}
