package com.tdpteam.repo.entity.user;

import com.tdpteam.repo.entity.BClass;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table(name = "teacher")
public class Teacher {

    @Id
    private Long id;

    @OneToOne
    @JoinColumn(name = "account_id")
    @MapsId
    @NonNull
    private Account account;

    @OneToMany(mappedBy = "teacher")
    private Set<BClass> bClasses;
}
