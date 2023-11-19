package com.capitole.inditex.v1.matcher;

import com.capitole.inditex.v1.entity.Price;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.Objects;

/**
 * TypeSafe matcher for {@link Price}
 *
 * @author Matias Scalerandi
 */
public class PriceMatcher extends TypeSafeMatcher<Price> {
    private final Price expected;

    /**
     * Constructor.
     *
     * @param expected {@link Price}
     */
    public PriceMatcher(Price expected) {
        this.expected = expected;
    }

    /**
     * @see TypeSafeMatcher#matchesSafely(Object)
     */
    @Override
    protected boolean matchesSafely(Price actual) {
        return Objects.equals(actual.getProductId(), expected.getProductId())
                && Objects.equals(actual.getPriceListId(), expected.getPriceListId())
                && Objects.equals(actual.getFinalPrice(), expected.getFinalPrice())
                && Objects.equals(actual.isPriority(), expected.isPriority())
                && Objects.equals(actual.getId(), expected.getId())
                && Objects.equals(actual.getCurrency().name(), expected.getCurrency().name())
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
