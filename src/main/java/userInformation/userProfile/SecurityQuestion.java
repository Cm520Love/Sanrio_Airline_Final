package userInformation.userProfile;
import userInformation.UserInformation;
public class SecurityQuestion extends UserInformation {

    private static String[] questions = {"What is your favorite anime character?",
            "What will you name your pet?",
            "What is your favorite song?",
            "What is your dream?",
            "What is your favorite Sanrio Collection?"};
    public SecurityQuestion(String info) {
        setErrorMsg("Please select a security question");
        setInfo(info);
        checkValid();
    }

    @Override
    public void checkValid() {
        if (getInfo() != null) {
            setValid(true);
        }
    }

    public static String[] getQuestions() {
        return questions;
    }
}
