package app.usuario;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import app.receta.ControladorRecetas;

@Component
public class CreadorLinksUsuario implements RepresentationModelAssembler<Usuario, EntityModel<Usuario>> {

    @Override
    public EntityModel<Usuario> toModel(Usuario usuario) {
        return EntityModel.of(usuario,
                linkTo(methodOn(ControladorUsuarios.class).obtenerUsuarioPorId(usuario.getId())).withSelfRel(),
                linkTo(methodOn(ControladorUsuarios.class).obtenerUsuarios()).withRel("usuarios"),
                linkTo(methodOn(ControladorRecetas.class).obtenerRecetasPorUsuario(usuario.getId())).withRel("recetas")
        );
    }
}
