public class ArgException extends Exception{
    public ArgException(String message) {
        super("Error: " + message);
    }
}
