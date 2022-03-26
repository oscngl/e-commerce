package com.osc.ecommerce.core.adapters.concretes;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.osc.ecommerce.core.adapters.abstracts.ImageUploadService;
import com.osc.ecommerce.core.utilities.results.DataResult;
import com.osc.ecommerce.core.utilities.results.ErrorDataResult;
import com.osc.ecommerce.core.utilities.results.SuccessDataResult;
import org.cloudinary.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryManager implements ImageUploadService {

    private final Cloudinary cloudinary;

    @Autowired
    public CloudinaryManager(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public DataResult<String> uploadImageProduct(MultipartFile image) {
        try {
            Map uploadResult = cloudinary
                    .uploader()
                    .upload(
                            image.getBytes(),
                            ObjectUtils.asMap(
                                    "folder", "e-commerce.product.photos",
                                    "transformation", new Transformation().height(500).width(500).crop("scale"))
                    );
            if (uploadResult == null) {
                return new ErrorDataResult<>(null, "Failed to upload!");
            }
            JSONObject response = new JSONObject(uploadResult);
            String url = response.getString("url");
            if (url == null) {
                return new ErrorDataResult<>(null, "Failed to get url!");
            }
            return new SuccessDataResult<>(url, null);
        } catch (IOException e) {
            return new ErrorDataResult<>(null, e.getMessage());
        }
    }

}
