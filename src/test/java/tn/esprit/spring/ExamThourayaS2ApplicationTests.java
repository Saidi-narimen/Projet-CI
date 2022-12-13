//package tn.esprit.spring;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//
//@SpringBootTest
//class ExamThourayaS2ApplicationTests {
//
//	@Test
//	void contextLoads() {
//	}
//
//}
package tn.esprit.spring;


import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.entities.Voyageur;
import tn.esprit.spring.repository.VoyageurRepository;
import tn.esprit.spring.services.VoyageurServiceImpl;
@RunWith(SpringRunner.class)
@Slf4j
@ExtendWith(MockitoExtension.class)

@MockitoSettings(strictness = Strictness.LENIENT)

@SpringBootTest
public class ExamThourayaS2ApplicationTests {

	@Mock
    VoyageurRepository sto;
    @InjectMocks
    VoyageurServiceImpl VoyageurService;
    
    Voyageur s = new Voyageur((long) 5,"Narimen");
    
    
    @Test
    public void testAjouterVoyageur() {
    	 //Voyageur s = new Voyageur();
        Mockito.when(sto.save(ArgumentMatchers.any(Voyageur.class))).thenReturn(s);
        VoyageurService.ajouterVoyageur(s);
        assertNotNull(s.getIdVoyageur());
        assertNotNull(s.getNomVoyageur());

        //VoyageurService.supprimerVoyageur(savedVoyageur.getIdVoyageur());

    }

    @Test
    public void testSupprimerVoyageur() {
        //Voyageur s = new Voyageur();
        s.setIdVoyageur(Long.valueOf(100));
        VoyageurService.ajouterVoyageur(s);
        VoyageurService.supprimerVoyageur(s);
        assertNull(VoyageurService.recupererVoyageParId(s.getIdVoyageur()));
    }
    @Test
    public void testRetrieveVoyageur() {
        Mockito.when(sto.findById(Mockito.anyLong())).thenReturn(Optional.of(s));
        Voyageur s1 = VoyageurService.recupererVoyageParId(s.getIdVoyageur());
    }


 

}