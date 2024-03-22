package com.bachelor.thesis.organization_education.dto.user;

import com.bachelor.thesis.organization_education.dto.abstract_type.BaseTableInfo;
import com.bachelor.thesis.organization_education.enums.TokenType;
import com.bachelor.thesis.organization_education.responces.user.RegisteredResponse;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

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
@Table(name = "tokens")
public class Token extends BaseTableInfo {
    @Column(unique = true)
    private String accessToken;

    @Enumerated(EnumType.STRING)
    private TokenType tokenType = TokenType.BEARER;

    private boolean revoked;

    private boolean expired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public RegisteredResponse getResponse() {
        var responseBuilder = RegisteredResponse.builder();
        return super
                .responseBuilder(responseBuilder)
                .accessToken(this.accessToken)
                .username(this.user.getUsername())
                .build();
    }
}
