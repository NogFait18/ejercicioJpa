
package Jpa;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "consulta")
public class Consulta extends Base {

    private LocalDate fecha;
    private String diagnostico;

    @ManyToOne
    private Paciente paciente;

    @ManyToOne
    private Medico medico;
}
