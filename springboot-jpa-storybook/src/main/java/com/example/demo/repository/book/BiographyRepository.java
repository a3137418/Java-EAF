package com.example.demo.repository.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.book.Biography;

@Repository
public interface BiographyRepository extends JpaRepository<Biography, Integer>{

}
