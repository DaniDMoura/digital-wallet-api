package tech.danilo.wallet.service;

import feign.FeignException;
import org.springframework.stereotype.Service;
import tech.danilo.wallet.client.AuthorizationClient;
import tech.danilo.wallet.controller.dto.TransferDto;

@Service
public class AuthorizationService {

    private final AuthorizationClient authorizationClient;

    public AuthorizationService(AuthorizationClient authorizationClient) {
        this.authorizationClient = authorizationClient;
    }

    public boolean isAuthorized(TransferDto transferDto) {
        try {
            var response = authorizationClient.isAuthroized();
            return response.getBody().data().authorization();
        } catch (FeignException.Forbidden e) {
            return false;
        }
    }
}
