package com.alesharik.country.provider.hardcoded;

import com.alesharik.country.provider.exception.CountryNotFoundException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HardcodedCountryProviderTest {
    private final HardcodedCountryProvider provider = new HardcodedCountryProvider();

    @Test
    void shouldGetCountries() {
        assertThat(provider.getCountries())
                .hasSize(193);
    }

    @Test
    void shouldGetCountry() {
        assertThat(provider.getCountry("af")).isNotNull();
    }

    @Test
    void shouldGetUppercaseCountry() {
        assertThat(provider.getCountry("AF")).isNotNull();
    }

    @Test
    void shouldThrowExceptionForNotExistentCountry() {
        assertThatThrownBy(() -> provider.getCountry("aa"))
                .isExactlyInstanceOf(CountryNotFoundException.class);
    }

    @Test
    void shouldHasCountry() {
        assertThat(provider.hasCountry("AF")).isTrue();
        assertThat(provider.hasCountry("aa")).isFalse();
    }
}
