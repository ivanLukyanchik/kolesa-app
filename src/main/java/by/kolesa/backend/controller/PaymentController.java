package by.kolesa.backend.controller;

import by.kolesa.backend.dto.PaymentDto;
import by.kolesa.backend.dto.PaymentResponse;
import by.kolesa.backend.service.PaymentService;
import com.paypal.api.payments.Payment;
import com.stripe.model.Charge;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PaymentController {

    private final PaymentService paymentsService;

    @Value("${stripe.public.key}")
    private String stripePublicKey;

    @Value("${paypal.success.url}")
    private String paypalSuccessUrl;

    @Value("${paypal.failure.url}")
    private String paypalFailureUrl;

    @GetMapping("/stripe-form")
    public String getStripeForm(Model model) {
        model.addAttribute("amount", 50 * 100);
        model.addAttribute("stripePublicKey", stripePublicKey);
        model.addAttribute("currency", "USD");
        return "stripe-form";
    }

    @PostMapping("/stripe-pay")
    @SneakyThrows
    @ResponseBody
    public PaymentResponse payWithStripe(PaymentDto paymentDto) {
        Charge charge = paymentsService.charge(paymentDto);
        log.info(charge.toJson());
        return PaymentResponse.builder().chargeId(charge.getId()).status(charge.getStatus())
                .balanceTransaction(charge.getBalanceTransaction())
                .build();
    }

    @GetMapping("/paypal-form")
    public String getPaypalForm() {
        return "paypal-form";
    }

    @SneakyThrows
    @PostMapping("/paypal-pay")
    public String payWithPaypal(@ModelAttribute("payment") PaymentDto paymentDto, HttpServletResponse resp) {
        Payment payment = paymentsService.createPaypalPayment(paymentDto.getAmount(), paypalFailureUrl, paypalSuccessUrl);
        String link = paymentsService.getApprovalLink(payment);
        if (link != null) {
            return "redirect:" + link;
        }
        resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error occurred while payment");
        return null;
    }

    @SneakyThrows
    @ApiOperation(value = "This is only for pay method", hidden = true)
    @GetMapping("/paypal-pay/success")
    @ResponseBody
    public ResponseEntity<String> paySuccess(@RequestParam String paymentId, @RequestParam("PayerID") String payerId) {
        Payment payment = paymentsService.executePaypalPayment(paymentId, payerId);
        log.info(payment.toJSON());
        if (payment.getState().equals("approved")) {
            return new ResponseEntity<>("Payment success", HttpStatus.OK);
        }
        return new ResponseEntity<>("Error occurred while payment", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ApiOperation(value = "This is only for pay method", hidden = true)
    @GetMapping("/paypal-pay/failure")
    @ResponseBody
    public String payFailure() {
        return "Payment failure";
    }

}
