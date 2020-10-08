package com.example.demo.service;
import com.amazonaws.services.apigateway.model.Op;
import com.example.demo.DTO.InfoMapaDTO;
import com.example.demo.domain.DadosLocal;
import com.example.demo.domain.InfoMapa;
import com.example.demo.domain.Usuario;
import com.example.demo.repository.DadosLocalDAO;
import com.example.demo.repository.InfoMapaDAO;
import com.example.demo.repository.UsuarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
public class InfoMapaService {
    @Autowired
    private InfoMapaDAO infoMapaDAO;

    @Autowired
    private DadosLocalDAO dadosLocalDAO;

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Autowired
    private S3Service s3Service;

    public void atualizarInfoMapa(long idInfoMapa, InfoMapa infoMapa) {
        if (infoMapaDAO.findById(idInfoMapa).isPresent()) {
            infoMapa.setIdInfoMapa(idInfoMapa);
            infoMapaDAO.save(infoMapa);
        }
    }

    public InfoMapa getById(long idInfoMapa) {
        Optional<InfoMapa> result = infoMapaDAO.findById(idInfoMapa);
        if (result.isPresent()) {
            return result.get();
        }
        else {
            return null;
        }
    }

    public List<InfoMapa> getAllInfoMapa() { return infoMapaDAO.findAll(); }

    public List<InfoMapa> getAllByDadosLocal(long idDadosLocal) {
        DadosLocal dadosLocal = new DadosLocal();
        dadosLocal.setIdDadosLocal(idDadosLocal);
        return infoMapaDAO.findAllByDadosLocal(dadosLocal);
    }

    public List<InfoMapa> getAllByUser (long idUsuario) {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(idUsuario);
        return infoMapaDAO.findAllByUsuario(usuario);
    }

    public List<InfoMapa> getAllByUserAndDados(long idUsuario, long idDadosLocal) {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(idUsuario);
        DadosLocal dadosLocal = new DadosLocal();
        dadosLocal.setIdDadosLocal(idDadosLocal);
        if (infoMapaDAO.findAllByDadosLocal(dadosLocal).size() > 0 && infoMapaDAO.findAllByUsuario(usuario).size() > 0) {
            return infoMapaDAO.findByUsuarioAndDadosLocal(usuario, dadosLocal);
        }
        return null;
    }

    public String salvarInfoMapa(String observacao, long idUsuario, long idDadosLocal, MultipartFile multipartFile) {
        Optional<DadosLocal> dadosLocalOptional = dadosLocalDAO.findById(idDadosLocal);
        DadosLocal dadosLocal;
        if (dadosLocalOptional.isPresent()){
            dadosLocal = dadosLocalOptional.get();
        } else {
            return "id dados local inválido";
        }

        Optional<Usuario> usuarioOptional = usuarioDAO.findById(idUsuario);
        Usuario usuario;
        if (usuarioOptional.isPresent()) {
            usuario = usuarioOptional.get();
        } else {
            return "id usuário inválido";
        }

        URI uri = s3Service.enviarArquivo(multipartFile);

        infoMapaDAO.save(new InfoMapa(null, observacao, uri.toString(), multipartFile.getOriginalFilename(), dadosLocal, usuario));
        return "Sucesso";
    }
}
