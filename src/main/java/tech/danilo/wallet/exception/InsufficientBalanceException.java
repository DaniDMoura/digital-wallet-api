package tech.danilo.wallet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class InsufficientBalanceException extends WalletException {
    @Override
    public ProblemDetail toProblemDetail() {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_CONTENT);
        problemDetail.setTitle("Insufficient balance");
        problemDetail.setDetail("You cannot transfer a value bigger than your current balance.");

        return problemDetail;
    }
}
