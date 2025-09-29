
package Jpa;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "historia_clinica")
public class HistoriaClinica extends Base {

    private String descripcion;

    @OneToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;
}


