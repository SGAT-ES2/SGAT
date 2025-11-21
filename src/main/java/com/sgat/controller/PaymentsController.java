package com.sgat.controller;

import java.time.LocalDate;
import java.util.Map;

import com.sgat.model.Pagamento;
import com.sgat.view.PaymentDialog;
import com.sgat.view.PaymentsView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

public class PaymentsController {
    
    // Lista de pagamentos simulada
    private final ObservableList<Pagamento> pagamentos = FXCollections.observableArrayList();
    private final PaymentsView paymentsView; 

    // Mapa Mock para o valor total das reservas
    private final Map<String, Double> reservationTotals = Map.of(
        // Ajustando para que os totais sejam R$ 77.600,00 (17k + 48k + 12.6k)
        "RES-001", 17000.00,
        "RES-002", 48000.00,
        "RES-003", 12600.00 
    );
    // 🚨 AJUSTADO PARA CORRESPONDER À IMAGEM: 
    // R$ 41.000,00 (Pago) + R$ 36.600,00 (Pendente) = R$ 77.600,00
    private static final double TOTAL_GERAL_RESERVAS = 77600.00; 

    public PaymentsController(PaymentsView paymentsView) {
        this.paymentsView = paymentsView; 
        
        // 🚨 DADOS MOCKUP AJUSTADOS PARA RESULTAR NOS VALORES DA IMAGEM:
        // Soma dos pagos: 17000 + 24000 = R$ 41.000,00
        
        // Pagamento 1: RES-001 (Total: R$ 17.000,00, Pago: R$ 17.000,00) -> Status: Paga
        pagamentos.add(new Pagamento(1, "RES-001\nMaria Silva", 17000.00, "Cartão de Crédito", LocalDate.of(2025, 11, 10), "Pagamento total", 101));
        
        // Pagamento 2: RES-002 (Total: R$ 48.000,00, Pago: R$ 24.000,00) -> Status: Parcial
        pagamentos.add(new Pagamento(2, "RES-002\nJoão Santos", 24000.00, "Transferência", LocalDate.of(2025, 11, 12), "Sinal de reserva", 102));
        
        // Pagamento 3: RES-003 (Total: R$ 12.600,00, Pago: R$ 0,00) -> Status: Pendente (Não precisa de objeto Pagamento, mas deixamos os dados zerados abaixo para consistência)
        pagamentos.add(new Pagamento(3, "RES-003\nAna Costa", 0.00, "-", LocalDate.of(2025, 11, 12), "Sinal de reserva", 102));
        // OBS: Pagamento 3 não é necessário para a soma dos R$ 41.000,00, mas a lógica de status o exige.
        // Simulamos o Pagamento 3 como zero, ou seja, a reserva existe, mas sem pagamentos registrados ainda.
        // Se a Pagamento 3 for necessária para o cálculo do status, você precisará de uma lista de Reservas, não de Pagamentos.
        
        // Mantemos apenas os pagamentos reais (os que somam R$ 41.000,00)
        
    }

    public ObservableList<Pagamento> getPagamentos() {
        // Para simular a linha "RES-003" com R$ 0,00 pago, adicionamos um objeto Pagamento vazio,
        // mas o total geral de reservas (R$ 77.600,00) e o mapa de totais de reserva já refletem a reserva RES-003.
        
        // Se quisermos que a RES-003 apareça na lista de pagamentos com R$ 0,00 pago, 
        // a lista deve ser, na verdade, uma lista de Reservas combinada com a lista de Pagamentos.
        
        // Manteremos apenas os pagamentos, pois a View já calcula o status com base no total da reserva.
        
        return pagamentos;
    }

    public void abrirDialogPagamento(Stage ownerStage) {
        PaymentDialog dialog = new PaymentDialog(ownerStage);
        
        dialog.showAndWait().ifPresent(novoPagamento -> {
            if (novoPagamento != null) {
                registrarPagamento(novoPagamento);
            }
        });
    }

    public void registrarPagamento(Pagamento novoPagamento) {
        
        int clienteIdExistente = novoPagamento.getClienteId(); 
        
        if (clienteIdExistente > 0) {
            novoPagamento.setId(pagamentos.size() + 1); 
            pagamentos.add(0, novoPagamento); 

            this.paymentsView.refreshAll(); 

            System.out.println("✅ Pagamento de R$ " + novoPagamento.getValor() + 
                               " registrado com sucesso! Associado ao Cliente ID: " + clienteIdExistente);
        } else {
            System.err.println("🚨 ERRO: Falha ao registrar pagamento. ID de cliente existente não encontrado.");
        }
    }
    
    public double[] calcularTotaisDePagamento() {
        double totalPago = 0.0;
        
        for (Pagamento p : pagamentos) {
            totalPago += p.getValor();
        }
        
        // Total Geral: R$ 77.600,00
        // Total Pago: R$ 41.000,00 (com os mockups acima)
        // Total Pendente: 77.600,00 - 41.000,00 = R$ 36.600,00
        double totalPendente = TOTAL_GERAL_RESERVAS - totalPago;
        
        return new double[] {totalPago, totalPendente};
    }
    
    public double getReservationTotal(String reservationText) {
        String reservationId = reservationText.split("\n")[0].trim().split(" ")[0]; 
        return reservationTotals.getOrDefault(reservationId, 48000.00); 
    }
}