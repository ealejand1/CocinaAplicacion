package com.example.cocina.API.ingrediente;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.cocina.API.receta_ingrediente.*;

@Component
public class CreadorLinksIngrediente implements RepresentationModelAssembler<Ingrediente, EntityModel<Ingrediente>> {

    @Override
    public EntityModel<Ingrediente> toModel(Ingrediente ingrediente) {
        return EntityModel.of(ingrediente,
                linkTo(methodOn(ControladorIngredientes.class).obtenerIngredientePorId(ingrediente.getId())).withSelfRel(),
                linkTo(methodOn(ControladorIngredientes.class).obtenerIngredientes()).withRel("ingredientes"),
                linkTo(methodOn(ControladorRecetasIngredientes.class).obtenerRecetasPorIngrediente(ingrediente.id)).withRel("recetas")
        );
    }
}

