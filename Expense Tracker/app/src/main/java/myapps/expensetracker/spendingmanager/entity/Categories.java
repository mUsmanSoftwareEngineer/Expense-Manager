package myapps.expensetracker.spendingmanager.entity;



import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "categories")
public class Categories {

    @PrimaryKey(autoGenerate = true)
    private int categoryId;

    @NonNull
    @ColumnInfo(name = "category_name")
    private String categoryName;

    @ColumnInfo(name = "category_type")
    private int categoryType;

    @ColumnInfo(name = "category_image", typeAffinity = ColumnInfo.BLOB)
    private byte[] categoryImage;

    public byte[] getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(byte[] categoryImage) {
        this.categoryImage = categoryImage;
    }



    @ColumnInfo(name = "category_colour")
    private int categoryColour;

//    @ColumnInfo(name = "category_image")
//    private Drawable categoryImg;

    public Categories() {

    }


    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @NonNull
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(@NonNull String categoryName) {
        this.categoryName = categoryName;
    }

    public int getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(int categoryType) {
        this.categoryType = categoryType;
    }

    public Categories(@NonNull String categoryName, int categoryType, byte[] categoryImage, int categoryColour) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
        this.categoryImage = categoryImage;
        this.categoryColour = categoryColour;
    }

    public int getCategoryColour() {
        return categoryColour;
    }

    public void setCategoryColour(int categoryColour) {
        this.categoryColour = categoryColour;
    }
}
