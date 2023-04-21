package br.com.register.part.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_ROLE")
    @SequenceGenerator(name = "SQ_ROLE", sequenceName = "SQ_ROLE", allocationSize = 1)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;
}
