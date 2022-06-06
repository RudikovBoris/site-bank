package comNew.mySite.site.controllers.bankContollers.admin;



import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "accounts")
public class Accounts {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;


    BigDecimal money;

    Long idUser;

    Integer numberAccount;

    Boolean accountNonLocked = true;

    public Boolean getAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(Boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }



    public Accounts() {
    }

    public Accounts(Long idUser, Integer numberAccount, BigDecimal money ) {
        this.money = money;
        this.idUser = idUser;
        this.numberAccount = numberAccount;
    }


    public Long getId() {
        return id;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public Integer getNumberAccount() {
        return numberAccount;
    }

    public void setNumberAccount(Integer numberAccount) {
        this.numberAccount = numberAccount;
    }


    @Override
    public int hashCode() {
        return Objects.hash(idUser, numberAccount);
    }
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }


}
