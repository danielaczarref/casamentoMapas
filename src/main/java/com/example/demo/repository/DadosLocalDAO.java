package com.example.demo.repository;
import com.example.demo.domain.Cidade;
import com.example.demo.domain.DadosLocal;
import com.example.demo.domain.TipoLocal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DadosLocalDAO extends JpaRepository<DadosLocal,Long> {
    DadosLocal findDadosLocalByCepLocal(long cepLocal);
    DadosLocal findDadosLocalByCidade(Cidade cidade);
    List<DadosLocal> findAllByTipoLocal(TipoLocal tipoLocal);
    List <DadosLocal> findAllByCidade(Cidade cidade);
    List <DadosLocal> findAllByTipoLocalAndCidadeAndCepLocal (TipoLocal tipoLocal, Cidade cidade, long cepLocal);
}
