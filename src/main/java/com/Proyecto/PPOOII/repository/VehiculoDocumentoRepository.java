package com.Proyecto.PPOOII.repository;

import com.Proyecto.PPOOII.enums.EstadoDocumento;
import com.Proyecto.PPOOII.models.VehiculoDocumento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehiculoDocumentoRepository extends JpaRepository<VehiculoDocumento, Long> {
    List<VehiculoDocumento> findByEstado(EstadoDocumento estado);
}

