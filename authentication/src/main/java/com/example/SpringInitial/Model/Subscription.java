package com.example.SpringInitial.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "subscription")
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Subscription
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    String subscription;


    @ManyToOne
    private SecretAppUserAuth secretAppUserAuth;

}
