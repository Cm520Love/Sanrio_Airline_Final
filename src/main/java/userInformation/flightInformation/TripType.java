package userInformation.flightInformation;
import userInformation.UserInformation;
public class TripType extends UserInformation {

    public TripType(String type) {
        setInfo(type);
        checkValid();
    }

    public void checkValid() { setValid(true); }
}
