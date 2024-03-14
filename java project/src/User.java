// class representing User
public class User {
    private String username;
    private String password;

    private boolean firstPurchase;

    // constructor


    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.firstPurchase = true;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;

    }
    public void setUsername (String username){
        this.username = username;
    }


    public boolean isFirstPurchase(){
        return firstPurchase;
    }

    public void setFirstPurchase(boolean firstPurchase){
        this.firstPurchase = firstPurchase;
    }
}