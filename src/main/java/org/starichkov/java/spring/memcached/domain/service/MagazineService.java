package org.starichkov.java.spring.memcached.domain.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.starichkov.java.spring.memcached.domain.dto.MagazineDto;
import org.starichkov.java.spring.memcached.domain.entity.Magazine;
import org.starichkov.java.spring.memcached.domain.mapper.MagazineMapper;
import org.starichkov.java.spring.memcached.domain.repository.MagazineRepository;
import org.starichkov.java.spring.memcached.config.CacheEvictionManager;
import org.starichkov.java.spring.memcached.config.Constants;

/**
 * @author Vadim Starichkov
 * @since 07.08.2020 16:05
 */
@Service
@Slf4j
public class MagazineService {

    private final MagazineRepository repository;
    private final CacheEvictionManager cacheEvictionManager;
    private final MagazineMapper mapper;

    @Autowired
    public MagazineService(MagazineRepository repository, CacheEvictionManager cacheEvictionManager) {
        this.repository = repository;
        this.cacheEvictionManager = cacheEvictionManager;
        this.mapper = MagazineMapper.INSTANCE;
    }

    @Cacheable(cacheNames = Constants.CACHE_MAGAZINES_ID, key = "#id", unless = "#result == null", cacheManager = "cacheManagerMgz")
    public MagazineDto get(Long id) {
        return mapper.map(repository.getOne(id));
    }

    public MagazineDto create(MagazineDto dto) {
        Magazine magazine = mapper.map(dto);
        return mapper.map(repository.save(magazine));
    }

    @CachePut(cacheNames = Constants.CACHE_MAGAZINES_ID, key = "#magazine.id", unless = "#result == null", cacheManager = "cacheManagerMgz")
    public MagazineDto update(MagazineDto magazine) {
        Magazine entity = repository.findById(magazine.getId())
                .orElseGet(() -> {
                    Magazine newMagazine = new Magazine();
                    newMagazine.setId(magazine.getId());
                    return newMagazine;
                });
        entity.setIssue(magazine.getIssue());
        entity.setTitle(magazine.getTitle());
        return mapper.map(repository.save(entity));
    }

    public void deleteById(Long id) {
        repository.findById(id).ifPresent(this::deleteMagazine);
    }

    private void deleteMagazine(Magazine magazine) {
//        repository.delete(magazine);
        cacheEvictionManager.evictMagazine(magazine.getId());
    }
}
