package com.parentapp.doubles;

import com.parentapp.child.Child;
import com.parentapp.child.ChildRepository;
import com.parentapp.fixture.ChildFixture;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class ChildRepositoryStub implements ChildRepository {
    @Override
    public Optional<Child> findById(String s) {
        return Optional.of(ChildFixture.testChild());
    }
    @Override
    public boolean existsByIdIgnoreCase(String id) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Child> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Child> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Child> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<String> strings) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Child getOne(String s) {
        return null;
    }

    @Override
    public Child getById(String s) {
        return null;
    }

    @Override
    public Child getReferenceById(String s) {
        return null;
    }

    @Override
    public <S extends Child> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Child> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Child> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Child> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Child> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Child> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Child, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Child> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Child> List<S> saveAll(Iterable<S> entities) {
        return null;
    }



    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public List<Child> findAll() {
        return null;
    }

    @Override
    public List<Child> findAllById(Iterable<String> strings) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(String s) {

    }

    @Override
    public void delete(Child entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends Child> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Child> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Child> findAll(Pageable pageable) {
        return null;
    }
}
