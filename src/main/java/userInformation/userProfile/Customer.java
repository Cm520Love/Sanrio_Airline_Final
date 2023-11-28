package userInformation.userProfile;
import userInformation.UserInformation;
public class Customer extends UserInformation {

    public Customer() {
        setInfo("Customer");
        checkValid();
    }

    @Override
    public void checkValid() {
        setValid(true);
    }


}
