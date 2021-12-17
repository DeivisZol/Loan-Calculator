package logic;

public class AnnuityGraph extends Graph {
    public AnnuityGraph(double debtAmount, int months, double interest) {
        super(debtAmount, months, interest);
    }

    @Override
    public void calculateMonthlyPayment() {
        this.monthlyRate = interest / 12 / 100;
        this.monthlyPayment = (monthlyRate * Math.pow((1 + monthlyRate), months) / (Math.pow((1 + monthlyRate), months) - 1)) * debtAmount;
        this.interest = debtAmount / 100 * interest / 12;
        this.loanAmount = monthlyPayment - interest;
    }

    @Override
    public Month[] fillTableData() {
        double remainingAmount = this.debtAmount;
        Month[] tempTableData = new Month[months];
        for (int i = 0; i < months; i++) {
            Month month = new Month();
            month.setIndexOfMonth(i + 1);
            month.setMonthlyPayment(Math.round(monthlyPayment * 100.0) / 100.0);
            month.setInterest(Math.round(interest * 100.0) / 100.0);
            month.setLoanAmount(Math.round(loanAmount * 100.0) / 100.0);
            month.setRemainingAmount(Math.round(remainingAmount * 100.0) / 100.0);
            tempTableData[i] = month;

            remainingAmount -= this.loanAmount;
            interest = remainingAmount * monthlyRate;
            this.loanAmount = monthlyPayment - interest;
        }
        return tempTableData;
    }
}
