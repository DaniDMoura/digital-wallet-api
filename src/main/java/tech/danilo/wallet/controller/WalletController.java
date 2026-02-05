package tech.danilo.wallet.controller;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.danilo.wallet.controller.dto.CreateWalletDto;
import tech.danilo.wallet.entity.Wallet;
import tech.danilo.wallet.service.WalletService;

@RestController
@RequestMapping("/wallets")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping
    public ResponseEntity<Wallet> createWallet(@RequestBody @Valid CreateWalletDto walletDto) {
        return ResponseEntity.status(HttpStatus.OK).body(
                walletService.createWallet(walletDto)
        );
    }
}
