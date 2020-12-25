package qiuchi.chen.exception;

import java.io.IOException;

class ChainingException extends Throwable {
    public static void main(String[] args) throws ChainingException {
        ChainingException exception = new ChainingException();
        exception.initCause(new MyOwnException().initCause(new IOException()));
        throw exception;
    }
}
