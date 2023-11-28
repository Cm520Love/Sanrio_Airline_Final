package userInformation.userProfile;
import userInformation.UserInformation;

public class Admin extends UserInformation {
    private static final int ADMIN_CODE = 4867;
    int providedCode;

    public Admin(String providedCode) {
        setErrorMsg("Incorrect Admin Code");
        setInfo("Admin");
        try {
            this.providedCode = Integer.parseInt(providedCode);
        } catch (NumberFormatException ex) {
            this.providedCode = 0;
        } finally {
            checkValid();
        }

    }

    @Override
    public void checkValid() {
        if (providedCode == ADMIN_CODE) {
            setValid(true);
        }
    }

}
