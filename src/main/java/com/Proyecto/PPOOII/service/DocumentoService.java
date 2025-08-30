package com.Proyecto.PPOOII.service;

import com.Proyecto.PPOOII.models.Documento;
import com.Proyecto.PPOOII.repository.DocumentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentoService {

    private final DocumentoRepository documentoRepository;

    @Transactional
    public Documento crearDocumento(Documento documento) {
        return documentoRepository.save(documento);
    }

    public List<Documento> listarDocumentos() {
        return documentoRepository.findAll();
    }

    public Documento buscarPorId(Long id) {
        return documentoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Documento no encontrado con ID: " + id));
    }

    @Transactional
    public Documento actualizarDocumento(Long id, Documento datos) {
        Documento existente = buscarPorId(id);

        existente.setCodigo(datos.getCodigo());
        existente.setNombre(datos.getNombre());
        existente.setTipoVehiculo(datos.getTipoVehiculo());
        existente.setObligatoriedad(datos.getObligatoriedad());
        existente.setDescripcion(datos.getDescripcion());

        return documentoRepository.save(existente);
    }

    @Transactional
    public void eliminarDocumento(Long id) {
        Documento existente = buscarPorId(id);
        documentoRepository.delete(existente);
    }

}

