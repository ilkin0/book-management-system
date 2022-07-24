package com.ilkinmehdiyev.usermanagement.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

import static com.ilkinmehdiyev.usermanagement.model.RefreshToken.TABLE_NAME;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = TABLE_NAME)
public class RefreshToken extends BaseEntity {
    public static final String TABLE_NAME = "refresh_token";

    private String username;

    private String token;

    private LocalDateTime expiryDate;

    private boolean valid;
}
