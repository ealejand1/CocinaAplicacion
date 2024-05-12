package app.receta;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import app.usuario.ControladorUsuarios;

@Component
public class CreadorLinksReceta implements RepresentationModelAssembler<Receta, EntityModel<Receta>> {

    @Override
    public EntityModel<Receta> toModel(Receta receta) {
        return EntityModel.of(receta,
                linkTo(methodOn(ControladorRecetas.class).obtenerRecetaPorId(receta.getId())).withSelfRel(),
                linkTo(methodOn(ControladorRecetas.class).obtenerRecetas()).withRel("recetas"),
                linkTo(methodOn(ControladorUsuarios.class).obtenerUsuarioPorId(receta.getUsuario().getId())).withRel("usuario")
        );
    }
}


