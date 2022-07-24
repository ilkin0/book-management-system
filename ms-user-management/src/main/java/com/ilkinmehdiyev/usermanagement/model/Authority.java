package com.ilkinmehdiyev.usermanagement.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import static com.ilkinmehdiyev.usermanagement.model.Authority.TABLE_NAME;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = TABLE_NAME)
@EqualsAndHashCode(callSuper = true)
public class Authority extends BaseEntity implements GrantedAuthority {
    public static final String TABLE_NAME = "authority";

    @Column(nullable = false)
    private String authority;
}
