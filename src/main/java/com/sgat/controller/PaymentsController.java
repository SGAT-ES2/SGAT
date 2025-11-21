package com.sgat.controller;

import com.sgat.model.Pagamento;
import com.sgat.model.PaymentDAO;
import com.sgat.model.Reservation;
import com.sgat.model.ReservationDAO;
import com.sgat.view.PaymentDialog;
import com.sgat.view.PaymentsView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

import java.util.List;

public class PaymentsController {

    private final PaymentsView paymentsView;
    private final PaymentDAO paymentDAO;
    private final ReservationDAO reservationDAO;

    public PaymentsController(PaymentsView paymentsView) {
        this.paymentsView = paymentsView;
        this.paymentDAO = new PaymentDAO();
        this.reservationDAO = new ReservationDAO();
    }

    public ObservableList<Pagamento> getPagamentos() {
        return FXCollections.observableArrayList(paymentDAO.getAllPayments());
    }

    public List<Reservation> getAllReservations() {
        return reservationDAO.getAllReservations();
    }

    public void openPaymentDialog(Stage ownerStage) {
        PaymentDialog dialog = new PaymentDialog(ownerStage, this);
        dialog.showAndWait().ifPresent(this::registerPayment);
    }

    public void registerPayment(Pagamento novoPagamento) {
        if (novoPagamento != null) {
            paymentDAO.addPayment(novoPagamento);
            paymentsView.refreshAll();
            System.out.println("Payment of R$ " + novoPagamento.getValorPago() + " registered successfully!");
        } else {
            System.err.println("ERROR: Failed to register payment.");
        }
    }

    public double[] calculatePaymentTotals() {
        List<Pagamento> payments = paymentDAO.getAllPayments();
        List<Reservation> reservations = reservationDAO.getAllReservations();
        
        double totalPaid = 0.0;
        for (Pagamento p : payments) {
            totalPaid += p.getValorPago();
        }

        double totalFromReservations = 0.0;
        for (Reservation r : reservations) {
            totalFromReservations += r.getTotalValue();
        }

        double totalPending = totalFromReservations - totalPaid;

        return new double[]{totalPaid, totalPending};
    }
    
    public double getReservationTotal(Reservation reservation) {
        return reservation.getTotalValue();
    }
}