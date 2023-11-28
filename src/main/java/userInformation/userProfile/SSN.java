package userInformation.userProfile;
import SQL.*;
import userInformation.UserInformation;
public class SSN extends UserInformation implements Interfaces.NotInsideDatabase {
    String userType;
    public SSN(String info, UserInformation userType) {
        setInfo(info);
        this.userType = userType.getInfo();
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
        return Access.isNewValue(getInfo(), "SSN", userType);
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
