package pbo.kelompok4.util;

import pbo.kelompok4.model.Pengguna;

public class Session {
    private static Pengguna currentUser;

    public static void setUser(Pengguna user) {
        currentUser = user;
    }

    public static Pengguna getUser() {
        return currentUser;
    }

    public static void logout() {
        currentUser = null;
    }
}