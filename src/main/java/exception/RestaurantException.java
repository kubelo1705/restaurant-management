package exception;

public class RestaurantException extends Exception {
    private String message;

    public RestaurantException(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Exception:" + message;
    }
}
