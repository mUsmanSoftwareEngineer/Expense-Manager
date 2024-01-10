package myapps.expensetracker.spendingmanager.entity;



import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "accounts")
public class Accounts {

    @PrimaryKey(autoGenerate = true)
    private int accountId;

    @NonNull
    @ColumnInfo(name = "account_name")
    private String accountName;

//    @ColumnInfo(name = "account_income")
//    private int accountIncome;
//
//    @ColumnInfo(name = "account_budget")
//    private int accountBudget;

    @ColumnInfo(name = "image", typeAffinity = ColumnInfo.BLOB)
    private byte[] image;

    public Accounts() {

    }

//    public Accounts(@NonNull String accountName, int accountIncome, int accountBudget, String accountImage) {
//        this.accountName = accountName;
//        this.accountIncome = accountIncome;
//        this.accountBudget = accountBudget;
//        this.accountImage = accountImage;
//    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @NonNull
    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(@NonNull String accountName) {
        this.accountName = accountName;
    }

//    public int getAccountIncome() {
//        return accountIncome;
//    }


//
//    public void setAccountIncome(int accountIncome) {
//        this.accountIncome = accountIncome;
//    }
//
//    public int getAccountBudget() {
//        return accountBudget;
//    }
//
//    public void setAccountBudget(int accountBudget) {
//        this.accountBudget = accountBudget;
//    }


    public Accounts(@NonNull String accountName, byte[] image) {
        this.accountName = accountName;
        this.image = image;
    }
}
