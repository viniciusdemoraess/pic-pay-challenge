package com.simulator.picpay.service;

import com.simulator.picpay.client.AuthorizationClient;
import com.simulator.picpay.controller.dto.TransferDto;
import com.simulator.picpay.exception.PicPayException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {

    public final AuthorizationClient authorizationClient;

    public AuthorizationService(AuthorizationClient authorizationClient) {
        this.authorizationClient = authorizationClient;
    }

    public boolean isAuthorized(TransferDto transfer) {
        var resp = authorizationClient.isAuthorized();

        if(resp.getStatusCode().isError()){
            throw new PicPayException();
        }

        return resp.getBody().authorized();

    }


}
