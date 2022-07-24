package com.ilkinmehdiyev.usermanagement.model;

import com.ilkinmehdiyev.usermanagement.model.enums.OtpStatus;
import com.ilkinmehdiyev.usermanagement.model.enums.OtpType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

import static com.ilkinmehdiyev.usermanagement.model.Otp.TABLE_NAME;
import static javax.persistence.EnumType.STRING;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = TABLE_NAME)
public class Otp extends BaseEntity {
    public static final String TABLE_NAME = "otp";

    @NotBlank
    private String otp;

    @NotNull
    private int otpLifeTime;

    @NotNull
    private Timestamp creationTime;

    @NotNull
    @Enumerated(STRING)
    private OtpType type;

    @NotNull
    @Enumerated(STRING)
    private OtpStatus status;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @MapsId
    //    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
