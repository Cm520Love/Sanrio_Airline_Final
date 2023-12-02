package Account.UserInformation;
import SQL.*;
import Interfaces.NotInsideDatabase;

public class Username extends Account.Information implements NotInsideDatabase {

    public Username(String info) {
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
        return NewUser.isNewValue(getInfo(), "Username");
    }

}
