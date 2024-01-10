package myapps.expensetracker.spendingmanager.data.models;

public class CategoryModel {

    int cat_icon;
    String Cat_name;

    public CategoryModel(int cat_icon, String cat_name) {
        this.cat_icon = cat_icon;
        Cat_name = cat_name;
    }

    public CategoryModel() {
    }

    public int getCat_icon() {
        return cat_icon;
    }

    public void setCat_icon(int cat_icon) {
        this.cat_icon = cat_icon;
    }

    public String getCat_name() {
        return Cat_name;
    }

    public void setCat_name(String cat_name) {
        Cat_name = cat_name;
    }
}
