package com.mobilers.the_little_racetrack_mobile;

public class GlobalData {
    private static GlobalData instance;

    private String currentUser;
    private int sessionWinCounter;
    private int sessionLoseCounter;

    private GlobalData() {}

    public static synchronized GlobalData getInstance() {
        if (instance == null) {
            instance = new GlobalData();
            instance.clearSession();
        }
        return instance;
    }

    public boolean isAuthenticated() {
        return currentUser != null;
    }

    public void clearSession() {
        setCurrentUser(null);
        setSessionWinCounter(0);
        setSessionLoseCounter(0);
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    public int getSessionWinCounter() {
        return sessionWinCounter;
    }

    public void setSessionWinCounter(int sessionWinCounter) {
        this.sessionWinCounter = sessionWinCounter;
    }

    public int getSessionLoseCounter() {
        return sessionLoseCounter;
    }

    public void setSessionLoseCounter(int sessionLoseCounter) {
        this.sessionLoseCounter = sessionLoseCounter;
    }
}

