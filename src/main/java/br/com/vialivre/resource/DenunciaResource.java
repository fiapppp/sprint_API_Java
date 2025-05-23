package br.com.vialivre.resource;

import br.com.vialivre.dto.DenunciaDTO;
import br.com.vialivre.dto.AtualizaDenunciaDTO;
import br.com.vialivre.dto.DenunciaResponseDTO;
import br.com.vialivre.model.Denuncia;
import br.com.vialivre.security.CurrentUser;
import br.com.vialivre.service.DenunciaService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/api/denuncia")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DenunciaResource {

    @Inject
    DenunciaService service;

    @Inject
    CurrentUser currentUser;

    /**
     * Abre uma nova denúncia para o usuário autenticado
     */
    @POST
    @RolesAllowed("cidadao")
    @Path("/criar")
    public Response criar(DenunciaDTO dto) {
        // obtém usuário logado
        var usuario = currentUser.get();
        if (usuario == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        Denuncia criada = service.criarComEvidencias(dto, usuario);
        return Response.status(Response.Status.CREATED)
                .entity(criada)
                .build();
    }

    /**
     * Consulta denúncias detalhadas do usuário autenticado
     */
    @GET
    @Path("/me/detalhes")
    @RolesAllowed({"cidadao","funcionario","admin"})
    public List<DenunciaResponseDTO> listarPorUsuarioDetalhado() {
        var usuario = currentUser.get();
        return service.listarPorUsuarioComDetalhes(usuario.id);
    }

    /**
     * Atualiza status de uma denúncia (gestor)
     */
    @PATCH
    @Path("/atualiza/{id}")
    @RolesAllowed("funcionario")
    public Response atualizarStatus(
            @PathParam("id") Long id,
            AtualizaDenunciaDTO dto
    ) {
        Denuncia atualizada = service.atualizarStatus(
                id,
                dto.status,
                dto.observacaoResponsavel,
                dto.idUsuarioResponsavel
        );
        return Response.ok(atualizada).build();
    }
}
