package com.example.wagba;

import android.app.Application;

import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.wagba.Database.UserDao;
import com.example.wagba.Database.UserDatabase;

// MyApplication.java
public class MyApplication extends Application {

    private static UserDatabase userDatabase;
    private static UserDao userDao;

    @Override
    public void onCreate() {
        super.onCreate();
        userDatabase = Room.databaseBuilder(
                getApplicationContext(),
                UserDatabase.class,
                "user_database"
        ).allowMainThreadQueries().fallbackToDestructiveMigration().build();
        userDao = userDatabase.userDao();
    }

    public static UserDao getUserDao() {
        return userDao;
    }
}
