package tech.danilo.wallet.service;

import org.springframework.stereotype.Service;
import tech.danilo.wallet.controller.dto.CreateWalletDto;
import tech.danilo.wallet.entity.Wallet;
import tech.danilo.wallet.exception.WalletDataAlreadyExistsException;
import tech.danilo.wallet.repository.WalletRepository;

import java.util.Optional;

@Service
public class WalletService {

    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public Wallet createWallet(CreateWalletDto walletDto) {

        Optional<Wallet> walletDb = walletRepository.findByCpfCnpjOrEmail(walletDto.cpfCnpj(), walletDto.email());

        if (walletDb.isPresent()) {
            throw new WalletDataAlreadyExistsException("Cpf/Cnpj or Email already exists");
        }

        return walletRepository.save(walletDto.toWallet());
    }
}
