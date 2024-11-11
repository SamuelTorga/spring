package br.com.samueltorga.spring.exceptions;

public class MaxPageSizeException extends RuntimeException {
    public MaxPageSizeException() {
        super("Max page size exceeds the limit");
    }
}
