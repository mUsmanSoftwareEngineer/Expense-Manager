package myapps.expensetracker.spendingmanager.data.models;

public class BarChartEnteries implements Comparable<BarChartEnteries> {

    Double value;
    String date;

    public BarChartEnteries(Double value, String date) {
        this.value = value;
        this.date = date;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int compareTo(BarChartEnteries o) {
        if (getDate() == null || o.getDate() == null) {
            return 0;
        }
        return getDate().compareTo(o.getDate());
    }
}
