/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package apsn.x9academicodesncorporativo;

import apsn.x9academicodesncorporativo.entities.Administrador;
import apsn.x9academicodesncorporativo.entities.Endereco;
import apsn.x9academicodesncorporativo.entities.Usuario;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import static org.junit.Assert.*;

public class Test {

    public Test() {
    }

    private static EntityManagerFactory emf;
    private static Logger logger;
    private EntityManager em;
    private EntityTransaction et;

    @BeforeClass
    public static void setUpClass() {
        logger = Logger.getGlobal();
        logger.setLevel(Level.INFO);
        emf = Persistence.createEntityManagerFactory("x9_pu2");

    }

    @AfterClass
    public static void tearDownClass() {
        emf.close();
    }

    @Before
    public void setUp() {
        em = emf.createEntityManager();
        et = em.getTransaction();
        et.begin();
    }

    @After
    public void tearDown() {
        try {
            et.commit();
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage());

            if (et.isActive()) {
                et.rollback();
            }
        } finally {
            em.close();
            em = null;
            et = null;
        }
    }

    public static Endereco insereEndereco() {
        Endereco endereco = new Endereco();

        endereco.setCep("68.765-874");
        endereco.setCidade("olinda");
        endereco.setNumeroEndereco(123);
        endereco.setRua("abc");
        endereco.setUf("pe");

        return endereco;
    }

    @org.junit.Test
    public void t01_criaAdmin() {
        logger.info("Executando t01: CriarAdmin");
        Administrador admin = new Administrador();

        admin.setCpf("075.335.464-07");
        admin.setNome("Gilbertojpql");
        admin.setEmail("aaa@bbb.com");
        admin.setEndereco(insereEndereco());
        admin.setSenha("1234.Qu");
        admin.setTelefone("1199898932");
        Calendar calendar = new GregorianCalendar();
        calendar.set(1980, Calendar.OCTOBER, 10);
        admin.setDataNascimento(calendar.getTime());
        admin.setCargo("Coordenador");
        admin.setLogin("admin");

        em.persist(admin);
        em.flush();
        assertNotNull(admin.getIdUsuario());

    }

    @org.junit.Test
    public void t02_buscaAdmin() {

        String jpql = "SELECT u FROM Usuario u where u.login = ?1";
        Query query = em.createQuery(jpql);
        query.setParameter(1, "admin");

        Usuario usuario = (Usuario) query.getSingleResult();

        assertEquals("admin", usuario.getLogin());

    }

    @org.junit.Test
    public void t03_atualizaAdmin() {
        String jpql = "SELECT u FROM Usuario u where u.login = ?1";
        Query query = em.createQuery(jpql);
        query.setParameter(1, "admin");

        Usuario usuario = (Usuario) query.getSingleResult();
        usuario.setEmail("apsn@a.recife");
        usuario.setSenha("yT.765432");
        em.merge(usuario);
        //Perguntar ao professor
        em.flush();
        //em.clear();
        usuario = (Usuario) query.getSingleResult();
        assertEquals("yT.765432", usuario.getSenha());
        assertEquals("apsn@a.recife", usuario.getEmail());
    }

    @org.junit.Test
    public void t04_removeAdmin() {

        String jpql = "SELECT u FROM Usuario u where u.login = ?1";
        Query query = em.createQuery(jpql);
        query.setParameter(1, "admin");

        Usuario usuario = (Usuario) query.getSingleResult();

        em.remove(usuario);

        em.flush();

        em.clear();

        assertEquals(0, query.getResultList().size());

    }

    
    
}
