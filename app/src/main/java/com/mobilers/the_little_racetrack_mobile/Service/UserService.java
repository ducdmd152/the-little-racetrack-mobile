package com.mobilers.the_little_racetrack_mobile.Service;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.mobilers.the_little_racetrack_mobile.Config.MySharedPreferences;
import com.mobilers.the_little_racetrack_mobile.Model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserService implements IDataService {
    private MySharedPreferences mySharedPreferences;
    private Gson gson;

    public UserService(Context context) {
        this.mySharedPreferences = new MySharedPreferences(context);
        this.gson = new Gson();
    }

    @Override
    public boolean register(String username, String password) {
        username = username.trim().toLowerCase();
        List<User> listUsers = getListUsers();

        // Check if the user already exists
        if (userExists(username)) {
            return false; // Registration failed (username already exists)
        }

        // Create a new instance of User for the new user
        User newUser = new User(username, password, 0);

        // Add the new user to the list
        listUsers.add(newUser);

        // Save the updated list of users
        setListUsers(listUsers);

        return true; // Registration successful
    }

    @Override
    public boolean userExists(String username) {
        List<User> listUsers = getListUsers();
        for (User user : listUsers) {
            if (user.getUsername().equals(username)) {
                return true; // Username found
            }
        }
        return false; // Username not found
    }

    @Override
    public boolean login(String username, String password) {
        username = username.trim().toLowerCase();
        List<User> listUsers = getListUsers();

        for (User user : listUsers) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                // Username and password match, login successful
                return true;
            }
        }

        // Username or password is incorrect, login failed
        return false;
    }

    @Override
    public boolean changePassword(String username, String newPassword) {
        return false;
    }

    @Override
    public void setBalance(String username, long balance) {
        List<User> listUsers = getListUsers();

        for (User user : listUsers) {
            if (user.getUsername().equals(username)) {
                // Set the balance for the specified user
                user.setBalance(balance);
                break;
            }
        }

        // Save the updated list of users
        setListUsers(listUsers);
    }

    @Override
    public void addBalance(String username, long amount) {
        username = username.trim().toLowerCase();
        long currentBalance = getBalance(username);
        setBalance(username, currentBalance + amount);
    }

    @Override
    public void minusBalance(String username, long amount) {
        username = username.trim().toLowerCase();
        long currentBalance = getBalance(username);
        setBalance(username, Math.max(0, currentBalance - amount));
    }

    @Override
    public long getBalance(String username) {
        List<User> listUsers = getListUsers();

        for (User user : listUsers) {
            if (user.getUsername().equals(username)) {
                // Return the balance for the specified user
                Log.i("[balance]", "::Here:: " + username);
                return user.getBalance();
            }
        }
        return -1;
    }

    @Override
    public void clearData() {
        mySharedPreferences.putStringSetValue(MySharedPreferences.KEY_USER_DATA, "");
    }

    public void setListUsers(List<User> listUsers) {
        JsonArray jsonArray = gson.toJsonTree(listUsers).getAsJsonArray();
        String strJsonArray = jsonArray.toString();
        mySharedPreferences.putStringSetValue(MySharedPreferences.KEY_USER_DATA, strJsonArray);
    }

    public List<User> getListUsers() {
        String strJsonArray = mySharedPreferences.getStringSetValue(MySharedPreferences.KEY_USER_DATA);
        List<User> listUser = new ArrayList<>();

        try {
            if (!strJsonArray.isEmpty()) { // Kiểm tra chuỗi JSON có dữ liệu không
                JSONArray jsonArray = new JSONArray(strJsonArray);
                JSONObject jsonObject;
                User user;
                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObject = jsonArray.getJSONObject(i);
                    user = gson.fromJson(jsonObject.toString(), User.class);
                    listUser.add(user);
                }
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return listUser;
    }
}
