package com.example.admin.controller;

import com.example.library.ClassOfConst.Constant;
import com.example.library.dto.ProductDTO;
import com.example.library.model.Category;
import com.example.library.model.Product;
import com.example.library.service.CategoryServiceImpl;
import com.example.library.service.ProductServiceImpl;
import com.example.library.utils.ImageUpload;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

@Controller
public class ProductController {

    @Autowired
    ProductServiceImpl productService;
    @Autowired
    CategoryServiceImpl categoryService;
    @Autowired
    ImageUpload imageUpload;

    @GetMapping("/all-products")
    public String AllProducts(Model model) {
        List<ProductDTO> productDTOList = productService.findAll();
        model.addAttribute("title", "Product Management");
        model.addAttribute("products", productDTOList);
        model.addAttribute("size", productDTOList.size());
        return "all-products";
    }
    @GetMapping("/products")
    public String products(Model model, Principal principal) {
                if (principal == null) {
            return "redirect:/login";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(authority -> authority.equals("ADMIN"));
        if(!isAdmin){
            return "error-role";
        }
        return page(1, model);
    }

    @GetMapping("/products/{pageNum}")
    public String page(@PathVariable("pageNum") int pageNum, Model model) {
//                if (principal == null) {
//            return "redirect:/login";
//        }
        Page<Product> page = productService.pageProduct(pageNum);
        List<Product> productList = page.getContent();
        long startCount = (long) (pageNum - 1) * Constant.PRODUCT_PER_PAGE + 1;
        long endCount = startCount + Constant.PRODUCT_PER_PAGE - 1;
        if (endCount > page.getTotalElements()) {
            endCount = page.getTotalElements();
        }
        model.addAttribute("title", "Product Management");
        model.addAttribute("products", productList);
        model.addAttribute("size", productList.size());
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalProducts", page.getTotalElements());
        model.addAttribute("totalPages", page.getTotalPages());
        return "products";
    }

    @GetMapping("/search-result/{pageNum}")
    public String searchProduct(@PathVariable("pageNum") int pageNum,
                                @RequestParam("keyWord") String keyWord,
                                Model model) {
        Page<Product> page = productService.serachProduct(pageNum, keyWord);
        List<Product> productList = page.getContent();
        long startCount = (long) (pageNum - 1) * Constant.PRODUCT_PER_PAGE + 1;
        long endCount = startCount + Constant.PRODUCT_PER_PAGE - 1;
        if (endCount > page.getTotalElements()) {
            endCount = page.getTotalElements();
        }
        long totalPages = page.getTotalElements();
        if (totalPages == 0) totalPages = 1;
        model.addAttribute("title", "Product Management");
        model.addAttribute("products", productList);
        model.addAttribute("size", productList.size());
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalProducts", totalPages);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("keyWord", keyWord);
        return "result-products";
    }

    @GetMapping("/add-product")
    public String addProduct(Model model) {
        //        if (principal == null) {
//            return "redirect:/login";
//        }
        List<Category> categories = categoryService.findAllByActive();
        model.addAttribute("categories", categories);
        model.addAttribute("product", new Product());
        return "add-product";

    }

    @PostMapping("/save-product")
    public String saveProduct(@ModelAttribute("product") ProductDTO productDTO,
                              @RequestParam("imageProduct") MultipartFile imageProduct,
                              RedirectAttributes attributes, Model model) {
        try {
            List<Category> categories = categoryService.findAllByActive();
            model.addAttribute("categories", categories);
            if (productService.existName(productDTO.getName()) > 0) { // =1, false
                model.addAttribute("failed", "tên đã tồn tại");
                return "add-product";
            } else { // =0, true
                if (imageUpload.isExist(imageProduct)) {
                    model.addAttribute("failed", "ảnh đã tồn tại");
                    return "add-product";
                } else {
                    productService.save(imageProduct, productDTO);
                    attributes.addFlashAttribute("success", "thêm thành công sản phẩm tên " + productDTO.getName());
                }
            }
        } catch (Exception e) {
            attributes.addFlashAttribute("failed", "thêm sản phẩm không thành công ");
            e.printStackTrace();
        }
        return "redirect:/products";
    }

    @GetMapping("/update-product/{id}")
    public String updateProduct(Model model, @PathVariable("id") Long id) {
        //        if (principal == null) {
//            return "redirect:/login";
//        }
        ProductDTO productDTO = productService.getById(id);
        String formattedValue = productService.formatPrice(productDTO.getCostPrice());
       // Định dạng giá trị số với dấu phân tách hàng nghìn
        model.addAttribute("formattedValue", formattedValue);
        model.addAttribute("title", "Edit Product");
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("productDTO", productDTO);
        model.addAttribute("productDTO2", productDTO); // cái này là riêng cái ảnh
        return "update-product";
    }

    @PostMapping("/update-product/{id}")
    public String updateProductFinal(@PathVariable("id") Long id,
                                     @ModelAttribute("productDTO") ProductDTO productDTO,
                                     @RequestParam("imageProduct") MultipartFile imageProduct,
                                     RedirectAttributes attributes, Model model) {
        try {
            ProductDTO productDTO2 = productService.getById(id);
            model.addAttribute("productDTO2", productDTO2); // khi trả về "update-product" vẫn còn hiện ảnh
            List<Category> categories = categoryService.findAll();
            model.addAttribute("categories", categories);
            if (productService.existName(productDTO.getName()) > 1) { // =2 khi để im mà trùng...,=1 khi sửa mà trùng với tên khac false
                model.addAttribute("failed", "cập nhập không thành công, tên đã tồn tại");
                return "update-product";

            } else if( productService.existName(productDTO.getName()) ==1 && !productService.getById(id).getName().equalsIgnoreCase(productDTO.getName())) {
                model.addAttribute("failed", "cập nhập không thành công, tên đã tồn tại");
                return "update-product";
            } else {
                // =0 or =1 khi trùng tên cũ, true bằng 0 là khi đã sửa tên mới k trùng all
                if (imageUpload.isExist(imageProduct)) {
                    model.addAttribute("failed", "cập nhập không thành công, ảnh đã tồn tại");
                    return "update-product";
                } else {
                    productService.update(imageProduct, productDTO);
                    attributes.addFlashAttribute("success", "cập nhật thành công sản phẩm tên " + productDTO.getName());
                }
                model.addAttribute("product", productDTO);
            }
        } catch (Exception e) {
            attributes.addFlashAttribute("failed", "cập nhật thông tin sản phẩm không thành công ");
            e.printStackTrace();
        }
        return "redirect:/products";
    }

    @RequestMapping(value = "/delete-product/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String deleteProduct(@PathVariable("id") Long id, RedirectAttributes attributes) {
        try {
            productService.deleted(id);
            attributes.addFlashAttribute("success", "ẩn thành công sản phẩm tên " + productService.getById(id).getName());

        } catch (Exception e) {
            attributes.addFlashAttribute("failed", "ẩn sản phẩm không thành công ");

            e.printStackTrace();
        }
        return "redirect:/products";
    }

    @RequestMapping(value = "/enable-product/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String enableProduct(@PathVariable("id") Long id, RedirectAttributes attributes) {
        try {
            productService.enable(id);
            attributes.addFlashAttribute("success", "hiện thành công sản phẩm tên " + productService.getById(id).getName());

        } catch (Exception e) {
            attributes.addFlashAttribute("failed", "hiện ản phẩm không thành công ");

            e.printStackTrace();
        }
        return "redirect:/products";
    }

    @GetMapping("/delete")
    // required = false : nếu k có cái này bị lỗi k nhận dạng được type, vì nó check 'url' trước 'data'
    @ResponseBody // phải có @ResponseBody mới chạy ajax thành công, vì đang dùng JSON
                // có cái @ResponseBody này mới dùng bên @Controller được, vì nêu k có buột phải trả về tên file html
                // nêu là method String, nếu k có thì bỏ qua @RestController
    public String finalDelete(
            @RequestParam(value = "jsonList", required = false) String jsonList
    ) {
        Gson gson = new Gson();
        Long[] longArray = gson.fromJson(jsonList, Long[].class);
        List<Long> longList = Arrays.asList(longArray);
//        System.out.println(longList.size());
        try {
            if (longList.size() > 0) {

                productService.finalDeleteItems(longList);
                return "xóa thành công list Id: ";
            } else
                return "Không có sản phẩm nào được chọn";
        } catch (Exception e) {
            e.printStackTrace();
            return "xóa không thành công";
        }

    }
}
