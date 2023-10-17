package com.tea.rest;


import java.io.Serializable;
import java.util.ArrayList;


public class User implements Serializable {
    private String token_id;
    private String email;
    private ArrayList<Jar> my_Jars;
    private ArrayList<Jar> owned_Jars;
    private Boolean admin = false;
    private boolean notify = true;

    public void setEmail(String email) {this.email = email;}

    public String getEmail() {return email;}

    public String getToken_id() {
        return token_id;
    }

    public void setToken_id(String token_id) {
        this.token_id = token_id;
    }

    public ArrayList<Jar> getMy_Jars() {
        return my_Jars;
    }
    public void setMy_Jars(ArrayList<Jar> my_Jars) {
        this.my_Jars = my_Jars;
    }


    public void add_Jar(Jar jar) {
        my_Jars.add(jar);
    }
    public ArrayList<Jar> getOwned_Jars() {
        return owned_Jars;
    }
    public void setOwned_Jars(ArrayList<Jar> owned_Jars) {
        this.owned_Jars = owned_Jars;
    }




    public void add_Owned_Jar(Jar j) {
        if(owned_Jars != null){
            owned_Jars.add(j);
        }else {
            owned_Jars = new ArrayList<>();
            owned_Jars.add(0,j);
        }

    }

    public void delete_Owned_jar(Jar j){
        owned_Jars.remove(j);
    }

    public boolean getNotify() {
        return notify;
    }

    public void setNotify(boolean notify) {
        this.notify = notify;
    }

    public boolean is_admin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }


//    public String myJarsToString() {
//        return " {" +
//                "my_Jars: " + my_Jars +
//                '}';
//    }

    @Override
    public String toString() {
        return "User{" +
                "token_Id='" + token_id + '\'' +
                ", email='" + email + '\'' +
                ", my_Jars=" + my_Jars +
                ", owned_Jars=" + owned_Jars +
                ", admin=" + admin +
                ", notify=" + notify +
                '}';
    }
}