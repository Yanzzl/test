package com.example.test.accounts;

import android.graphics.Bitmap;


import java.util.HashMap;

public class UserData {
    private String currentUser;
    private boolean login = false;
    private String admin = "admin@gmail.com";
    private HashMap<String, String> name = new HashMap<>();
    private HashMap<String, String> password = new HashMap<>();
    private HashMap<String, Bitmap> picture = new HashMap<>();
    private HashMap<String, Boolean> stared = new HashMap<>();
    private HashMap<Integer, Boolean> gameState = new HashMap<>();


    private static final UserData data = new UserData();

    public static UserData getInstance() {
        return data;
    }

    public UserData() {
        name.put(admin, "admin");
        password.put(admin, "123456");
        picture.put(admin, null);
        stared.put(admin, false);
        iniGameState();
    }

    public void iniGameState() {
        gameState.put(1, true);
        gameState.put(2, false);
        gameState.put(3, false);
        gameState.put(4, false);
        gameState.put(5, false);
    }

    public Boolean isStared(String email) {
        return stared.get(email);
    }

    public void star(String email) {
        stared.put(email, true);
    }

    public void unStar(String email) {
        stared.put(email, false);
    }

    public void putName(String email, String name) {
        this.name.put(email, name);
    }

    public void putPassword(String email, String password) {
        this.password.put(email, password);
    }

    public void putPicture(String email, Bitmap pic) {
        this.picture.put(email, pic);
    }

    public String getName(String email) {
        return name.get(email);
    }

    public String getPassword(String email) {
        return password.get(email);
    }

    public Bitmap getPicture(String email) {
        return picture.get(email);
    }

    public boolean changeEmail(String oldEmail, String newEmail) {
        if (name.containsKey(newEmail)) {
            return false;
        } else {
            name.put(newEmail, getName(oldEmail));
            password.put(newEmail, getPassword(oldEmail));
            name.remove(oldEmail);
            password.remove(oldEmail);
            login(newEmail);
            return true;
        }
    }

    public boolean isLogin() {
        return login;
    }

    public void login(String email) {
        currentUser = email;
        login = true;
    }

    public void logout() {
        login = false;
    }

    public boolean isUser(String email) {
        return name.containsKey(email);
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public void unlockGame(int game) {
        this.gameState.put(game, true);
    }

    public boolean getGameState(int game) {
        return gameState.get(game);
    }


}
