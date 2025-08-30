package com.Proyecto.PPOOII.controller;

import com.Proyecto.PPOOII.models.Vehiculo;
import com.Proyecto.PPOOII.service.VehiculoService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehiculos")
@RequiredArgsConstructor
public class VehiculoController {

    private final VehiculoService vehiculoService;

    // ✅ Crear vehículo con documentos
    @PostMapping
    public ResponseEntity<?> crearVehiculo(@RequestBody VehiculoRequest request) {
        try {
            Vehiculo nuevo = vehiculoService.crearVehiculoConDocumentos(
                    request.getVehiculo(),
                    request.getDocumentosIds()
            );
            return ResponseEntity.ok(nuevo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al crear vehículo: " + e.getMessage());
        }
    }

    // ✅ Listar todos los vehículos
    @GetMapping
    public List<Vehiculo> listarVehiculos() {
        return vehiculoService.listarVehiculos();
    }


    // ✅ Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(vehiculoService.buscarPorId(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ✅ Actualizar vehículo
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarVehiculo(@PathVariable Long id, @RequestBody Vehiculo datos) {
        try {
            return ResponseEntity.ok(vehiculoService.actualizarVehiculo(id, datos));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ✅ Eliminar vehículo
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarVehiculo(@PathVariable Long id) {
        try {
            vehiculoService.eliminarVehiculo(id);
            return ResponseEntity.ok("Vehículo eliminado correctamente.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ✅ Buscar por placa
    @GetMapping("/placa/{placa}")
    public ResponseEntity<?> buscarPorPlaca(@PathVariable String placa) {
        try {
            return ResponseEntity.ok(vehiculoService.buscarPorPlaca(placa));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 🔎 Buscar vehículos por tipo
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<?> buscarPorTipo(@PathVariable String tipo) {
        try {
            return ResponseEntity.ok(
                    vehiculoService.buscarPorTipo(Enum.valueOf(com.Proyecto.PPOOII.enums.TipoVehiculo.class, tipo.toUpperCase()))
            );
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Tipo de vehículo inválido. Use AUTOMOVIL o MOTOCICLETA.");
        }
    }

    // 🔎 Buscar vehículos que tengan un documento en común
    @GetMapping("/documento/{documentoId}")
    public ResponseEntity<?> buscarPorDocumento(@PathVariable Long documentoId) {
        return ResponseEntity.ok(vehiculoService.buscarPorDocumento(documentoId));
    }

    // 🔎 Buscar vehículos según estado de documento
    @GetMapping("/documento/estado/{estado}")
    public ResponseEntity<?> buscarPorEstadoDocumento(@PathVariable String estado) {
        try {
            return ResponseEntity.ok(
                    vehiculoService.buscarPorEstadoDocumento(Enum.valueOf(com.Proyecto.PPOOII.enums.EstadoDocumento.class, estado.toUpperCase()))
            );
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Estado inválido. Use: HABILITADO, VENCIDO o EN_VERIFICACION.");
        }
    }

        // ✅ Agregar un documento a un vehículo existente
    @PostMapping("/{vehiculoId}/documentos/{documentoId}")
    public ResponseEntity<?> agregarDocumentoAVehiculo(
            @PathVariable Long vehiculoId,
            @PathVariable Long documentoId) {
        try {
            return ResponseEntity.ok(vehiculoService.agregarDocumentoAVehiculo(vehiculoId, documentoId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}

// DTO para request
@Data
class VehiculoRequest {
    private Vehiculo vehiculo;
    private List<Long> documentosIds;
}



