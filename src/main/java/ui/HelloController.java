package ui;
/*
Deivis Zolba Informatika 2 grupe
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import logic.AnnuityGraph;
import logic.Graph;
import logic.LinearGraph;
import logic.Month;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.ResourceBundle;


public class HelloController implements Initializable {
    @FXML
    public TextField debtAmount;
    @FXML
    public TextField months;
    @FXML
    private TextField interest;
    @FXML
    public Button logButton;
    @FXML
    public Button printToFile;
    @FXML
    public ChoiceBox<String> paymentModel;
    @FXML
    public TextField monthFrom;
    @FXML
    public TextField monthTo;
    @FXML
    public TextField delayMonthFrom;
    @FXML
    public TextField delayMonthTo;
    @FXML
    private TextField delayInterest;
    @FXML
    public LineChart<String, Number> lineChart;

    @FXML
    private TableView<Month> loanTable;
    @FXML
    private TableColumn<Month, Integer> monthCol;
    @FXML
    private TableColumn<Month, Double> paymentCol;
    @FXML
    private TableColumn<Month, Double> creditCol;
    @FXML
    private TableColumn<Month, Double> interestCol;
    @FXML
    private TableColumn<Month, Double> unpaidCol;

    private void printToScreen(ActionEvent event) {
        Graph graph;
        double debtAmount = Double.parseDouble(this.debtAmount.getText());
        int months = Integer.parseInt(this.months.getText());
        double interest = Double.parseDouble(this.interest.getText());
        if ("Anuiteto".equals(paymentModel.getSelectionModel().getSelectedItem())) {
            graph = new AnnuityGraph(debtAmount, months, interest);
        } else {
            graph = new LinearGraph(debtAmount, months, interest);
        }

        graph.calculateMonthlyPayment();

        loanTable.getItems().clear();
        monthCol.setCellValueFactory(new PropertyValueFactory<>("indexOfMonth"));
        unpaidCol.setCellValueFactory(new PropertyValueFactory<>("remainingAmount"));
        paymentCol.setCellValueFactory(new PropertyValueFactory<>("monthlyPayment"));
        interestCol.setCellValueFactory(new PropertyValueFactory<>("interest"));
        creditCol.setCellValueFactory(new PropertyValueFactory<>("loanAmount"));

        Month[] results = graph.fillTableData();
        if (delayMonthFrom.getText() != null && delayMonthFrom.getText().length() > 0 && delayMonthTo.getText() != null && delayMonthTo.getText().length() > 0 && this.delayInterest.getText() != null && this.delayInterest.getText().length() > 0) {
            double delayInterest = Double.parseDouble(this.delayInterest.getText());
            int delayMonthFromNumber = Integer.parseInt(delayMonthFrom.getText());
            int delayMonthToNumber = Integer.parseInt(delayMonthTo.getText());

            Month[] delayResults = new Month[results.length + delayMonthToNumber - delayMonthFromNumber + 1];
            for (int i = 0; i < delayMonthFromNumber - 1; i++) {
                delayResults[i] = results[i];
            }
            double lastMonthRemainingAmount = results[delayMonthFromNumber - 1].getRemainingAmount();
            double paymentInterest = Math.round(lastMonthRemainingAmount * delayInterest) / 100d;
            for (int i = delayMonthFromNumber; i <= delayMonthToNumber; i++) {
                Month month = new Month();
                month.setIndexOfMonth(i);
                month.setRemainingAmount(lastMonthRemainingAmount);
                month.setInterest(paymentInterest);
                month.setMonthlyPayment(paymentInterest);
                delayResults[i - 1] = month;
            }
            for (int i = delayMonthFromNumber - 1; i < results.length; i++) {
                Month month = results[i];
                month.setIndexOfMonth(month.getIndexOfMonth() + delayMonthToNumber - delayMonthFromNumber + 1);
                delayResults[month.getIndexOfMonth() - 1] = month;
            }

            results = delayResults;
        }

        if (monthFrom.getText() != null && monthFrom.getText().length() > 0) {
            int monthFromNumber = Integer.parseInt(monthFrom.getText());
            results = Arrays.stream(results).filter(month -> month.getIndexOfMonth() >= monthFromNumber).toArray(Month[]::new);
        }
        if (monthTo.getText() != null && monthTo.getText().length() > 0) {
            int monthToNumber = Integer.parseInt(monthTo.getText());
            results = Arrays.stream(results).filter(month -> month.getIndexOfMonth() <= monthToNumber).toArray(Month[]::new);
        }

        loanTable.getItems().addAll(results);

        lineChart.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (int i = 0; i < results.length; i++) {
            series.getData().add(new XYChart.Data<>(i + "", results[i].getRemainingAmount()));
        }
        series.setName("Likusi suma");
        lineChart.getData().add(series);
    }

    private void printToFile(ActionEvent event) {
        Graph graph;
        double debtAmount = Double.parseDouble(this.debtAmount.getText());
        int months = Integer.parseInt(this.months.getText());
        double interest = Double.parseDouble(this.interest.getText());
        if ("Anuiteto".equals(paymentModel.getSelectionModel().getSelectedItem())) {
            graph = new AnnuityGraph(debtAmount, months, interest);
        } else {
            graph = new LinearGraph(debtAmount, months, interest);
        }

        graph.calculateMonthlyPayment();

        Month[] results = graph.fillTableData();
        if (delayMonthFrom.getText() != null && delayMonthFrom.getText().length() > 0 && delayMonthTo.getText() != null && delayMonthTo.getText().length() > 0 && this.delayInterest.getText() != null && this.delayInterest.getText().length() > 0) {
            double delayInterest = Double.parseDouble(this.delayInterest.getText());
            int delayMonthFromNumber = Integer.parseInt(delayMonthFrom.getText());
            int delayMonthToNumber = Integer.parseInt(delayMonthTo.getText());

            Month[] delayResults = new Month[results.length + delayMonthToNumber - delayMonthFromNumber + 1];
            for (int i = 0; i < delayMonthFromNumber - 1; i++) {
                delayResults[i] = results[i];
            }
            double lastMonthRemainingAmount = results[delayMonthFromNumber - 1].getRemainingAmount();
            double paymentInterest = Math.round(lastMonthRemainingAmount * delayInterest) / 100d;
            for (int i = delayMonthFromNumber; i <= delayMonthToNumber; i++) {
                Month month = new Month();
                month.setIndexOfMonth(i);
                month.setRemainingAmount(lastMonthRemainingAmount);
                month.setInterest(paymentInterest);
                month.setMonthlyPayment(paymentInterest);
                delayResults[i - 1] = month;
            }
            for (int i = delayMonthFromNumber - 1; i < results.length; i++) {
                Month month = results[i];
                month.setIndexOfMonth(month.getIndexOfMonth() + delayMonthToNumber - delayMonthFromNumber + 1);
                delayResults[month.getIndexOfMonth() - 1] = month;
            }

            results = delayResults;
        }

        if (monthFrom.getText() != null && monthFrom.getText().length() > 0) {
            int monthFromNumber = Integer.parseInt(monthFrom.getText());
            results = Arrays.stream(results).filter(month -> month.getIndexOfMonth() >= monthFromNumber).toArray(Month[]::new);
        }
        if (monthTo.getText() != null && monthTo.getText().length() > 0) {
            int monthToNumber = Integer.parseInt(monthTo.getText());
            results = Arrays.stream(results).filter(month -> month.getIndexOfMonth() <= monthToNumber).toArray(Month[]::new);
        }

        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("Ataskaita.csv"), StandardCharsets.UTF_8))) {
            writer.write("sep=,");
            writer.newLine();
            writer.write("Menesis,Imoka,Suma,Palukanos,Liko Nesumoketa");
            writer.newLine();

            for (Month result : results) {
                writer.write(result.getIndexOfMonth() + "," + result.getMonthlyPayment() + "," + result.getLoanAmount() + "," + result.getInterest() + "," + result.getRemainingAmount());
                writer.newLine();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        logButton.setOnAction(this::printToScreen);
        printToFile.setOnAction(this::printToFile);
    }
}