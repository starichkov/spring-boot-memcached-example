package org.starichkov.java.spring.memcached.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.starichkov.java.spring.memcached.domain.dto.MagazineDto;
import org.starichkov.java.spring.memcached.domain.service.MagazineService;

/**
 * @author Vadim Starichkov
 * @since 07.08.2020 16:09
 */
@RestController
@RequestMapping("/magazines")
public class MagazinesController {

    private final MagazineService service;

    @Autowired
    public MagazinesController(MagazineService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public MagazineDto getMagazine(@PathVariable("id") Long id) {
        return service.get(id);
    }

    @PostMapping(params = {"title", "issue"})
    public MagazineDto createMagazine(@RequestParam("title") String title, @RequestParam("issue") Integer issue) {
        MagazineDto magazine = new MagazineDto();
        magazine.setTitle(title);
        magazine.setIssue(issue);
        return service.create(magazine);
    }

    @PatchMapping(path = "/{id}", params = {"title"})
    public MagazineDto updateMagazine(@PathVariable("id") Long id, @RequestParam("title") String title) {
        MagazineDto magazine = service.get(id);
        magazine.setTitle(title);
        return service.update(magazine);
    }

    @DeleteMapping("/{id}")
    public void deleteMagazine(@PathVariable("id") Long id) {
        service.deleteById(id);
    }
}
