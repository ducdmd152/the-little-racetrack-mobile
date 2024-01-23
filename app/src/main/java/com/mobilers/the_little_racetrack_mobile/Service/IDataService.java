package com.mobilers.the_little_racetrack_mobile.service;

public interface IDataService
{
    boolean userExists(String username);
    boolean login(String username, String password);
    boolean register(String username, String password);
    boolean changePassword(String username, String newPassword);
    void setBalance(String username, long balance);
    void addBalance(String username, long amount);
    void minusBalance(String username, long amount);
    long getBalance(String username);
    void clearData();
}
