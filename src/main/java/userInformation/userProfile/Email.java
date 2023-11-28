package userInformation.userProfile;
import userInformation.UserInformation;
public class Email extends UserInformation {

    public Email(String info) {
        setErrorMsg("Invalid email");
        setInfo(info);
        checkValid();
    }

    @Override
    public void checkValid() {
        if (getInfo().matches("[a-z\\d]{5,}[@][a-z\\d]{4,}[.]com")) {
            setValid(true);
        }
    }
}
