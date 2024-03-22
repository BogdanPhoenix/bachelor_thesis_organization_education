package com.bachelor.thesis.organization_education.dto.user;

import com.bachelor.thesis.organization_education.dto.abstract_type.BaseTableInfo;
import com.bachelor.thesis.organization_education.enums.Role;
import com.bachelor.thesis.organization_education.responces.user.UserResponse;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

import static jakarta.persistence.CascadeType.*;

@Entity
@Getter
@Setter
@SuperBuilder
@DynamicUpdate
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Table(name = "registered_users")
public class User extends BaseTableInfo implements UserDetails {
    @NonNull
    @Column(name = "user_email", nullable = false, unique = true)
    private String username;

    @NonNull
    @EqualsAndHashCode.Exclude
    @Column(name = "password_user", length = 1000, nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(mappedBy = "user", cascade = {MERGE, REMOVE, REFRESH, DETACH}, fetch = FetchType.LAZY)
    private transient UserInfo infoUser;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "user", cascade = {MERGE, REMOVE, REFRESH, DETACH}, fetch = FetchType.LAZY)
    private transient Set<Token> tokens;

    @Override
    public UserResponse getResponse() {
        var responseBuilder = UserResponse.builder();
        return super
                .responseBuilder(responseBuilder)
                .username(this.username)
                .role(this.role)
                .build();
    }

    @Override
    public void onPrePersist(){
        super.onPrePersist();
        this.setEnabled(false);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.role.getAuthorities();
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
}
