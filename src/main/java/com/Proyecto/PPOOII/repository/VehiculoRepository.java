package com.Proyecto.PPOOII.repository;

import com.Proyecto.PPOOII.models.Vehiculo;
import com.Proyecto.PPOOII.enums.TipoVehiculo;
import com.Proyecto.PPOOII.enums.EstadoDocumento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {

    Optional<Vehiculo> findByPlaca(String placa);

    List<Vehiculo> findByTipoVehiculo(TipoVehiculo tipoVehiculo);

    // Vehículos que tengan en común un documento específico
    @Query("SELECT vd.vehiculo FROM VehiculoDocumento vd WHERE vd.documento.id = :documentoId")
    List<Vehiculo> findByDocumento(@Param("documentoId") Long documentoId);

    // Vehículos según estado de documento asociado
    @Query("SELECT DISTINCT vd.vehiculo FROM VehiculoDocumento vd WHERE vd.estado = :estado")
    List<Vehiculo> findByEstadoDocumento(@Param("estado") EstadoDocumento estado);
}


