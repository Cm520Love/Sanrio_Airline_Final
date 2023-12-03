package Account.UserInformation;

public class Password extends Account.Information {
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
