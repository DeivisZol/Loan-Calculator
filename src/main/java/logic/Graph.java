package logic;

public abstract class Graph {
    protected double debtAmount;
    protected int months;
    protected double interest;
    protected double monthlyPayment;
    protected double monthlyRate;
    protected double loanAmount;

    public Graph(double debtAmount, int months, double interest) {
        this.debtAmount = debtAmount;
        this.months = months;
        this.interest = interest;
    }

    public abstract void calculateMonthlyPayment();

    public abstract Month[] fillTableData();
}
