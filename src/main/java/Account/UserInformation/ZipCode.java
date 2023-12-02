package Account.UserInformation;

public class ZipCode extends Account.Information {

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
