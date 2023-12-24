package com.tt.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    @Id
    @NonNull
    private String employeeName;
    private String productBarcode;
    private String productName;
    private int price;
    private int quantity;
    private int totalPrice;
    private String url_image;
}
