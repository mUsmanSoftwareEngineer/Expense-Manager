package myapps.expensetracker.spendingmanager.entity;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

import myapps.expensetracker.spendingmanager.database.DateTypeConverter;
import myapps.expensetracker.spendingmanager.database.TimestampConverter;


@Entity(tableName = "spending")
public class Spending {




    @PrimaryKey(autoGenerate = true)
    private int spendingId;

    @ColumnInfo(name = "spending_type")
    private int spendingType;


//    @ColumnInfo(name = "spending_type_name")
//    private String spendingTypeName;


    @ColumnInfo(name = "spending_category_id")
    private int spendingCategoryId;


//    @ColumnInfo(name = "spending_category_name")
//    private String spendingCategoryName;


    @ColumnInfo(name = "spending_amount")
    private double spendingAmount;


    @ColumnInfo(name = "spending_account_holder_id")
    private int spendingAccountHolderId;


//    @ColumnInfo(name = "spending_account_holder_name")
//    private String spendingAccountHolderName;

    @TypeConverters({TimestampConverter.class})
    private Date transactionDate;

    @ColumnInfo(name = "spending_notes")
    private String spendingNotes;

    @ColumnInfo(name = "image", typeAffinity = ColumnInfo.BLOB)
    private byte[] SpendingImage;

//    @TypeConverters({TimestampConverter.class})
//    private Date transactionEndRepeatingDate;
//
//    @ColumnInfo(name = "spending_repeat")
//    private boolean isRepeat;



    public Spending() {
    }

    public Spending(int spendingType, int spendingCategoryId, double spendingAmount, int spendingAccountHolderId, Date transactionDate, String spendingNotes, byte[] spendingImage) {
        this.spendingType = spendingType;
        this.spendingCategoryId = spendingCategoryId;
        this.spendingAmount = spendingAmount;
        this.spendingAccountHolderId = spendingAccountHolderId;
        this.transactionDate = transactionDate;
        this.spendingNotes = spendingNotes;
        SpendingImage = spendingImage;
    }

    public byte[] getSpendingImage() {
        return SpendingImage;
    }

    public void setSpendingImage(byte[] spendingImage) {
        SpendingImage = spendingImage;
    }

    public String getSpendingNotes() {
        return spendingNotes;
    }

    public void setSpendingNotes(String spendingNotes) {
        this.spendingNotes = spendingNotes;
    }

    public int getSpendingId()
    {
        return spendingId;
    }

    public void setSpendingId(int spendingId) {
        this.spendingId = spendingId;
    }

    public int getSpendingType() {
        return spendingType;
    }

    public void setSpendingType(int spendingType) {
        this.spendingType = spendingType;
    }

//    @NotNull
//    public String getSpendingTypeName() {
//        return spendingTypeName;
//    }
//
//    public void setSpendingTypeName(@NotNull String spendingTypeName) {
//        this.spendingTypeName = spendingTypeName;
//    }

    public int getSpendingCategoryId() {
        return spendingCategoryId;
    }

    public void setSpendingCategoryId(int spendingCategoryId) {
        this.spendingCategoryId = spendingCategoryId;
    }

//    @NotNull
//    public String getSpendingCategoryName() {
//        return spendingCategoryName;
//    }
//
//    public void setSpendingCategoryName(@NotNull String spendingCategoryName) {
//        this.spendingCategoryName = spendingCategoryName;
//    }

    public double getSpendingAmount() {
        return spendingAmount;
    }

    public void setSpendingAmount(double spendingAmount) {
        this.spendingAmount = spendingAmount;
    }

    public int getSpendingAccountHolderId() {
        return spendingAccountHolderId;
    }

    public void setSpendingAccountHolderId(int spendingAccountHolderId) {
        this.spendingAccountHolderId = spendingAccountHolderId;
    }




//    @NotNull
//    public String getSpendingAccountHolderName() {
//        return spendingAccountHolderName;
//    }
//
//    public void setSpendingAccountHolderName(@NotNull String spendingAccountHolderName) {
//        this.spendingAccountHolderName = spendingAccountHolderName;
//    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }





}
