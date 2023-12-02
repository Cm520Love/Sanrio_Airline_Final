package Account.FlightInformation;

public class TripType extends Account.Information {

    public TripType(String type) {
        setInfo(type);
        checkValid();
    }

    public void checkValid() { setValid(true); }
}
