package com.tt.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Order {
    @Id
    @NonNull
    private String employeeName;
    private String customerPhone;
    private String customerName;
    private String customerAddress;
    private int totalQuantity;
    private int totalPrice;
    private int received;
    private int refunds;
    private String creation_date;
}
