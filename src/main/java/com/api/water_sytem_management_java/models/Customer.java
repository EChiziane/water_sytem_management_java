package com.api.water_sytem_management_java.models;
import jakarta.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;
@Entity
@Table(name = "tb_customers")
public class Customer implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;
    private String contact;
    private String address;
    private CustomerStatus status;
    private Integer valve;
    private Integer monthsInDebt; // Total number of months in debt

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public CustomerStatus getStatus() {
        return status;
    }

    public void setStatus(CustomerStatus status) {
        this.status = status;
    }

    public Integer getValve() {
        return valve;
    }

    public void setValve(Integer valve) {
        this.valve = valve;
    }

    public Integer getMonthsInDebt() {
        return monthsInDebt;
    }

    public void setMonthsInDebt(Integer monthsInDebt) {
        this.monthsInDebt = monthsInDebt;
    }

    /*  @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
            private List<Payment> payments = new ArrayList<>();
        */
  boolean  isContactValid(){
    return true;
    }

    boolean isContactSizeEqual9(){
      return true;
    }

    boolean isContactStartBy8(){
      return true;
    }

    boolean isContactSecondDigitValid(){
      return true;
    }

}
