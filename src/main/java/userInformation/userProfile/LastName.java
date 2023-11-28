package userInformation.userProfile;
import userInformation.UserInformation;
public class LastName extends UserInformation {
    public LastName(String info) {
        setErrorMsg("Invalid Last Name");
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
