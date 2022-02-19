package pl.javastart.library.Exceptions;

public class PublicationAlreadyExistException extends RuntimeException{
    public PublicationAlreadyExistException(String message) {
        super(message);
    }
}
