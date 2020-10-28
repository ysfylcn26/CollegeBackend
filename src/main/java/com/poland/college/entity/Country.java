package com.poland.college.entity;

import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "country", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name")
})
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class Country extends BaseEntitiy{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank
    @Size(max = 50)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "country", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<College> colleges = new HashSet<>();
}
