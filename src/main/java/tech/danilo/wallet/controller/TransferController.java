package tech.danilo.wallet.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.danilo.wallet.controller.dto.TransferDto;
import tech.danilo.wallet.entity.Transfer;
import tech.danilo.wallet.service.TransferService;

@RestController
public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping("/transfer")
    public ResponseEntity<Transfer> transfer(@RequestBody @Valid TransferDto transferDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(
                transferService.transfer(transferDTO)
        );
    }
}
