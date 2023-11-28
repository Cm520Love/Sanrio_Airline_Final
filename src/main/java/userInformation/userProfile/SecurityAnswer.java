package userInformation.userProfile;
import userInformation.UserInformation;
public class SecurityAnswer extends UserInformation {
    public SecurityAnswer(String info) {
        setErrorMsg("Security answer cannot be blank");
        setInfo(info);
        checkValid();
    }

    public void checkValid() {
        if (getInfo().length() != 0) {
            setValid(true);
        }
    }
}
