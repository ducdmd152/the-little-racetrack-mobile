package com.mobilers.the_little_racetrack_mobile.Service;

import android.content.Context;

import java.io.*;

public class DataService implements IDataService {
    private final String dataFileName = "Data.txt";
    private final Context context;

    public DataService(Context context) {
        this.context = context;
    }

    @Override
    public boolean login(String username, String password) {
        username = username.trim().toLowerCase();
        try {
            String fileContents = readFromFile(dataFileName);
            StringBuilder newContents = new StringBuilder();
            String[] lines = fileContents.split("\n");

            for (String line : lines) {
                String[] parts = line.split(":");
                if (parts.length == 3 && parts[0].equals(username) && parts[1].equals(password)) {
                    return true; // Login successful
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // Login failed
    }

    @Override
    public boolean register(String username, String password) {
        username = username.trim().toLowerCase();
        try {
            // Check if the username already exists
            if (userExists(username)) {
                return false; // Registration failed (username already exists)
            }

            // Append new user credentials to Data.txt
            writeToFile(dataFileName, username + ":" + password + ":0");

            return true; // Registration successful
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Registration failed (IO error)
        }
    }

    @Override
    public boolean changePassword(String username, String newPassword) {
        username = username.trim().toLowerCase();
        try {
            String fileContents = readFromFile(dataFileName);
            StringBuilder newContents = new StringBuilder();
            String[] lines = fileContents.split("\n");

            for (String line : lines) {
                String[] parts = line.split(":");
                if (parts.length == 3 && parts[0].equals(username)) {
                    // Update the password
                    newContents.append(username).append(":").append(newPassword).append(":").append(parts[2]).append("\n");
                } else {
                    // Keep the existing line
                    newContents.append(line).append("\n");
                }
            }

            // Write the updated content back to the file
            writeToFile(dataFileName, newContents.toString());

            return true; // Password changed successfully
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Password change failed (IO error)
        }
    }

    @Override
    public void setBalance(String username, long balance) {
        username = username.trim().toLowerCase();
        try {
            String fileContents = readFromFile(dataFileName);
            StringBuilder newContents = new StringBuilder();
            String[] lines = fileContents.split("\n");
            boolean userExists = false;

            for (String line : lines) {
                String[] parts = line.split(":");
                if (parts.length == 3 && parts[0].equals(username)) {
                    // Update existing balance
                    newContents.append(username).append(":").append(parts[1]).append(":").append(balance).append("\n");
                    userExists = true;
                } else {
                    newContents.append(line).append("\n");
                }
            }

            writeToFile(dataFileName, newContents.toString());

            // If the username doesn't exist in Data.txt, add it with the specified balance
            if (!userExists) {
                throw new Exception(username + " is not exists!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        username = username.trim().toLowerCase();
        try {
            String fileContents = readFromFile(dataFileName);
            StringBuilder newContents = new StringBuilder();
            String[] lines = fileContents.split("\n");

            for (String line : lines) {
                String[] parts = line.split(":");
                if (parts.length == 3 && parts[0].equals(username)) {
                    return Long.parseLong(parts[2]); // Return the balance
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0L; // Return 0 if username not found or on error
    }

    @Override
    public void clearData() {
        try {
            writeToFile(dataFileName, "");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public boolean userExists(String username) {
        username = username.trim().toLowerCase();
        try {
            String fileContents = readFromFile(dataFileName);
            StringBuilder newContents = new StringBuilder();
            String[] lines = fileContents.split("\n");

            for (String line : lines) {
                String[] parts = line.split(":");
                if (parts.length == 3 && parts[0].equals(username)) {
                    return true; // Username found
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // Username not found
    }

    private String readFromFile(String fileName) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        File path = context.getApplicationContext().getFilesDir();
        try (FileInputStream fis = new FileInputStream(new File(path, fileName));
             InputStreamReader inputStreamReader = new InputStreamReader(fis);
             BufferedReader reader = new BufferedReader(inputStreamReader)) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return stringBuilder.toString();
    }

    private void writeToFile(String fileName, String data) throws IOException {
        File path = context.getApplicationContext().getFilesDir();
        try (FileOutputStream fos = new FileOutputStream(new File(path, fileName));
             OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fos);
             BufferedWriter writer = new BufferedWriter(outputStreamWriter)) {
            writer.write(data + "\n");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}