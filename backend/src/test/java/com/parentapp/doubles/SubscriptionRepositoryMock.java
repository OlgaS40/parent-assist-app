package com.parentapp.doubles;

import com.parentapp.subscription.Subscription;
import com.parentapp.subscription.SubscriptionRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class SubscriptionRepositoryMock implements SubscriptionRepository {
    private List<Subscription> subscriptions = new ArrayList<>();

    @Override
    public List<Subscription> findAll() {
        return subscriptions;
    }

    @Override
    public Optional<Subscription> findById(String s) {
        return subscriptions.stream().filter(subs -> subs.getId().equals(s)).findFirst();
    }
    @Override
    public <S extends Subscription> S save(S entity) {
        if (subscriptions.contains(entity)) {
            int index = subscriptions.indexOf(entity);
            subscriptions.set(index, entity);
        } else {
            subscriptions.add(entity);
        }
        return entity;
    }
    @Override
    public void deleteById(String s) {
        subscriptions.removeIf(subscription -> subscription.getId().equals(s));
    }
    @Override
    public boolean existsByIdIgnoreCase(String id) {
        return subscriptions.stream().anyMatch(subscription -> subscription.getId().equals(id));
    }
    @Override
    public Subscription getById(String s) {
        return null;
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
    public Subscription getReferenceById(String s) {
        return null;
    }

    @Override
    public <S extends Subscription> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Subscription> List<S> findAll(Example<S> example) {
        return null;
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
    public List<Subscription> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Subscription> findAll(Pageable pageable) {
        return null;
    }
}
