
package Jpa;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "paciente")
public class Paciente extends Base {

    private String nombre;
    private String apellido;
    private int edad;
    private int DNI;
    private String obraSocial;
    private LocalDate fechaDeNacimiento;
    private char sexo;
    public Paciente(String nombre, String apellido, int edad, int DNI,
                    String obraSocial, LocalDate fechaDeNacimiento, char sexo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.DNI = DNI;
        this.obraSocial = obraSocial;
        this.fechaDeNacimiento = fechaDeNacimiento;
        this.sexo = sexo;
    }


    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL)
    private List<Consulta> consultas = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "paciente_medicamento",
            joinColumns = @JoinColumn(name = "paciente_id"),
            inverseJoinColumns = @JoinColumn(name = "medicamento_id")
    )
    private List<Medicamento> medicamentos = new ArrayList<>();

    @OneToOne(mappedBy = "paciente", cascade = CascadeType.ALL)
    private HistoriaClinica historiaClinica;
}

