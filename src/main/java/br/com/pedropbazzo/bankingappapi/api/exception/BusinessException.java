package br.com.limaisaias.bankingappapi.api.exception;

public class BusinessException extends RuntimeException {
    public BusinessException(String s) {
        super(s);
    }
}
