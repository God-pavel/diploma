package com.studying.diploma.controller;

import com.studying.diploma.dto.ProductDTO;
import com.studying.diploma.model.Product;
import com.studying.diploma.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final MessageSource messageSource;

    @Autowired
    public ProductController(ProductService productService, MessageSource messageSource) {
        this.productService = productService;
        this.messageSource = messageSource;
    }

    @GetMapping
    public String allProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "products";
    }

    @GetMapping("{product}")
    public String productEditPage(@PathVariable Product product, Model model) {
        model.addAttribute("product", product);
        return "product_edit";
    }

    @PostMapping
    public String productEdit(@RequestParam("productId") Product product,
                              Model model,
                              ProductDTO productDTO) {
        if (!productService.editProduct(product, productDTO)) {
            model.addAttribute("message", messageSource.getMessage("message.exist.product", null, LocaleContextHolder.getLocale()));
            return productEditPage(product, model);
        }

        return "redirect:/products";
    }

    @GetMapping("add")
    public String addProductPage() {
        return "product_add";
    }

    @PostMapping("add")
    public String addProduct(ProductDTO productDTO, Model model) {
        if (!productService.saveProduct(productDTO)) {
            model.addAttribute("message", messageSource.getMessage("message.exist.product", null, LocaleContextHolder.getLocale()));
            return "product_add";
        }

        return "redirect:/products";
    }

}