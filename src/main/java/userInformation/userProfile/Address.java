package userInformation.userProfile;
import userInformation.UserInformation;

public class Address extends UserInformation {
    public Address(String info) {
        setErrorMsg("Invalid Address");
        setInfo(info);
        checkValid();
    }

    @Override
    public void checkValid() {
        if (getInfo().matches("[\\d]{1,}\\s[a-zA-Z\\s]{3,}")) {
            setValid(true);
        }
    }

}


