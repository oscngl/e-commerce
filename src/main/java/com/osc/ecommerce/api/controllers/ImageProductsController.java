package com.osc.ecommerce.api.controllers;

import com.osc.ecommerce.business.abstracts.ImageProductService;
import com.osc.ecommerce.core.utilities.results.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/images/products")
@RequiredArgsConstructor
public class ImageProductsController {

    private final ImageProductService imageProductService;

    @PostMapping("/upload")
    public Result save(@RequestParam("image") MultipartFile image,
                       @RequestParam("product_id") int productId) {
        return imageProductService.save(image, productId);
    }

}
