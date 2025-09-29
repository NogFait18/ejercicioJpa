
package Jpa;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "medicamento")
public class Medicamento extends Base {

    private String nombre;

    @ManyToMany(mappedBy = "medicamentos")
    private List<Paciente> pacientes = new ArrayList<>();
}

