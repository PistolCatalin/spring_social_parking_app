package social.parking.app.exceptions;

public class SocialParkingAppException extends RuntimeException{
    public SocialParkingAppException(String exMessage, Exception exception) {
        super(exMessage, exception);
    }

    public SocialParkingAppException(String exMessage) {
        super(exMessage);
    }
}
