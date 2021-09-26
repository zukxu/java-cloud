package com.zukxu.jpa.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

/**
 * @author xupu
 * @Description
 * @Date 2021-09-26 11:38
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity(name = "t_role")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String nameZh;

    @Override
    public boolean equals(Object o) {
        if(this == o)
            return true;
        if(o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        RoleEntity that = (RoleEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
