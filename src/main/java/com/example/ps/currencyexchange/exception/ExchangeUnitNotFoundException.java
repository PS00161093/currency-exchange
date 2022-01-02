package com.example.ps.currencyexchange.exception;

public class ExchangeUnitNotFoundException extends RuntimeException {
    public ExchangeUnitNotFoundException(String errMsg) {
        super(errMsg);
    }
}
