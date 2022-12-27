package com.alesharik.currency.provider;

import com.alesharik.currency.provider.domain.Currency;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * Provides currency info
 */
public interface CurrencyProvider {
    /**
     * Get currency symbol for code
     * @param currencyCode 3-letter code
     * @return currency symbol
     */
    @NonNull
    String getSymbolForCode(@NonNull String currencyCode);

    List<Currency> getCurrencies();
}
