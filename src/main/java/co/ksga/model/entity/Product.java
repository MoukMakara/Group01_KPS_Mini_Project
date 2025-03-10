package co.ksga.model.entity;

import java.sql.Date;
import java.time.LocalDate;

public class Product {
    private Integer id;
    private String name;
    private double unitPrice;
    private int quantity;
    private LocalDate importedDate;

    public Product() {}

    public Product(Integer id, String name, Double unitPrice, Integer quantity, LocalDate importedDate) {
        this.id = id;
        this.name = name;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.importedDate = importedDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }


    public void setImportedDate(LocalDate importedDate) {
        this.importedDate = importedDate;
    }
    public LocalDate getImportedDate() {
        return importedDate;
    }
}
