package com.Proyecto.PPOOII.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "documentos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Documento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigo;
    private String nombre;
    private String tipoVehiculo; // A, M, AM

    // RA, RM, RR = Requeridos
    // OA, OM, OO = Opcionales
    private String obligatoriedad;
    private String descripcion;

    @OneToMany(mappedBy = "documento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VehiculoDocumento> vehiculos = new ArrayList<>();
}

