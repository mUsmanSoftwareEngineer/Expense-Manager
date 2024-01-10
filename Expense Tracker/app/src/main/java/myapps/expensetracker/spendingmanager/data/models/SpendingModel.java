package myapps.expensetracker.spendingmanager.data.models;

public class SpendingModel  implements Comparable<BarChartEnteries>{

    int catId;

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public SpendingModel(int catId, Float amount, String categoryName, int categoryColor, byte[] categoryIcon, long time) {
        this.catId = catId;
        this.amount = amount;
        this.categoryName = categoryName;
        this.categoryColor = categoryColor;
        this.categoryIcon = categoryIcon;
        this.time = time;
    }

    Float amount;
    String categoryName;
    int categoryColor;
    byte[] categoryIcon;
    long time;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public SpendingModel(Float amount, String categoryName, int categoryColor, byte[] categoryIcon, long time) {
        this.amount = amount;
        this.categoryName = categoryName;
        this.categoryColor = categoryColor;
        this.categoryIcon = categoryIcon;
        this.time = time;
    }

    public byte[] getCategoryIcon() {
        return categoryIcon;
    }

    public void setCategoryIcon(byte[] categoryIcon) {
        this.categoryIcon = categoryIcon;
    }

    public SpendingModel(Float amount, String categoryName, int categoryColor, byte[] categoryIcon) {
        this.amount = amount;
        this.categoryName = categoryName;
        this.categoryColor = categoryColor;
        this.categoryIcon = categoryIcon;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public SpendingModel(Float amount, String categoryName) {
        this.amount = amount;
        this.categoryName = categoryName;
    }

    public int getCategoryColor() {
        return categoryColor;
    }

    public void setCategoryColor(int categoryColor) {
        this.categoryColor = categoryColor;
    }

    public SpendingModel(Float amount, String categoryName, int categoryColor) {
        this.amount = amount;
        this.categoryName = categoryName;
        this.categoryColor = categoryColor;
    }


    @Override
    public int compareTo(BarChartEnteries o) {

        return 0;
    }
}
