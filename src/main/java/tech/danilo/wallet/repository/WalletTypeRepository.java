package tech.danilo.wallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.danilo.wallet.entity.WalletType;

public interface WalletTypeRepository extends JpaRepository<WalletType, Long> {
}
