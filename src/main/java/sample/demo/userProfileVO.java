package sample.demo;
import userInformation.userProfile.*;
import java.util.ArrayList;
import java.util.Arrays;

public class userProfileVO {

    userInformation.UserInformation userType;
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

    //private static HashMap<String, ArrayList<userInformation.UserInformation>> allUsers = new HashMap<>();

    ArrayList<userInformation.UserInformation> getUserInformation() {
        ArrayList<userInformation.UserInformation> userInformation = new ArrayList<>(Arrays.asList(
                userType, firstName, lastName, address, state, zipCode,
                username, password, ssn, email, securityQuestion, securityAnswer
        ));
        return userInformation;
    }

    //public static HashMap<String, ArrayList<userInformation.UserInformation>> getAllUsers() {
        //return allUsers;
    //}

}
