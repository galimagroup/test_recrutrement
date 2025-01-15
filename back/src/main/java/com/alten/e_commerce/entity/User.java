package com.alten.e_commerce.entity;

import com.alten.e_commerce.token.Token;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.ManyToAny;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
  name = "users",
  uniqueConstraints = { @UniqueConstraint(columnNames = { "email" }) }
)
public class User implements UserDetails{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String firstname;
  private String lastname;

  @Column(unique = true)
  private String email;

  @JsonIgnore
  private String password;


  @JsonIgnore
  @OneToMany(mappedBy = "user")
  private List<Token> tokens;

  private String photo;

  @JsonIgnore
  @OneToMany(mappedBy = "user")
  private Set<ShoppingCart> carts;

  @JsonIgnore
  @ManyToAny
  @JoinTable(
  name = "product_like", 
  joinColumns = @JoinColumn(name = "user_id"), 
  inverseJoinColumns = @JoinColumn(name = "product_id"))
  private Set<Product> likedProducts;



  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @JsonIgnore
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<SimpleGrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority(getEmail()));
    return authorities;
  }
}
