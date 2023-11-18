package com.capitole.inditex.v1.matcher;

import com.capitole.inditex.v1.model.ProductItem;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.Objects;

/**
 * TypeSafe matcher for {@link ProductItem}
 *
 * @author Matias Scalerandi
 */
public class ProductItemMatcher extends TypeSafeMatcher<ProductItem> {
    private final ProductItem expected;

    /**
     * Constructor.
     *
     * @param expected {@link ProductItem}
     */
    public ProductItemMatcher(ProductItem expected) {
        this.expected = expected;
    }

    /**
     * @see TypeSafeMatcher#matchesSafely(Object)
     */
    @Override
    protected boolean matchesSafely(ProductItem actual) {
        return Objects.equals(actual.getProductId(), expected.getProductId())
                && Objects.equals(actual.getRateToApply(), expected.getRateToApply())
                && Objects.equals(actual.getFinalPrice(), expected.getFinalPrice())
                && Objects.equals(actual.getBrandId(), expected.getBrandId());
    }

    /**
     * @see TypeSafeMatcher#describeTo(Description)
     */
    @Override
    public void describeTo(Description description) {
        description.appendText("does not match expected").appendValue(expected);
    }
}
