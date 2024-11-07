package com.simulator.picpay.service;

import com.simulator.picpay.controller.dto.TransferDto;
import com.simulator.picpay.entity.Transfer;

import com.simulator.picpay.entity.Wallet;
import com.simulator.picpay.exception.InsufficientBalanceException;
import com.simulator.picpay.exception.TransferNotAllowedForWalletTypeException;
import com.simulator.picpay.exception.TransferNotAuthorizedException;
import com.simulator.picpay.exception.WalletNotFoundException;
import com.simulator.picpay.repository.TransferRepository;
import com.simulator.picpay.repository.WalletRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class TransferService {

    private final TransferRepository transferRepository;
    private final AuthorizationService authorizationService;
    private final NotificationService notificationService;
    private final WalletRepository walletRepository;


    public TransferService(AuthorizationService authorizationService, TransferRepository transferRepository, NotificationService notificationService, WalletRepository walletRepository) {
        this.authorizationService = authorizationService;
        this.transferRepository = transferRepository;
        this.notificationService = notificationService;
        this.walletRepository = walletRepository;
    }

    @Transactional
    public Transfer transfer(TransferDto transferDto) {

        var sender = walletRepository.findById(transferDto.payer()).orElseThrow(() -> new WalletNotFoundException(transferDto.payer()));

        var receiver = walletRepository.findById(transferDto.payee()).orElseThrow(() -> new WalletNotFoundException(transferDto.payee()));

        validateTransfer(transferDto, sender);

        sender.debit(transferDto.value());

        receiver.credit(transferDto.value());

        var transfer = new Transfer(sender, receiver, transferDto.value());

        walletRepository.save(sender);
        walletRepository.save(receiver);
        var transferResult = transferRepository.save(transfer);

        CompletableFuture.runAsync(() -> notificationService.sendNotification(transferResult));

        return transferResult;

    }

    private void validateTransfer(TransferDto transferDto, Wallet sender) {

        if(!sender.isTransferAllowedForWalletType()) {
            throw new TransferNotAllowedForWalletTypeException();
        }

        if(!sender.isBalancerEqualOrGreaterThan(transferDto.value())) {
            throw new InsufficientBalanceException();
        }

        if(!authorizationService.isAuthorized(transferDto)) {
            throw new TransferNotAuthorizedException();
        }

    }
}
