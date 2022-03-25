package com.osc.ecommerce.core.adapters.abstracts;

import com.osc.ecommerce.core.utilities.results.DataResult;
import org.springframework.web.multipart.MultipartFile;

public interface ImageUploadService {

    DataResult<String> upload(MultipartFile file);

}
