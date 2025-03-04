package kino.kinobackend.customer;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class CustomerModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private long customerId;

    private String name;
    private String email;
    private String phone;

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }
    public long getCustomerId() {
        return customerId;
    }
}
