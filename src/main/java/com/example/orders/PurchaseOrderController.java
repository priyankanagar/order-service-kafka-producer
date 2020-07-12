package com.example.orders;

import java.util.HashMap;
import java.util.Map;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderService orderService;

    @PostMapping("/orders")
    public ResponseEntity<PurchaseOrder> createOrder(@Valid @RequestBody PurchaseOrder order) {
        return ResponseEntity.ok().body(this.orderService.createOrder(order));
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<PurchaseOrder> getOrderById(@PathVariable Long orderId) {
        return ResponseEntity.ok().body(this.orderService.getOrderById(orderId));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public Map<String, String> handleConstraintViolationExceptions(
            ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().stream().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }


}
