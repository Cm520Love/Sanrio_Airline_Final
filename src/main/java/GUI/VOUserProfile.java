package GUI;
import Account.UserInformation.*;
import java.util.ArrayList;
import java.util.Arrays;

public class VOUserProfile {

    FirstName firstName;
    LastName lastName;
    Address address;
    State state;
    ZipCode zipCode;
    Username username;
    Password password;
    SSN ssn;
    Email email;
    SecurityQuestion securityQuestion;
    SecurityAnswer securityAnswer;


    ArrayList<Account.Information> getUserInformation() {
        ArrayList<Account.Information> information = new ArrayList<>(Arrays.asList(
                firstName, lastName, address, state, zipCode,
                username, password, ssn, email, securityQuestion, securityAnswer
        ));
        return information;
    }



}
