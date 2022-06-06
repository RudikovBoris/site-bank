package comNew.mySite.site.repository;

import comNew.mySite.site.controllers.bankContollers.admin.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Accounts,Long> {

    Boolean existsByNumberAccount (Integer numberAccount);
    Boolean existsByIdUser (Long idUser);

    Boolean existsByMoney(BigDecimal money);
    Optional<Accounts> findById(Long id);

    List<Accounts> findByIdUser (Long idUser);


    Optional<Accounts> findByNumberAccount (Integer numberAccount);


}
