package User;

import java.util.HashMap;

public class UserDetails {
    
    public String name;
    public String email;
    public String mobileNumber;
    public HashMap<String, Integer> balance;

    public UserDetails(String name, String email, String mobileNumber) {
        this.name = name;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.balance = new HashMap<String, Integer>();
    }
}