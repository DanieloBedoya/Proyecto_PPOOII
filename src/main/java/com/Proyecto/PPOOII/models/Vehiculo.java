package com.Proyecto.PPOOII.models;

import com.Proyecto.PPOOII.enums.TipoVehiculo;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vehiculos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoVehiculo tipoVehiculo;

    @Column(unique = true, nullable = false, length = 6)
    private String placa;

    private String tipoServicio;
    private String combustible;
    private int capacidadPasajeros;
    private String color; // Hexadecimal
    private int modelo;
    private String marca;
    private String linea;

    @OneToMany(mappedBy = "vehiculo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VehiculoDocumento> documentos = new ArrayList<>();
}


