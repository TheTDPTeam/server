package com.tdpteam.repo.entity.user;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity

@Table(name = "userDetail")
@ToString(exclude = "account")
@EqualsAndHashCode(exclude = "account")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class UserDetail {
    @Id
    private long id;

    @OneToOne
    @JoinColumn(name = "account_id")
    @MapsId
    @NonNull
    private Account account;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String phone;

    @Column
    private Date birthDate;

    @Column
    private String nationalId;

    @Column
    private String address;

    public String getFullName(){
        return getFirstName() + " " + getLastName();
    }
}
