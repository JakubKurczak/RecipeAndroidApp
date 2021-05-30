package models;

public class User {
    public static String doc_id;
    public static String name;
    public static String surname;
    public static String pic_url;
    public static String mail;
    public User(){

    }

    public User(String doc_id,String name, String surname, String pic_url, String mail) {
        User.doc_id = doc_id;
        User.name = name;
        User.surname = surname;
        User.pic_url = pic_url;
        User.mail = mail;

    }

    public static String getDoc_id() {
        return doc_id;
    }

    public static void setDoc_id(String doc_id) {
        User.doc_id = doc_id;
    }

    public static String getMail() {
        return User.mail;
    }

    public static void setMail(String mail) {
        User.mail = mail;
    }

    public  static String getName() {
        return User.name;
    }

    public static  void setName(String name) {
        User.name = name;
    }

    public static  String getSurname() {
        return User.surname;
    }

    public  static void setSurname(String surname) {
        User.surname = surname;
    }

    public static  String getPic_url() {
        return User.pic_url;
    }

    public static  void setPic_url(String pic_url) {
        User.pic_url = pic_url;
    }
}
