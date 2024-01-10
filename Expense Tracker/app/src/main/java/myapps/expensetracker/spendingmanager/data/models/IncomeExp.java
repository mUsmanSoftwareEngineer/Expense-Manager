package myapps.expensetracker.spendingmanager.data.models;

public class IncomeExp {

    Float income;
    Integer expense;

    public IncomeExp() {
    }

    public IncomeExp(Float income, Integer expense) {
        this.income = income;
        this.expense = expense;
    }

    public Float getIncome() {
        return income;
    }

    public void setIncome(Float income) {
        this.income = income;
    }

    public Integer getExpense() {
        return expense;
    }

    public void setExpense(Integer expense) {
        this.expense = expense;
    }
}
