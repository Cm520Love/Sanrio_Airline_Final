package userInformation.userProfile;
import SQL.*;
import Interfaces.NotInsideDatabase;
import userInformation.UserInformation;
public class Username extends UserInformation implements NotInsideDatabase {
    String userType;
    public Username(String info, UserInformation userType) {
        this.userType = userType.getInfo();
        setInfo(info);
        checkValid();
    }
    @Override
    public void checkValid() {
        if (getInfo().matches("[a-zA-Z]{3,}[\\d]*") & isNew()) {
            setValid(true);
        } else if (!getInfo().matches("[a-zA-Z]{3,}[\\d]*")) {
            setErrorMsg("Invalid Username");
        } else if (!isNew()){
            setErrorMsg("Username already exists");
        }


    }

    @Override
    public boolean isNew() {
        return Access.isNewValue(getInfo(), "Username", userType);
    }

}
