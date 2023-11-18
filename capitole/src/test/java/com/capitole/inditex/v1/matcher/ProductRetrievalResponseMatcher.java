package com.capitole.inditex.v1.matcher;

import com.capitole.inditex.v1.model.ProductRetrievalResponse;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.Objects;

/**
 * TypeSafe matcher for {@link ProductRetrievalResponse}
 *
 * @author Matias Scalerandi
 */
public class ProductRetrievalResponseMatcher extends TypeSafeMatcher<ProductRetrievalResponse> {

    private final ProductRetrievalResponse expected;

    /**
     * Constructor.
     *
     * @param expected {@link ProductRetrievalResponse}
     */
    public ProductRetrievalResponseMatcher(ProductRetrievalResponse expected) {
        this.expected = expected;
    }

    /**
     * @see TypeSafeMatcher#matchesSafely(Object)
     */
    @Override
    protected boolean matchesSafely(ProductRetrievalResponse actual) {
        return Objects.equals(actual.getProductId(), expected.getProductId())
                && Objects.equals(actual.getRateToApply(), expected.getRateToApply())
                && Objects.equals(actual.getFinalPrice(), expected.getFinalPrice())
                && Objects.equals(actual.getApplicationEndDate(), expected.getApplicationEndDate())
                && Objects.equals(actual.getBrandId(), expected.getBrandId())
                && Objects.equals(actual.getApplicationStartDate(), expected.getApplicationStartDate());
    }

    /**
     * @see TypeSafeMatcher#describeTo(Description)
     */
    @Override
    public void describeTo(Description description) {
        description.appendText("does not match expected").appendValue(expected);
    }
}
