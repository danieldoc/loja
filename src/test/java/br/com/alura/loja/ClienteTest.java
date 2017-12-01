import br.com.alura.loja.Servidor;
import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.modelo.Produto;
import br.com.alura.loja.modelo.Projeto;
import com.thoughtworks.xstream.XStream;
import junit.framework.TestCase;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ClienteTest extends TestCase {

    private HttpServer server;

    private Client client = ClientBuilder.newClient();
    private WebTarget target = client.target("http://localhost:8080");

    @Before
    public void startaServidor() {
        this.server = Servidor.inicializaServidor();
    }

    @After
    public void mataServidor() {
        server.stop();
    }

    @Test
    public void testaQueBuscarUmCarrinhoTrazOCarrinhoEsperado() {
        String conteudo = target.path("/carrinhos/1").request().get(String.class);
        Carrinho carrinho = (Carrinho) new XStream().fromXML(conteudo);
        Assert.assertEquals("Rua Vergueiro 3185, 8 andar", carrinho.getRua());
    }

    @Test
    public void testaQueAConexaoComOProjetosFunciona() {
        String conteudo = target.path("/projetos").request().get(String.class);
        Assert.assertTrue(conteudo.contains("<nome>Minha Loja"));
    }

    @Test
    public void testaQueBuscarUmProjetosTrazOProjetoEsperado() {
        String conteudo = target.path("/projetos").request().get(String.class);
        Projeto projeto = (Projeto) new XStream().fromXML(conteudo);
        Assert.assertEquals("Minha Loja", projeto.getNome());
    }

    @Test
    public void testaQueSuportaNovosCarrinhos(){
        Carrinho carrinho = new Carrinho();
        carrinho.adiciona(new Produto(314L, "Tablet", 999, 1));
        carrinho.setRua("Rua Vergueiro");
        carrinho.setCidade("Sao Paulo");
        String xml = carrinho.toXML();

        Entity<String> entity = Entity.entity(xml, MediaType.APPLICATION_XML);

        Response response = target.path("/carrinhos").request().post(entity);
        Assert.assertEquals(201, response.getStatus());

        String location = response.getHeaderString("location");
        String conteudo = client.target(location).request().get(String.class);
        Assert.assertTrue(conteudo.contains("Tablet"));
    }
}