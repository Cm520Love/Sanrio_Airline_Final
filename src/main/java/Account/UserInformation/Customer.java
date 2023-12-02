package Account.UserInformation;

public class Customer extends Account.Information {

    public Customer() {
        setInfo("Customer");
        checkValid();
    }

    @Override
    public void checkValid() {
        setValid(true);
    }


}
