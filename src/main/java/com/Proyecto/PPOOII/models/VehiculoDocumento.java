package com.Proyecto.PPOOII.models;

import com.Proyecto.PPOOII.enums.EstadoDocumento;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "vehiculo_documento")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehiculoDocumento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vehiculo_id", nullable = false)
    private Vehiculo vehiculo;

    @ManyToOne
    @JoinColumn(name = "documento_id", nullable = false)
    private Documento documento;

    private LocalDate fechaExpedicion;
    private LocalDate fechaVencimiento;

    @Enumerated(EnumType.STRING)
    private EstadoDocumento estado;
}

