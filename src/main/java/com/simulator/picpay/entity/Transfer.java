package com.simulator.picpay.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "tb_transfer")
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "wallet_sender_id")
    private Wallet sender;

    @ManyToOne
    @JoinColumn(name = "wallet_receiver_id")
    private Wallet receiver;

    @Column(name = "value")
    private BigDecimal values;


    public Transfer() {}

    public Transfer(Wallet sender, Wallet receiver, BigDecimal values) {
        this.sender = sender;
        this.receiver = receiver;
        this.values = values;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Wallet getReceiver() {
        return receiver;
    }

    public void setReceiver(Wallet receiver) {
        this.receiver = receiver;
    }

    public Wallet getSender() {
        return sender;
    }

    public void setSender(Wallet sender) {
        this.sender = sender;
    }

    public BigDecimal getValues() {
        return values;
    }

    public void setValues(BigDecimal values) {
        this.values = values;
    }
}

