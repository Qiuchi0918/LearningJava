package qiuchi.chen.exception;

class MyOwnException extends Throwable {

    @Override
    public String getMessage() {
        return "Custom Message," + super.getMessage();
    }
}

