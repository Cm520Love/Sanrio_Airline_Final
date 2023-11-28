package userInformation.flightInformation;
import userInformation.UserInformation;
public class FromCity extends UserInformation {

    public FromCity(String info) {
        setInfo(info);
        setErrorMsg("Select a city");
        checkValid();
    }

    public void checkValid() {
        if (getInfo() != null) {
            setValid(true);
        }
    }
}
