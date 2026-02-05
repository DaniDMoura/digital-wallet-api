package tech.danilo.wallet.client.dto;

public record AuthorizationResponse(
    String status,
    AuthorizationDataResponse data
) {
}
