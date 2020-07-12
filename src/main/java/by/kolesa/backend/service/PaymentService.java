package by.kolesa.backend.service;

import by.kolesa.backend.dto.PaymentDto;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.stripe.Stripe;
import com.stripe.model.Charge;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final APIContext apiContext;

    @Value("${stripe.secret.key}")
    private String secretKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;
    }

    @SneakyThrows
    public Charge charge(PaymentDto paymentDto) {
        paymentDto.setDescription("Yet another description");
        paymentDto.setCurrency("USD");
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", paymentDto.getAmount());
        chargeParams.put("currency", paymentDto.getCurrency());
        chargeParams.put("description", paymentDto.getDescription());
        chargeParams.put("source", paymentDto.getStripeToken());
        return Charge.create(chargeParams);
    }

    @SneakyThrows
    public Payment createPaypalPayment(int total, String failureUrl, String successUrl) {
        Amount amount = new Amount();
        amount.setCurrency("USD");
        double finalTotal = new BigDecimal(total).setScale(2, RoundingMode.HALF_UP).doubleValue();
        amount.setTotal(String.format("%.2f", finalTotal));

        Transaction transaction = new Transaction();
        transaction.setDescription("Yet another description");
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(transactions);
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(failureUrl);
        redirectUrls.setReturnUrl(successUrl);
        payment.setRedirectUrls(redirectUrls);

        return payment.create(apiContext);
    }

    @SneakyThrows
    public Payment executePaypalPayment(String paymentId, String payerId) {
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecute = new PaymentExecution();
        paymentExecute.setPayerId(payerId);
        return payment.execute(apiContext, paymentExecute);
    }

    public String getApprovalLink(Payment payment) {
        for (Links link : payment.getLinks()) {
            if (link.getRel().equals("approval_url")) {
                return  link.getHref();
            }
        }
        return null;
    }

}
