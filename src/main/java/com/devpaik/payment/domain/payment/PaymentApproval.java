package com.devpaik.payment.domain.payment;

import com.devpaik.payment.adapter.out.persistence.payment.entity.PaymentApprovalEntity;
import com.devpaik.payment.domain.exchangerate.field.CurrencyCode;
import com.devpaik.payment.domain.exchangerate.field.ExchangeRate;
import com.devpaik.payment.domain.merchant.field.MerchantId;
import com.devpaik.payment.domain.payment.field.*;
import com.devpaik.payment.domain.user.field.UserId;
import jakarta.el.ELResolver;
import lombok.Getter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class PaymentApproval implements Serializable {

    @Getter
    private final PaymentId paymentId;

    @Getter
    private final MerchantId merchantId;

    @Getter
    private final UserId userId;

    @Getter
    private final PaymentDtm paymentDtm;

    @Getter
    private final PaymentMethod paymentMethod;

    @Getter
    private final CardNum cardNum;

    @Getter
    private final Status status;

    @Getter
    private final Amount amount;

    @Getter
    private final Fee fee;

    @Getter
    private final CurrencyCode currencyCode;

    @Getter
    private final ExchangeRate exchangeRate;

    @Getter
    private final WalletAmount walletAmount;

    @Getter
    private final CardAmount cardAmount;

    @Getter
    private final WonAmount wonAmount;

    @Getter
    private final AmountTotal amountTotal;

    @Getter
    private final AfterBalance afterBalance;

    public PaymentApproval(PaymentId paymentId, MerchantId merchantId, UserId userId, PaymentDtm paymentDtm,
                           PaymentMethod paymentMethod, CardNum cardNum, Status status, Amount amount, Fee fee,
                           CurrencyCode currencyCode, ExchangeRate exchangeRate, WalletAmount walletAmount,
                           CardAmount cardAmount, WonAmount wonAmount, AmountTotal amountTotal,
                           AfterBalance afterBalance) {
        this.paymentId = paymentId;
        this.merchantId = merchantId;
        this.userId = userId;
        this.paymentDtm = paymentDtm;
        this.paymentMethod = paymentMethod;
        this.cardNum = cardNum;
        this.status = status;
        this.amount = amount;
        this.fee = fee;
        this.currencyCode = currencyCode;
        this.exchangeRate = exchangeRate;
        this.walletAmount = walletAmount;
        this.cardAmount = cardAmount;
        this.wonAmount = wonAmount;
        this.amountTotal = amountTotal;
        this.afterBalance = afterBalance;
    }

    public static PaymentApproval createPaymentApprovalByEntity(PaymentApprovalEntity entity) {
        return new PaymentApproval(
                new PaymentId(entity.getPaymentId()),
                new MerchantId(entity.getMerchantId()),
                new UserId(entity.getUserId()),
                new PaymentDtm(entity.getPaymentDtm()),
                entity.getPaymentMethod(),
                new CardNum(entity.getCardNum()),
                entity.getStatus(),
                new Amount(entity.getAmount()),
                new Fee(entity.getFee()),
                new CurrencyCode(entity.getCurrencyCode()),
                Objects.isNull(entity.getExchangeRate()) ? null : new ExchangeRate(entity.getExchangeRate()),
                new WalletAmount(entity.getWalletAmount()),
                new CardAmount(entity.getCardAmount()),
                new WonAmount(entity.getWonAmount()),
                new AmountTotal(entity.getAmountTotal()),
                new AfterBalance(entity.getAfterBalance())
        );
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentApproval that = (PaymentApproval) o;
        return Objects.equals(paymentId, that.paymentId) && Objects.equals(merchantId, that.merchantId) &&
                Objects.equals(userId, that.userId) && Objects.equals(paymentDtm, that.paymentDtm) &&
                paymentMethod == that.paymentMethod && Objects.equals(cardNum, that.cardNum) &&
                status == that.status && Objects.equals(amount, that.amount) && Objects.equals(fee, that.fee) &&
                Objects.equals(currencyCode, that.currencyCode) && Objects.equals(exchangeRate, that.exchangeRate) &&
                Objects.equals(walletAmount, that.walletAmount) && Objects.equals(cardAmount, that.cardAmount) &&
                Objects.equals(wonAmount, that.wonAmount) && Objects.equals(amountTotal, that.amountTotal) &&
                Objects.equals(afterBalance, that.afterBalance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paymentId, merchantId, userId, paymentDtm, paymentMethod, cardNum, status, amount,
                fee, currencyCode, exchangeRate, walletAmount, cardAmount, wonAmount, amountTotal, afterBalance);
    }

    @Override
    public String toString() {
        return "PaymentApproval{" +
                "paymentId=" + paymentId +
                ", merchantId=" + merchantId +
                ", userId=" + userId +
                ", paymentDtm=" + paymentDtm +
                ", paymentMethod=" + paymentMethod +
                ", cardNum=" + cardNum +
                ", status=" + status +
                ", amount=" + amount +
                ", fee=" + fee +
                ", currencyCode=" + currencyCode +
                ", exchangeRate=" + exchangeRate +
                ", walletAmount=" + walletAmount +
                ", cardAmount=" + cardAmount +
                ", wonAmount=" + wonAmount +
                ", amountTotal=" + amountTotal +
                ", afterBalance=" + afterBalance +
                '}';
    }



    public static PaymentApproval createPaymentApproval(CalculatePayment calculatePayment) {
        return new PaymentApproval(
                calculatePayment.paymentId(),
                calculatePayment.merchantId(),
                calculatePayment.userId(),
                calculatePayment.paymentDtm(),
                calculatePayment.paymentMethod(),
                calculatePayment.cardNum(),
                calculatePayment.status(),
                calculatePayment.amount(),
                calculatePayment.fee(),
                calculatePayment.currencyCode(),
                calculatePayment.exchangeRate(),
                calculatePayment.walletAmount(),
                calculatePayment.cardAmount(),
                calculatePayment.wonAmount(),
                calculatePayment.amountTotal(),
                calculatePayment.afterWallet().parseAfterBalance());
    }

    public PaymentApproval sendFailPaymentApproval() {
        return new PaymentApproval(
                this.getPaymentId(),
                this.getMerchantId(),
                this.getUserId(),
                this.getPaymentDtm(),
                this.getPaymentMethod(),
                this.getCardNum(),
                Status.failed,
                this.getAmount(),
                this.getFee(),
                this.getCurrencyCode(),
                this.getExchangeRate(),
                this.getWalletAmount(),
                this.getCardAmount(),
                this.getWonAmount(),
                this.getAmountTotal(),
                this.getAfterBalance()
        );
    }

    public PaymentApproval sendSuccessPaymentApproval() {
        return new PaymentApproval(
                this.getPaymentId(),
                this.getMerchantId(),
                this.getUserId(),
                this.getPaymentDtm(),
                this.getPaymentMethod(),
                this.getCardNum(),
                Status.approved,
                this.getAmount(),
                this.getFee(),
                this.getCurrencyCode(),
                this.getExchangeRate(),
                this.getWalletAmount(),
                this.getCardAmount(),
                this.getWonAmount(),
                this.getAmountTotal(),
                this.getAfterBalance()
        );
    }

    public AmountTotal getAmountTotalScale() {
        if (CurrencyCode.KRW.equals(this.currencyCode.getValue())) {
            return new AmountTotal(this.amountTotal.getValue().setScale(0, RoundingMode.FLOOR));
        }
        return new AmountTotal(this.amountTotal.getValue().setScale(2, RoundingMode.HALF_UP));
    }
}
