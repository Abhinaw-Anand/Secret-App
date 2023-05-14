package com.example.SpringInitial.Model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;




@NoArgsConstructor
@Entity
@Table(name="secret_app_user_auth")
@Getter
@Builder
@AllArgsConstructor
public class SecretAppUserAuth implements UserDetails
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Setter
    @Column(unique = true, nullable = false)
    private String email;
    @Setter
    @Column(nullable = false)
    private String password;

    @UpdateTimestamp
    private LocalDateTime lastUpdatedDate;

    @CreationTimestamp
    private LocalDateTime createdDate;

    @Setter
    @OneToMany(mappedBy = "secretAppUserAuth",fetch = FetchType.EAGER)
    private List<Subscription> subscription;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Subscription value : this.subscription)
        {
            authorities.add(new SimpleGrantedAuthority(value.getSubscription()));
        }
        return authorities;
    }

    @Override
    public String getPassword()
    {
        return this.password;
    }

    @Override
    public String getUsername()
    {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    @Override
    public boolean isEnabled()
    {
        return true;
    }
}
