package comNew.mySite.site.controllers.bankContollers;


import java.math.BigDecimal;

public class BankTransfer {
    private Long from;
    private Long to;
    private BigDecimal amount;

    public Long getFrom() {
        return from;
    }

    public void setFrom(Long from) {
        this.from = from;
    }

    public Long getTo() {
        return to;
    }

    public void setTo(Long to) {
        this.to = to;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
