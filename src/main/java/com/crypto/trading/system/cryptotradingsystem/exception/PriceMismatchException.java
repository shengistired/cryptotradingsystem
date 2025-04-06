package com.crypto.trading.system.cryptotradingsystem.exception;

public class PriceMismatchException extends RuntimeException{
    public PriceMismatchException(String value){
        super(value);
    }
}
