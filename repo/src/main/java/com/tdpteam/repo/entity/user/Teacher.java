package com.tdpteam.repo.entity.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tdpteam.repo.entity.BClass;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table(name = "teacher")
@NoArgsConstructor
public class Teacher {

    public Teacher(Account account) {
        this.account = account;
    }

    @Id
    private Long id;

    @OneToOne
    @JoinColumn(name = "account_id")
    @MapsId
    @NonNull
    private Account account;

    @OneToMany(mappedBy = "teacher")
    @JsonManagedReference
    private Set<BClass> bClasses;
}
