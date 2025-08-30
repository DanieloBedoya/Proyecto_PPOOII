package com.Proyecto.PPOOII.service;

import com.Proyecto.PPOOII.enums.EstadoDocumento;
import com.Proyecto.PPOOII.enums.TipoVehiculo;
import com.Proyecto.PPOOII.models.*;
import com.Proyecto.PPOOII.repository.DocumentoRepository;
import com.Proyecto.PPOOII.repository.VehiculoDocumentoRepository;
import com.Proyecto.PPOOII.repository.VehiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehiculoService {

    private final VehiculoRepository vehiculoRepository;
    private final DocumentoRepository documentoRepository;
    private final VehiculoDocumentoRepository vehiculoDocumentoRepository;

    @Transactional
    public Vehiculo crearVehiculoConDocumentos(Vehiculo vehiculo, List<Long> documentosIds) {
        if (documentosIds == null || documentosIds.isEmpty()) {
            throw new IllegalArgumentException("El vehículo debe tener al menos un documento asociado.");
        }

        List<Documento> docsSeleccionados = documentoRepository.findAllById(documentosIds);

        // ✅ Validar documentos obligatorios según tipo de vehículo
        if (vehiculo.getTipoVehiculo().equals(TipoVehiculo.AUTOMOVIL)) {
            boolean faltante = documentoRepository.findAll().stream()
                    .filter(d -> d.getObligatoriedad().equals("RA") || d.getObligatoriedad().equals("RR"))
                    .anyMatch(req -> docsSeleccionados.stream().noneMatch(d -> d.getId().equals(req.getId())));
            if (faltante) {
                throw new IllegalArgumentException("Faltan documentos obligatorios para automóvil.");
            }
        }

        if (vehiculo.getTipoVehiculo().equals(TipoVehiculo.MOTOCICLETA)) {
            boolean faltante = documentoRepository.findAll().stream()
                    .filter(d -> d.getObligatoriedad().equals("RM") || d.getObligatoriedad().equals("RR"))
                    .anyMatch(req -> docsSeleccionados.stream().noneMatch(d -> d.getId().equals(req.getId())));
            if (faltante) {
                throw new IllegalArgumentException("Faltan documentos obligatorios para motocicleta.");
            }
        }

        // ✅ Guardar vehículo
        Vehiculo nuevo = vehiculoRepository.save(vehiculo);

        // ✅ Asociar documentos con estado EN_VERIFICACION
        for (Documento doc : docsSeleccionados) {
            VehiculoDocumento relacion = VehiculoDocumento.builder()
                    .vehiculo(nuevo)
                    .documento(doc)
                    .fechaExpedicion(null)
                    .fechaVencimiento(null)
                    .estado(EstadoDocumento.EN_VERIFICACION)
                    .build();
            vehiculoDocumentoRepository.save(relacion);
        }

        return nuevo;
    }

    public List<Vehiculo> listarVehiculos() {
        return vehiculoRepository.findAll();
    }

    public Vehiculo buscarPorPlaca(String placa) {
        return vehiculoRepository.findByPlaca(placa)
                .orElseThrow(() -> new IllegalArgumentException("Vehículo no encontrado con placa: " + placa));
    }

    public Vehiculo buscarPorId(Long id) {
        return vehiculoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vehículo no encontrado con ID: " + id));
    }

    @Transactional
    public Vehiculo actualizarVehiculo(Long id, Vehiculo datos) {
        Vehiculo existente = buscarPorId(id);

        existente.setTipoVehiculo(datos.getTipoVehiculo());
        existente.setPlaca(datos.getPlaca());
        existente.setTipoServicio(datos.getTipoServicio());
        existente.setCombustible(datos.getCombustible());
        existente.setCapacidadPasajeros(datos.getCapacidadPasajeros());
        existente.setColor(datos.getColor());
        existente.setModelo(datos.getModelo());
        existente.setMarca(datos.getMarca());
        existente.setLinea(datos.getLinea());

        return vehiculoRepository.save(existente);
    }

    @Transactional
    public void eliminarVehiculo(Long id) {
        Vehiculo existente = buscarPorId(id);
        vehiculoRepository.delete(existente);
    }

    public List<Vehiculo> buscarPorTipo(TipoVehiculo tipoVehiculo) {
        return vehiculoRepository.findByTipoVehiculo(tipoVehiculo);
    }

    public List<Vehiculo> buscarPorDocumento(Long documentoId) {
        return vehiculoRepository.findByDocumento(documentoId);
    }

    public List<Vehiculo> buscarPorEstadoDocumento(EstadoDocumento estado) {
        return vehiculoRepository.findByEstadoDocumento(estado);
    }

    @Transactional
    public Vehiculo agregarDocumentoAVehiculo(Long vehiculoId, Long documentoId) {
        Vehiculo vehiculo = vehiculoRepository.findById(vehiculoId)
                .orElseThrow(() -> new IllegalArgumentException("Vehículo no encontrado con ID: " + vehiculoId));

        Documento documento = documentoRepository.findById(documentoId)
                .orElseThrow(() -> new IllegalArgumentException("Documento no encontrado con ID: " + documentoId));

        // Validar que no esté ya asociado
        boolean yaAsociado = vehiculo.getDocumentos().stream()
                .anyMatch(vd -> vd.getDocumento().getId().equals(documentoId));

        if (yaAsociado) {
            throw new IllegalArgumentException("El documento ya está asociado a este vehículo.");
        }

        // Crear nueva relación Vehiculo-Documento
        VehiculoDocumento relacion = new VehiculoDocumento();
        relacion.setVehiculo(vehiculo);
        relacion.setDocumento(documento);
        relacion.setEstado(EstadoDocumento.EN_VERIFICACION);
        relacion.setFechaExpedicion(null);
        relacion.setFechaVencimiento(null);

        vehiculo.getDocumentos().add(relacion);

        return vehiculoRepository.save(vehiculo);
    }

}

