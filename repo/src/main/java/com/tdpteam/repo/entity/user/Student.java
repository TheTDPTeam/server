package com.tdpteam.repo.entity.user;

import com.tdpteam.repo.entity.BClass;
import com.tdpteam.repo.entity.Batch;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "student")
public class Student {

    @Id
    private Long id;

    @OneToOne
    @JoinColumn(name = "account_id")
    @MapsId
    @NonNull
    private Account account;

    @ManyToMany(mappedBy = "students")
    private Set<BClass> bClasses = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "batch_id", nullable = false)
    private Batch batch;
}
