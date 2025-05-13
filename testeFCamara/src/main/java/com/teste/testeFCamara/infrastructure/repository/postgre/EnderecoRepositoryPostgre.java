package com.teste.testeFCamara.infrastructure.repository.postgre;

import com.teste.testeFCamara.domain.entity.Endereco;
import com.teste.testeFCamara.infrastructure.repository.EnderecoRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class EnderecoRepositoryPostgre implements EnderecoRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Endereco salvar(Endereco endereco) {
        em.persist(endereco);
        return endereco;
    }

}