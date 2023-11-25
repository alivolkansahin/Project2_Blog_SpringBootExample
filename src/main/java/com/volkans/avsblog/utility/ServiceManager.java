package com.volkans.avsblog.utility;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public class ServiceManager<T,ID> implements IService<T,ID>{

    private final JpaRepository<T,ID> jpaRepository;

    public ServiceManager(JpaRepository<T, ID> jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public T save(T t) {
        return jpaRepository.save(t);
    }

    @Override
    public T saveAndFlush(T t) {
        return jpaRepository.saveAndFlush(t);
    }

    @Override
    public Iterable<T> saveAll(Iterable<T> t) {
        return jpaRepository.saveAll(t);
    }

    @Override
    public Iterable<T> saveAllAndFlush(Iterable<T> t) {
        return jpaRepository.saveAllAndFlush(t);
    }

    @Override
    public T update(T t) {
        return jpaRepository.save(t); // verilen entityde id bilgisi yoksa save methodu kaydediyor, id bilgisi varsa gidiyor o idli entityi güncelliyor. böyle çalışıyor arka planda
    }

    @Override
    public void delete(T t) {
        jpaRepository.delete(t);
    }

    @Override
    public void deleteById(ID id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public Optional<T> findById(ID id) {
        return jpaRepository.findById(id);
    }

    @Override
    public List<T> findAll() {
        return jpaRepository.findAll();
    }
}
