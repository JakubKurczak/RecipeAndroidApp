package models;

import java.util.List;

public class ComplexRecipie {
    String name;
    List<Integer> quantity;
    List<String> unit;
    List<String> ingredient_name;
    List<String> step_pic_url;
    List<String> step_desc;
    int likes = 0;

    public ComplexRecipie(){

    }
    public ComplexRecipie(String name, List<Integer> quantity, List<String> unit, List<String> ingredient_name, List<String> step_pic_url, List<String> step_desc, int likes) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.ingredient_name = ingredient_name;
        this.step_pic_url = step_pic_url;
        this.step_desc = step_desc;
        this.likes = likes;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getQuantity() {
        return quantity;
    }

    public void setQuantity(List<Integer> quantity) {
        this.quantity = quantity;
    }

    public List<String> getUnit() {
        return unit;
    }

    public void setUnit(List<String> unit) {
        this.unit = unit;
    }

    public List<String> getIngredient_name() {
        return ingredient_name;
    }

    public void setIngredient_name(List<String> ingredient_name) {
        this.ingredient_name = ingredient_name;
    }

    public List<String> getStep_pic_url() {
        return step_pic_url;
    }

    public void setStep_pic_url(List<String> step_pic_url) {
        this.step_pic_url = step_pic_url;
    }

    public List<String> getStep_desc() {
        return step_desc;
    }

    public void setStep_desc(List<String> step_desc) {
        this.step_desc = step_desc;
    }
}
