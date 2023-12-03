package Account.FlightInformation;

public class FromCity extends Account.Information {

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
