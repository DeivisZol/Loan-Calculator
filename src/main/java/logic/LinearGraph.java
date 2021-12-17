package logic;

public class LinearGraph extends Graph {
    public LinearGraph(double debtAmount, int months, double interest) {
        super(debtAmount, months, interest);
    }

    @Override
    public void calculateMonthlyPayment() {
        this.loanAmount = debtAmount / this.months;
        this.interest = debtAmount / 100 * interest / 12;
        this.monthlyRate = this.interest / this.months;
        this.monthlyPayment = this.interest + this.loanAmount;
    }

    @Override
    public Month[]  fillTableData() {
        Month[] tempTableData = new Month[months];
        double remainingAmount = debtAmount;
        for (int i = 0; i < months; ++i) {
            Month month = new Month();
            month.setIndexOfMonth(i + 1);
            month.setMonthlyPayment(Math.round(monthlyPayment * 100.0) / 100.0);
            month.setInterest(Math.round(interest * 100.0) / 100.0);
            month.setLoanAmount(Math.round(loanAmount * 100.0) / 100.0);
            month.setRemainingAmount(Math.round(remainingAmount * 100.0) / 100.0);
            tempTableData[i] = month;

            this.interest -= this.monthlyRate;
            monthlyPayment = this.loanAmount + this.interest;
            remainingAmount -= this.loanAmount;
        }
        return tempTableData;
    }
}
