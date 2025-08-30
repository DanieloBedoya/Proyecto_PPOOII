package com.Proyecto.PPOOII.controller;

import com.Proyecto.PPOOII.models.Documento;
import com.Proyecto.PPOOII.service.DocumentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/documentos")
@RequiredArgsConstructor
public class DocumentoController {

    private final DocumentoService documentoService;

    // ✅ Crear documento
    @PostMapping
    public ResponseEntity<?> crearDocumento(@RequestBody Documento documento) {
        try {
            return ResponseEntity.ok(documentoService.crearDocumento(documento));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al crear documento: " + e.getMessage());
        }
    }

    // ✅ Listar todos
    @GetMapping
    public List<Documento> listarDocumentos() {
        return documentoService.listarDocumentos();
    }

    // ✅ Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(documentoService.buscarPorId(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ✅ Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarDocumento(@PathVariable Long id, @RequestBody Documento datos) {
        try {
            return ResponseEntity.ok(documentoService.actualizarDocumento(id, datos));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ✅ Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarDocumento(@PathVariable Long id) {
        try {
            documentoService.eliminarDocumento(id);
            return ResponseEntity.ok("Documento eliminado correctamente.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
