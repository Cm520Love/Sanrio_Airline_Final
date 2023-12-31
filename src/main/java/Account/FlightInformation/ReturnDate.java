package Account.FlightInformation;
import java.time.LocalDate;

public class ReturnDate extends Account.Information {
    LocalDate testDate;
    TripType type;
    public ReturnDate(LocalDate info, TripType type) {
        testDate = info;
        this.type = type;
        setErrorMsg("Please select a return date");
        checkValid();
    }

    public void checkValid() {
        if (type.getInfo().equals("One-Way")) {
            setValid(true);
        } else if (testDate == null) {
            setValid(false);
        } else {
            setInfo(testDate.toString());
            setValid(true);
        }
    }
}
