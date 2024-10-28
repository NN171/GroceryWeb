package edu.rut.grocery.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.util.List;

@NoRepositoryBean
public interface BaseRepository<T, ID> extends Repository<T, ID> {

    T findById();

    List<T> findAll();

    long count();

    <S extends T> boolean save(S entity);

    <S extends T> boolean saveAll(Iterable<S> entities);
}
