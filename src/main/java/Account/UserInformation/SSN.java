package Account.UserInformation;
import SQL.*;

public class SSN extends Account.Information implements Interfaces.NotInsideDatabase {

    public SSN(String info) {
        setInfo(info);
        try {
            formatSSN();
            checkValid();
        } catch (Exception ex) {
            setErrorMsg("Invalid SSN");
        }

    }

    @Override
    public void checkValid() {
        if (getInfo().matches("[\\d]{3}[-]?[\\d]{2}[-]?[\\d]{4}") && isNew()) {
            setValid(true);
        } else if (!getInfo().matches("[\\d]{3}[-]?[\\d]{2}[-]?[\\d]{4}")) {
            setErrorMsg("Invalid SSN");
        } else if (!isNew()){
            setErrorMsg("Account already exists with SSN");
        }
    }

    @Override
    public boolean isNew() {
        return NewUser.isNewValue(getInfo(), "SSN");
    }

    private void formatSSN() {
        if (!getInfo().contains("-")) {
            StringBuilder ssn = new StringBuilder(getInfo());
            ssn.insert(3, "-");
            ssn.insert(6, "-");
            setInfo(ssn.toString());
        }
    }

}
