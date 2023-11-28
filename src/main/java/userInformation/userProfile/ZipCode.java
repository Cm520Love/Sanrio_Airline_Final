package userInformation.userProfile;
import userInformation.UserInformation;
public class ZipCode extends UserInformation {

    public ZipCode(String info) {
        setErrorMsg("Invalid ZipCode");
        setInfo(info);
        checkValid();

    }

    @Override
    public void checkValid() {
        if (getInfo().matches("\\d{5}")) {
            setValid(true);
        }

    }

}
