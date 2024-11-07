package com.simulator.picpay.controller.dto;

import com.simulator.picpay.entity.Wallet;
import com.simulator.picpay.entity.WalletType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateWalletDto(
        @NotBlank String fullName,
        @NotBlank String cpfCnpj,
        @NotBlank String email,
        @NotBlank String password,
        @NotNull WalletType.Enum walletType) {

    public Wallet toWallet() {
        return new Wallet(
                cpfCnpj,
                email,
                fullName,
                password,
                walletType.get()
        );
    }
}
