package com.capitole.inditex.v1.mapper;

import com.capitole.inditex.v1.model.ProductItem;
import com.capitole.inditex.v1.model.ProductRetrievalRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper for {@link ProductRetrievalRequest}
 *
 * @author Matias Scalerandi
 */
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {
    ProductItem requestToProductItem(ProductRetrievalRequest request);
}
