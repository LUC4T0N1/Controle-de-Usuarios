package com.teste.testeFCamara.infrastructure.repository.postgre;

import com.teste.testeFCamara.domain.dto.UsuarioDto;
import com.teste.testeFCamara.domain.entity.Usuario;
import com.teste.testeFCamara.infrastructure.repository.UsuarioRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class UsuarioRepositoryPostgre implements UsuarioRepository {

    final int TAMANHO_PAGINA = 10;

    @PersistenceContext
    private EntityManager em;

    @Override
    public Usuario salvar(Usuario usuario) {
        em.persist(usuario);
        return usuario;
    }

    @Override
    public Optional<Usuario> buscarPorId(Long id) {
        return Optional.ofNullable(em.find(Usuario.class, id));
    }

    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        TypedQuery<Usuario> query = em.createQuery("SELECT u FROM Usuario u WHERE u.email = :email", Usuario.class);
        query.setParameter("email", email);
        return query.getResultList().stream().findFirst();
    }

    @Override
    public Page<UsuarioDto> listarPaginado(int pagina, int ordenacao) {
        String ordenacaoTipo = "";
        if(ordenacao == 1) ordenacaoTipo = "nome";
        else if (ordenacao == 2) ordenacaoTipo = "email";
        else ordenacaoTipo = "id";
        TypedQuery<Usuario> query = em.createQuery(
                "SELECT DISTINCT u FROM Usuario u LEFT JOIN FETCH u.enderecos ORDER BY u." + ordenacaoTipo, Usuario.class);
        query.setFirstResult(pagina * TAMANHO_PAGINA);
        query.setMaxResults(TAMANHO_PAGINA);
        List<Usuario> usuarios = query.getResultList();

        Long total = em.createQuery("SELECT COUNT(u) FROM Usuario u", Long.class)
                .getSingleResult();
        List<UsuarioDto> dtos = usuarios.stream().map(UsuarioDto::new).toList();
        return new PageImpl<>(dtos, PageRequest.of(pagina, TAMANHO_PAGINA), total);
    }

    @Override
    public void deletar(Long id) {
        Usuario usuario = em.find(Usuario.class, id);
        if (usuario != null) {
            em.remove(usuario);
        }
    }

}