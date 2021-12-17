package logic;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Month {
    private SimpleIntegerProperty indexOfMonth = new SimpleIntegerProperty();
    private SimpleDoubleProperty remainingAmount = new SimpleDoubleProperty();
    private SimpleDoubleProperty monthlyPayment = new SimpleDoubleProperty();
    private SimpleDoubleProperty interest = new SimpleDoubleProperty();
    private SimpleDoubleProperty loanAmount = new SimpleDoubleProperty();

    // paslepta implementacija
    public int getIndexOfMonth() {
        return indexOfMonth.get();
    }

    public void setIndexOfMonth(int indexOfMonth) {
        this.indexOfMonth = new SimpleIntegerProperty(indexOfMonth);
    }

    public double getRemainingAmount() {
        return remainingAmount.get();
    }

    public void setRemainingAmount(double remainingAmount) {
        this.remainingAmount = new SimpleDoubleProperty(remainingAmount);
    }

    public double getMonthlyPayment() {
        return monthlyPayment.get();
    }

    public void setMonthlyPayment(double monthlyPayment) {
        this.monthlyPayment = new SimpleDoubleProperty(monthlyPayment);
    }

    public double getInterest() {
        return interest.get();
    }

    public void setInterest(double interest) {
        this.interest = new SimpleDoubleProperty(interest);
    }

    public double getLoanAmount() {
        return loanAmount.get();
    }

    public void setLoanAmount(double loanAmount) {
        this.loanAmount = new SimpleDoubleProperty(loanAmount);
    }
}
