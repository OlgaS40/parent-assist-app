package com.parentapp.doubles;

import com.parentapp.subscription.Subscription;
import com.parentapp.subscription.SubscriptionRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class SubscriptionRepositorySpy implements SubscriptionRepository {
    private int invocationsOf_findAll;
    private int invocationsOf_findById;
    private int invocationsOf_save;
    private int invocationsOf_delete;
    private int invocationsOf_existsByIdIgnoreCase;

    public int getInvocationsOf_findAll() {
        return invocationsOf_findAll;
    }

    public int getInvocationsOf_findById() {
        return invocationsOf_findById;
    }

    public int getInvocationsOf_save() {
        return invocationsOf_save;
    }

    public int getInvocationsOf_delete() {
        return invocationsOf_delete;
    }

    public int getInvocationsOf_existsByIdIgnoreCase() {
        return invocationsOf_existsByIdIgnoreCase;
    }

    @Override
    public List<Subscription> findAll() {
        invocationsOf_findAll++;
        return List.of();
    }

    @Override
    public Optional<Subscription> findById(String s) {
        invocationsOf_findById++;
        return Optional.of(new Subscription());
    }

    @Override
    public <S extends Subscription> S save(S entity) {
        invocationsOf_save++;
        return entity;
    }
    @Override
    public void deleteById(String s) {
        invocationsOf_delete++;
    }
    @Override
    public boolean existsByIdIgnoreCase(String id) {
        invocationsOf_existsByIdIgnoreCase++;
        return true;
    }

    @Override
    public <S extends Subscription> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public List<Subscription> findAllById(Iterable<String> strings) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void delete(Subscription entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends Subscription> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Subscription> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Subscription> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Subscription> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<String> strings) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Subscription getOne(String s) {
        return null;
    }

    @Override
    public Subscription getById(String s) {
        return null;
    }

    @Override
    public Subscription getReferenceById(String s) {
        return null;
    }

    @Override
    public <S extends Subscription> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Subscription> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Subscription> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Subscription> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Subscription> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Subscription> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Subscription, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public List<Subscription> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Subscription> findAll(Pageable pageable) {
        return null;
    }
}
