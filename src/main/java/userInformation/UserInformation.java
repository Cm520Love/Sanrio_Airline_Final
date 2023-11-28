package userInformation;

public abstract class UserInformation {
    private boolean isValid;
    private String info;
    private String errorMsg;

    protected abstract void checkValid();

    public void setValid(boolean isValid) {
        this.isValid = isValid;
    }

    public boolean isValid() {
        return this.isValid;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
    public String getErrorMsg() { return errorMsg; }
    public void setErrorMsg(String errorMsg) {this.errorMsg = errorMsg;}

}