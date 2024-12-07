package edu.rut.grocery.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository<T, ID> extends Repository<T, ID> {

    Optional<T> findById(Long id);

    Page<T> findAll(Pageable pageable);

    long count();

    <S extends T> S save(S entity);

    <S extends T> boolean saveAll(Iterable<S> entities);
}
