package Account.FlightInformation;
import java.time.LocalDate;

public class DepartDate extends Account.Information {
    public DepartDate(LocalDate info) {
        setInfo(info.toString());
        checkValid();
    }

    public void checkValid() {
        setValid(true);
    }
}
