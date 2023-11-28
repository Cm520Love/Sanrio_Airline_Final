package userInformation.userProfile;
import userInformation.UserInformation;
public class FirstName extends UserInformation {

    public FirstName(String info) {
        setErrorMsg("Invalid First Name");
        setInfo(info);
        checkValid();
    }

    @Override
    public void checkValid() {
        if (getInfo().matches("[a-zA-z]{3,}")) {
            setValid(true);
        }
    }
}
