package com.example.cocina.user;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.cocina.API.receta.ControladorRecetas;
import com.example.cocina.API.valoraciones.ControladorValoraciones;

@Component
public class CreadorLinksUsuario implements RepresentationModelAssembler<User, EntityModel<User>> {

    @Override
    public EntityModel<User> toModel(User usuario) {
        return EntityModel.of(usuario,
                linkTo(methodOn(ControladorUsuarios.class).obtenerUsuarioPorId(usuario.getId())).withSelfRel(),
                linkTo(methodOn(ControladorUsuarios.class).obtenerUsuarios()).withRel("usuarios"),
                linkTo(methodOn(ControladorRecetas.class).obtenerRecetasPorUsuario(usuario.getId())).withRel("recetas"),
                linkTo(methodOn(ControladorValoraciones.class).obtenerValoracionesPorUsuario(usuario.getId())).withRel("valoraciones")
        );
    }
}
