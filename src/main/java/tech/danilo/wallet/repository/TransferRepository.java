package tech.danilo.wallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.danilo.wallet.entity.Transfer;

public interface TransferRepository extends JpaRepository<Transfer, Long> {
}
