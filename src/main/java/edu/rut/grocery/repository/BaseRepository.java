package edu.rut.grocery.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository<T, ID> extends Repository<T, ID> {

    Optional<T> findById(Long id);

    Optional<List<T>> findAll();

    long count();

    <S extends T> boolean save(S entity);

    <S extends T> boolean saveAll(Iterable<S> entities);
}
