package com.example.demo.DTO;

import com.example.demo.model.Commentaire;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateCommentdto {
    @NotBlank
    private String contenu;

    public CreateCommentdto() { }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }
}

