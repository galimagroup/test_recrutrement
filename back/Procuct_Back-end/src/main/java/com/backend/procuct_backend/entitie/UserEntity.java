package com.backend.procuct_backend.entitie;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 50)
    private String username;
    @Column(length = 50)
    private String firstname;
    @Column(length = 25)
    private String email;
    @Column(length = 100)
    private String password;
    @OneToMany(mappedBy = "user")
    private List<ProductEntity> productEntities;
}
