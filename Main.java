
package Jpa;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidad");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        // Pacientes
        Paciente p1 = new Paciente("Juan", "Pérez", 35, 12345678, "OSDE", LocalDate.of(1989, 1, 1), 'M');
        Paciente p2 = new Paciente("Ana", "López", 28, 23456789, "Swiss Medical", LocalDate.of(1996, 5, 10), 'F');
        Paciente p3 = new Paciente("Luis", "García", 40, 34567890, "OSDE", LocalDate.of(1984, 3, 15), 'M');

        // Médicos
        Medico m1 = new Medico("Dr. Pérez", "Clínica", null);
        Medico m2 = new Medico("Dra. Gómez", "Cardiología", null);

        // Consultas
        Consulta c1 = new Consulta(LocalDate.of(2024, 1, 10), "Gripe", p1, m1);
        Consulta c2 = new Consulta(LocalDate.of(2024, 2, 5), "Hipertensión", p2, m2);
        Consulta c3 = new Consulta(LocalDate.of(2024, 3, 12), "Resfrío", p3, m1);
        Consulta c4 = new Consulta(LocalDate.of(2024, 4, 20), "Dolor de pecho", p1, m2);

        p1.getConsultas().add(c1);
        p1.getConsultas().add(c4);
        p2.getConsultas().add(c2);
        p3.getConsultas().add(c3);

        // Historias clínicas
        HistoriaClinica h1 = new HistoriaClinica("Historia clínica de Juan", p1);
        HistoriaClinica h2 = new HistoriaClinica("Historia clínica de Ana", p2);

        p1.setHistoriaClinica(h1);
        p2.setHistoriaClinica(h2);

        // Medicamentos
        Medicamento med1 = new Medicamento("Paracetamol", null);
        Medicamento med2 = new Medicamento("Ibuprofeno", null);
        Medicamento med3 = new Medicamento("Amoxicilina", null);

        p1.getMedicamentos().add(med1);
        p1.getMedicamentos().add(med2);
        p2.getMedicamentos().add(med3);
        p3.getMedicamentos().add(med1);

        em.persist(p1);
        em.persist(p2);
        em.persist(p3);
        em.persist(m1);
        em.persist(m2);
        em.persist(c1);
        em.persist(c2);
        em.persist(c3);
        em.persist(c4);
        em.persist(h1);
        em.persist(h2);
        em.persist(med1);
        em.persist(med2);
        em.persist(med3);

        em.getTransaction().commit();

        // ---- CONSULTAS ----

        // 1. Pacientes mayores de 30 años
        System.out.println("\nPacientes mayores de 30 años:");
        List<Paciente> mayores30 = em.createQuery(
                        "SELECT p FROM Paciente p WHERE p.edad > 30", Paciente.class)
                .getResultList();
        mayores30.forEach(p -> System.out.println(p.getNombre()));

        // 2. Consultas realizadas por un médico específico
        System.out.println("\nConsultas del Dr. Pérez:");
        List<Consulta> consultas = em.createQuery(
                        "SELECT c FROM Consulta c WHERE c.medico.nombre = :nombre", Consulta.class)
                .setParameter("nombre", "Dr. Pérez")
                .getResultList();
        consultas.forEach(c -> System.out.println(c.getDiagnostico()));

        // 3. Medicamentos de un paciente
        System.out.println("\nMedicamentos de Juan:");
        List<Medicamento> meds = em.createQuery(
                        "SELECT m FROM Paciente p JOIN p.medicamentos m WHERE p.nombre = :nombre", Medicamento.class)
                .setParameter("nombre", "Juan")
                .getResultList();
        meds.forEach(m -> System.out.println(m.getNombre()));

        // 4. Consultas con diagnóstico y paciente
        System.out.println("\nConsultas con paciente:");
        List<Object[]> lista1 = em.createQuery(
                        "SELECT c.diagnostico, c.paciente.nombre FROM Consulta c")
                .getResultList();
        lista1.forEach(o -> System.out.println(o[0] + " - " + o[1]));

        // 5. Promedio de edad de pacientes
        Double promedio = em.createQuery(
                        "SELECT AVG(p.edad) FROM Paciente p", Double.class)
                .getSingleResult();
        System.out.println("\nPromedio de edad: " + promedio);

        // 6. Pacientes con obra social específica
        System.out.println("\nPacientes con OSDE:");
        List<Paciente> pacientesOSDE = em.createQuery(
                        "SELECT p FROM Paciente p WHERE p.obraSocial = :os", Paciente.class)
                .setParameter("os", "OSDE")
                .getResultList();
        pacientesOSDE.forEach(p -> System.out.println(p.getNombre()));

        // 7. Médicos y cantidad de consultas
        System.out.println("\nMédicos y cantidad de consultas:");
        List<Object[]> resultados = em.createQuery(
                        "SELECT m.nombre, COUNT(c) FROM Medico m JOIN m.consultas c GROUP BY m.nombre")
                .getResultList();
        resultados.forEach(r -> System.out.println(r[0] + " -> " + r[1]));

        // 8. Pacientes con historia clínica
        System.out.println("\nPacientes con historia clínica:");
        List<Object[]> lista2 = em.createQuery(
                        "SELECT p.nombre, h.descripcion FROM Paciente p JOIN p.historiaClinica h")
                .getResultList();
        lista2.forEach(o -> System.out.println(o[0] + ": " + o[1]));

        em.close();
        emf.close();
    }
}
