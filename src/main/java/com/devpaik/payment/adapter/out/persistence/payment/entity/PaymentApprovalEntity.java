package com.devpaik.payment.adapter.out.persistence.payment.entity;

import com.devpaik.payment.domain.payment.field.PaymentMethod;
import com.devpaik.payment.domain.payment.field.Status;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "tb_payment_approval")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
@DynamicInsert
public class PaymentApprovalEntity implements Serializable {

    @Id
    @Column(name = "payment_id", length = 60, nullable = false)
    @Comment("승인번호")
    @Getter
    private String paymentId;

    @Column(name = "merchant_id", length = 60, nullable = false)
    @Comment("상점ID")
    @Getter
    private String merchantId;

    @Column(name = "user_id", nullable = false)
    @Comment("사용자ID")
    @Getter
    private String userId;

    @Column(name = "payment_dtm")
    @Comment("결제일시")
    @Getter
    private LocalDateTime paymentDtm;

    @Enumerated(EnumType.STRING)
    @Column(name = "paymentMethod", length = 20)
    @Comment("거래유형")
    @Getter
    private PaymentMethod paymentMethod;

    @Column(name = "card_num", length = 20)
    @Comment("카드번호")
    @Getter
    private String cardNum;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    @Comment("승인상태")
    @Getter
    private Status status;

    @Column(name = "amount", precision = 18, scale = 2)
    @Comment("결제금액")
    @Getter
    private BigDecimal amount;

    @Column(name = "fee", precision = 18, scale = 2)
    @Comment("수수료")
    @Getter
    private BigDecimal fee;

    @Column(name = "currency_code", length = 3)
    @Comment("통화코드")
    @Getter
    private String currencyCode;

    @Column(name = "exchange_rate", precision = 18, scale = 2)
    @Comment("적용환율")
    @Getter
    private BigDecimal exchangeRate;

    @Column(name = "wallet_amount", precision = 18, scale = 2)
    @Comment("지갑결제금액")
    @Getter
    private BigDecimal walletAmount;

    @Column(name = "card_amount", precision = 18, scale = 2)
    @Comment("카드결제금액")
    @Getter
    private BigDecimal cardAmount;

    @Column(name = "won_amount")
    @Comment("원화결제금액")
    @Getter
    private Long wonAmount;

    @Column(name = "amount_total", precision = 18, scale = 2)
    @Comment("총지불금액")
    @Getter
    private BigDecimal amountTotal;

    @Column(name = "after_balance", precision = 18, scale = 2)
    @Comment("거래후잔액")
    @Getter
    private BigDecimal afterBalance;


    @Builder
    public PaymentApprovalEntity(String paymentId, String merchantId, String userId, LocalDateTime paymentDtm,
                                 PaymentMethod paymentMethod, String cardNum, Status status, BigDecimal amount,
                                 BigDecimal fee, String currencyCode, BigDecimal exchangeRate, BigDecimal walletAmount,
                                 BigDecimal cardAmount, Long wonAmount, BigDecimal amountTotal, BigDecimal afterBalance) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentApprovalEntity that = (PaymentApprovalEntity) o;
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
        return Objects.hash(paymentId, merchantId, userId, paymentDtm, paymentMethod, cardNum, status,
                amount, fee, currencyCode, exchangeRate, walletAmount, cardAmount, wonAmount,
                amountTotal, afterBalance);
    }

    @Override
    public String toString() {
        return "PaymentApprovalEntity{" +
                "paymentId='" + paymentId + '\'' +
                ", merchantId='" + merchantId + '\'' +
                ", userId='" + userId + '\'' +
                ", paymentDtm=" + paymentDtm +
                ", paymentMethod=" + paymentMethod +
                ", cardNum='" + cardNum + '\'' +
                ", status=" + status +
                ", amount=" + amount +
                ", fee=" + fee +
                ", currencyCode='" + currencyCode + '\'' +
                ", exchangeRate=" + exchangeRate +
                ", walletAmount=" + walletAmount +
                ", cardAmount=" + cardAmount +
                ", wonAmount=" + wonAmount +
                ", amountTotal=" + amountTotal +
                ", afterBalance=" + afterBalance +
                '}';
    }
}
