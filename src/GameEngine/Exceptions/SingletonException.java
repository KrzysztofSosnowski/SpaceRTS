package GameEngine.Exceptions;

public class SingletonException extends RuntimeException {

    public SingletonException() {
        super();
    }

    public SingletonException(String message) {
        super(message);
    }
}
