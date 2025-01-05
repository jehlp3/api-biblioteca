package br.com.BuscaLivros.repository;


import br.com.BuscaLivros.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> findByNome(String name);

    // @Query("SELECT a FROM autor  WHERE a.anoMorte > :ano")
    List<Autor>findByAnoMorteGreaterThan(Integer ano);

}