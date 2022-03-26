package com.osc.ecommerce.business.abstracts;

import com.osc.ecommerce.core.utilities.results.Result;
import org.springframework.web.multipart.MultipartFile;

public interface ImageProductService {

    Result save(MultipartFile image, int productId);

}
