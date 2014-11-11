package yukinami.example.saas.domain;

import javax.persistence.*;

/**
 * Created by snowblink on 14/11/10.
 */
@Entity
@Table(name="inventory_vw")
public class Inventory {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
