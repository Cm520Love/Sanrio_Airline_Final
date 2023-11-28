package userInformation.flightInformation;
import java.time.LocalDate;
import userInformation.UserInformation;
public class DepartDate extends UserInformation {
    public DepartDate(LocalDate info) {
        setInfo(info.toString());
        checkValid();
    }

    public void checkValid() {
        setValid(true);
    }
}
