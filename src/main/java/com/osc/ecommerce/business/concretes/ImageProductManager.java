package com.osc.ecommerce.business.concretes;

import com.osc.ecommerce.business.abstracts.ImageProductService;
import com.osc.ecommerce.business.abstracts.ProductService;
import com.osc.ecommerce.core.adapters.abstracts.ImageUploadService;
import com.osc.ecommerce.core.utilities.results.DataResult;
import com.osc.ecommerce.core.utilities.results.ErrorResult;
import com.osc.ecommerce.core.utilities.results.Result;
import com.osc.ecommerce.core.utilities.results.SuccessResult;
import com.osc.ecommerce.dal.abstracts.ImageProductDao;
import com.osc.ecommerce.entities.concretes.ImageProduct;
import com.osc.ecommerce.entities.concretes.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageProductManager implements ImageProductService {

    private final ImageProductDao imageProductDao;
    private final ProductService productService;
    private final ImageUploadService imageUploadService;

    @Override
    public Result save(MultipartFile image, int productId) {
        if(image.isEmpty()) {
            return new ErrorResult("Image is required!");
        }
        DataResult<Product> product = productService.getById(productId);
        if(!product.isSuccess() || product.getData() == null) {
            return new ErrorResult("Product not found!");
        }
        DataResult<String> result = imageUploadService.uploadImageProduct(image);
        if(!result.isSuccess() || result.getData() == null) {
            return new ErrorResult("Failed to upload!");
        }
        ImageProduct imageProduct = new ImageProduct();
        imageProduct.setUrl(result.getData());
        imageProduct.setProduct(product.getData());
        imageProductDao.save(imageProduct);
        return new SuccessResult("ImageProduct saved.");
    }

}
