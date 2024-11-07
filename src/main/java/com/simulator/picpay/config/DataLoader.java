package com.simulator.picpay.config;

import com.simulator.picpay.entity.WalletType;
import com.simulator.picpay.repository.WallatTypeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class DataLoader implements CommandLineRunner {

    private final WallatTypeRepository wallatTypeRepository;

    public DataLoader(WallatTypeRepository wallatTypeRepository) {
        this.wallatTypeRepository = wallatTypeRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Arrays.stream(WalletType.Enum.values())
                .forEach(walletType -> wallatTypeRepository.save(walletType.get()));
    }

}
