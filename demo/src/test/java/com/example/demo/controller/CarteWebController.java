package com.example.demo.controller;

import com.example.demo.entity.Carte;
import com.example.demo.repository.CarteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CarteWebController {

    @Autowired
    private CarteRepository carteRepository;

    //afisare lista de carti
    @GetMapping("/lista-carti")
    public String listaCarti(Model model)
    {
        List<Carte> carti = carteRepository.findAll();
        model.addAttribute("carti", carti);
        model.addAttribute("mesaj", "Lista cărților preluate prin repository.");
        return "carti";
    }

    //lucrarea cu operatiile crud.
    @PostMapping("/operatii")
    public String operatii(
            @RequestParam(value = "isbn", required = false) String isbn,
            @RequestParam(value = "titlu", required = false) String titlu,
            @RequestParam(value = "autor", required = false) String autor,
            @RequestParam(value = "adauga", required = false) String adauga,
            @RequestParam(value = "modifica", required = false) String modifica,
            @RequestParam(value = "sterge", required = false) String sterge,
            @RequestParam(value = "filtreaza", required = false) String filtreaza,
            Model model) {

        String mesaj = "";

        if (adauga != null)
        {
       //adaugare carte
            if (isbn != null && !isbn.isEmpty() && titlu != null && !titlu.isEmpty() && autor != null && !autor.isEmpty())
            {
                carteRepository.save(new Carte());
                mesaj = "Adaugare cu succes!";
            } else {
                mesaj = "Adaugarea nu s-a putut realiza";
            }
        } else if (modifica != null) {
            // Modificare carte
            if (carteRepository.existsById(isbn))
            {
                carteRepository.save(new Carte());
                mesaj = "Cartea cu ISBN-ul " + isbn + " a fost modificata!";
            } else {
                mesaj = "Cartea NU a fost gasita";
            }
        } else if (sterge != null)
        {
            //stergere carte
            if (carteRepository.existsById(isbn))
            {
                carteRepository.deleteById(isbn);
                mesaj = "Cartea cu ISBN-ul " + isbn + " a fost stearsa";
            } else {
                mesaj = "Cartea NU a fost gasita.";
            }
        } else if (filtreaza != null)
        {
            //filtrare dupa autor
            List<Carte> carti;
            if (autor != null && !autor.isEmpty())
            {

                carti = carteRepository.findByAutor(autor);
                mesaj = "Cartile apartinand urmatorului autor:" + autor + ".";
            } else
            {
                carti = carteRepository.findAll();

            }
            model.addAttribute("carti", carti);
        }

        //reincarcare lista de carti
        model.addAttribute("carti", carteRepository.findAll());
        model.addAttribute("mesaj", mesaj);

        return "carti";
    }
}
