package com.example.demo.repository;

import com.example.demo.entity.Carte; //pt import enitatea Carte
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CarteRepository extends JpaRepository<Carte, String>
{

    List<Carte> findByAutor(String autor);  //gasire o carte dupa autor
}
