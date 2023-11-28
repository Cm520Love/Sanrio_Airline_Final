package userInformation.userProfile;
import userInformation.UserInformation;
public class Password extends UserInformation {
    public Password(String info) {
        setErrorMsg("Invalid password");
        setInfo(info);
        checkValid();
    }

    @Override
    public void checkValid() {
        if (getInfo().matches("[a-zA-Z]{5,}[\\d]{1,}")) {
            setValid(true);
        }
    }
}
