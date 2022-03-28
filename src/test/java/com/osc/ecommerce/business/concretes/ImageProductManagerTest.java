package com.osc.ecommerce.business.concretes;

import com.osc.ecommerce.business.abstracts.ProductService;
import com.osc.ecommerce.core.adapters.abstracts.ImageUploadService;
import com.osc.ecommerce.core.utilities.results.Result;
import com.osc.ecommerce.core.utilities.results.SuccessDataResult;
import com.osc.ecommerce.dal.abstracts.ImageProductDao;
import com.osc.ecommerce.entities.concretes.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ImageProductManagerTest {

    private ImageProductManager imageProductManager;

    @Mock
    private ImageProductDao imageProductDao;

    @Mock
    private ProductService productService;

    @Mock
    private ImageUploadService imageUploadService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        imageProductManager = new ImageProductManager(
                imageProductDao,
                productService,
                imageUploadService
        );
    }

    @Test
    void canSave() {

        MultipartFile image = new MockMultipartFile("name", new byte[]{1});
        int productId = 1;

        given(productService.getById(productId)).willReturn(new SuccessDataResult<>(new Product(), null));
        given(imageUploadService.uploadImageProduct(image)).willReturn(new SuccessDataResult<>("url", null));

        Result expected = imageProductManager.save(image, productId);

        assertThat(expected.isSuccess()).isTrue();

    }

}