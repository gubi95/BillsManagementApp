package pwr.billsmanagement.webApp.models;

import java.util.List;

/**
 * Created by E6520 on 2017-05-10.
 */

public class User {

    public int userID;
    public String username;
    public String password;
    public String email;
    public String firstName;
    public String lastName;
    public List<Bill> bills;

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBills(List<Bill> bills) {
        this.bills = bills;
    }

    public int getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public List<Bill> getBills() {
        return bills;
    }




}
