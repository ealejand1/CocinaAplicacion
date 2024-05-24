package com.example.cocina.API.receta;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.cocina.API.categorias.ControladorCategorias;
import com.example.cocina.API.receta_ingrediente.ControladorRecetasIngredientes;
import com.example.cocina.API.valoraciones.ControladorValoraciones;
import com.example.cocina.user.ControladorUsuarios;

@Component
public class CreadorLinksReceta implements RepresentationModelAssembler<Receta, EntityModel<Receta>> {

    @Override
    public EntityModel<Receta> toModel(Receta receta) {
        return EntityModel.of(receta,
                linkTo(methodOn(ControladorRecetas.class).obtenerRecetaPorId(receta.getId())).withSelfRel(),
                linkTo(methodOn(ControladorRecetas.class).obtenerRecetas()).withRel("recetas"),
                linkTo(methodOn(ControladorUsuarios.class).obtenerUsuarioPorId(receta.getUsuario().getId())).withRel("usuario"),
                linkTo(methodOn(ControladorValoraciones.class).obtenerValoracionesPorReceta(receta.getId())).withRel("valoraciones"),
                linkTo(methodOn(ControladorCategorias.class).obtenerCategoriasPorReceta(receta.getId())).withRel("categorias"),
                linkTo(methodOn(ControladorRecetasIngredientes.class).obtenerRecetasPorIngrediente(receta.id)).withRel("ingredientes")
        );
    }
}


