package tech.danilo.wallet.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import tech.danilo.wallet.controller.dto.TransferDto;
import tech.danilo.wallet.entity.Transfer;
import tech.danilo.wallet.entity.Wallet;
import tech.danilo.wallet.exception.InsufficientBalanceException;
import tech.danilo.wallet.exception.TransferNotAllowedForWalletTypeException;
import tech.danilo.wallet.exception.TransferNotAuthorizedException;
import tech.danilo.wallet.exception.WalletNotFoundException;
import tech.danilo.wallet.repository.TransferRepository;
import tech.danilo.wallet.repository.WalletRepository;

import java.util.concurrent.CompletableFuture;

@Service
public class TransferService {

    private final NotificationService notificationService;
    private final AuthorizationService authorizationService;
    private final TransferRepository transferRepository;
    private final WalletRepository walletRepository;

    public TransferService(NotificationService notificationService, AuthorizationService authorizationService, TransferRepository transferRepository, WalletRepository walletRepository) {
        this.notificationService = notificationService;
        this.authorizationService = authorizationService;
        this.transferRepository = transferRepository;
        this.walletRepository = walletRepository;
    }

    @Transactional
    public Transfer transfer(TransferDto transferDTO) {

        Wallet sender = walletRepository.findById(transferDTO.payer())
                .orElseThrow(() -> new WalletNotFoundException(transferDTO.payer()));

        Wallet receiver = walletRepository.findById(transferDTO.payee())
                .orElseThrow(() -> new WalletNotFoundException(transferDTO.payee()));

        validateTransfer(transferDTO, sender);

        sender.debit(transferDTO.value());
        receiver.credit(transferDTO.value());

        Transfer transfer = new Transfer(
                sender,
                receiver,
                transferDTO.value()
        );

        walletRepository.save(receiver);
        walletRepository.save(sender);
        Transfer savedTransfer = transferRepository.save(transfer);

        CompletableFuture.runAsync(() -> notificationService.sendNotification(savedTransfer));

        return savedTransfer;
    }

    private void validateTransfer(TransferDto transferDto, Wallet sender) {

        if (!sender.isTransferAllowedForWalletType()) {
            throw new TransferNotAllowedForWalletTypeException();
        }

        if (!sender.isBalancerEqualOrGreaterThan(transferDto.value())) {
            throw new InsufficientBalanceException();
        }

        if (!authorizationService.isAuthorized(transferDto)) {
            throw new TransferNotAuthorizedException();
        }


    }

}
