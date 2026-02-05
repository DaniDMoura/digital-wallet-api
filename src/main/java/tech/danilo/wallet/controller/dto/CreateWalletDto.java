package tech.danilo.wallet.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import tech.danilo.wallet.entity.Wallet;
import tech.danilo.wallet.entity.WalletType;

public record CreateWalletDto(

    @NotBlank
    String fullName,

    @NotBlank
    String cpfCnpj,

    @NotBlank
    String email,

    @NotBlank
    String password,

    @NotNull
    WalletType.Enum walletType
) {
    public Wallet toWallet() {
        return new Wallet(
                fullName,
                cpfCnpj,
                email,
                password,
                walletType.getWalletType()
        );
    }
}
