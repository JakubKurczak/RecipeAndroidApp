package models;

public class BaseRecipe {
    private String name;
    private int likeNumber;
    private String rec_ref="rcriObKcjblAV0kYnoAX";
    private String photo_url="beans.jpg";

    public BaseRecipe(){

    }
    public BaseRecipe(String name, int likeNumber) {
        this.name = name;
        this.likeNumber = likeNumber;
    }

    public BaseRecipe(String name, int likeNumber, String rec_ref) {
        this.name = name;
        this.likeNumber = likeNumber;
        this.rec_ref = rec_ref;
    }

    public BaseRecipe(String name, int likeNumber, String rec_ref, String photo_url) {
        this.name = name;
        this.likeNumber = likeNumber;
        this.rec_ref = rec_ref;
        this.photo_url = photo_url;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public String getRec_ref() {
        return rec_ref;
    }

    public void setRec_ref(String rec_ref) {
        this.rec_ref = rec_ref;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLikeNumber() {
        return likeNumber;
    }

    public void setLikeNumber(int likeNumber) {
        this.likeNumber = likeNumber;
    }
}
