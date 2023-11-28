package userInformation.userProfile;
import java.util.ArrayList;
import java.util.Arrays;
import userInformation.UserInformation;
public class State extends UserInformation {

    private static ArrayList<String> stateAbbreviations = new ArrayList<>(Arrays.asList(
            "AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA", "HI", "ID", "IL", "IN", "IA", "KS",
            "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC",
            "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT","VT", "VA", "WA", "WV", "WI", "WY"
    ));
    public State(String info) {
        setErrorMsg("Select 1");
        setInfo(info);
        checkValid();
    }

    @Override
    public void checkValid() {
        if (getInfo() != null) {
            setValid(true);
        }
    }

    public static ArrayList<String> getStateAbbreviations() {
        return stateAbbreviations;
    }
}
