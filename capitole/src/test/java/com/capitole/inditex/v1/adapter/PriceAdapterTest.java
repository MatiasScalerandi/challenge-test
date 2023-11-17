package com.capitole.inditex.v1.adapter;

import com.capitole.inditex.v1.mapper.ProductMapper;
import com.capitole.inditex.v1.model.ProductItem;
import com.capitole.inditex.v1.model.ProductRetrievalRequest;
import com.capitole.inditex.v1.model.ProductRetrievalResponse;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;

@ExtendWith(MockitoExtension.class)
 class PriceAdapterTest {
    @InjectMocks
    private PriceAdapter tested;

    @Mock
    private ProductMapper productMapper;

//    @BeforeEach
//    void init(@Mock SettingRepository settingRepository) {
//        userService = new DefaultUserService(userRepository, settingRepository, mailClient);
//
//        lenient().when(settingRepository.getUserMinAge()).thenReturn(10);
//
//        when(settingRepository.getUserNameMinLength()).thenReturn(4);
//
//        lenient().when(userRepository.isUsernameAlreadyExists(any(String.class)))
//                .thenReturn(false);
//    }

    @Test
    void givenValidUser_whenSaveUser_thenSucceed() {
        ProductItem request = new ProductItem();
       ProductRetrievalResponse response = tested.toApiProductRetrievalResponse(request);
    }

}
