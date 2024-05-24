package com.example.cocina.API.valoraciones;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.cocina.API.receta.ControladorRecetas;
import com.example.cocina.user.ControladorUsuarios;

@Component
public class CreadorLinksValoracion implements RepresentationModelAssembler<Valoracion, EntityModel<Valoracion>> {

    @Override
    public EntityModel<Valoracion> toModel(Valoracion valoracion) {
        return EntityModel.of(valoracion,
                linkTo(methodOn(ControladorValoraciones.class).obtenerValoracionPorId(valoracion.getId())).withSelfRel(),
                linkTo(methodOn(ControladorValoraciones.class).obtenerValoraciones()).withRel("valoraciones"),
                linkTo(methodOn(ControladorRecetas.class).obtenerRecetaPorId(valoracion.getReceta().getId())).withRel("receta"),
                linkTo(methodOn(ControladorUsuarios.class).obtenerUsuarioPorId(valoracion.getUsuario().getId())).withRel("usuario")
        );
    }
}

