package com.crossover.auctionapp.vo;

import android.content.ContentValues;

import com.crossover.auctionapp.schema.AppSchema;


/**
 * Created by rafaelpino on 12/13/16.
 */
public class UserVO {
    private String rowId;
    private String login;
    private String names;
    private String lastNames;
    private int age;
    private String gender;
    private int documentId;
    private String password;

    public UserVO() {
    }

    public UserVO(String rowId, String login, String names, String lastNames, int age, String gender, int documentId,String password) {
        this.rowId = rowId;
        this.login = login;
        this.names = names;
        this.lastNames = lastNames;
        this.age = age;
        this.gender = gender;
        this.documentId = documentId;
        this.password = password;
    }

    public String getRowId() {
        return rowId;
    }

    public void setRowId(String rowId) {
        this.rowId = rowId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getLastNames() {
        return lastNames;
    }

    public void setLastNames(String lastNames) {
        this.lastNames = lastNames;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getDocumentId() {
        return documentId;
    }

    public void setDocumentId(int documentId) {
        this.documentId = documentId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put(AppSchema.UserSchema.USR_LOGIN, login);
        values.put(AppSchema.UserSchema.USR_NAMES, names);
        values.put(AppSchema.UserSchema.USR_LAST_NAMES, lastNames);
        values.put(AppSchema.UserSchema.USR_AGE, age);
        values.put(AppSchema.UserSchema.USR_GENDER, gender);
        values.put(AppSchema.UserSchema.USR_PASSWORD, password );
        values.put(AppSchema.UserSchema.USR_DOCID,documentId);
        return values;
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" \n RowId : " + rowId);
        stringBuilder.append(" \n Login : " + login);
        stringBuilder.append(" \n Name : " + names + " " + lastNames );
        stringBuilder.append(" \n Age : " + age);
        return stringBuilder.toString();
    }
}
