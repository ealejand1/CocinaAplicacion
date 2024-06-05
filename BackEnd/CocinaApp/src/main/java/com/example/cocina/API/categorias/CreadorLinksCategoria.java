package com.example.cocina.API.categorias;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.cocina.API.receta.*;

@Component
public class CreadorLinksCategoria implements RepresentationModelAssembler<Categoria, EntityModel<Categoria>> {

    @Override
    public EntityModel<Categoria> toModel(Categoria categoria) {
        return EntityModel.of(categoria,
                linkTo(methodOn(ControladorCategorias.class).obtenerCategoriaPorId(categoria.getId())).withSelfRel(),
                linkTo(methodOn(ControladorCategorias.class).obtenerCategorias()).withRel("categorias"),
                linkTo(methodOn(ControladorRecetas.class).obtenerRecetasPorCategoria(categoria.getId())).withRel("recetas")
        );
    }
}
