package tn.esprit.spring.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.spring.entities.Train;
import tn.esprit.spring.entities.Ville;
import tn.esprit.spring.entities.Voyage;
import tn.esprit.spring.entities.Voyageur;
import tn.esprit.spring.entities.etatTrain;
import tn.esprit.spring.repository.TrainRepository;
import tn.esprit.spring.repository.VoyageRepository;
import tn.esprit.spring.repository.VoyageurRepository;

@Service
public class TrainServiceImpl implements ITrainService {


    @Autowired
    VoyageurRepository VoyageurRepository;


    @Autowired
    TrainRepository trainRepository;

    @Autowired
    VoyageRepository voyageRepository;


    public void ajouterTrain(Train t) {

        trainRepository.save(t);
    }

    public int TrainPlacesLibres(Ville nomGareDepart) {
        int cpt = 0;
        int occ = 0;
        int s=0;
        
        List<Voyage> listvoyage = (List<Voyage>) voyageRepository.findAll();
        System.out.println("tailee" + listvoyage.size());

        for (int i = 0; i < listvoyage.size(); i++) {
            System.out.println("gare" + nomGareDepart + "value" + listvoyage.get(0).getGareDepart());
            if (listvoyage.get(i).getGareDepart() == nomGareDepart) {
                cpt = cpt + listvoyage.get(i).getTrain().getNbPlaceLibre();
                occ = occ + 1;
                System.out.println("cpt " + cpt);
            } else {

            }
        }
        if (occ!=0)
        { s = cpt / occ;}
        return s; 
        		}


    public List<Train> ListerTrainsIndirects(Ville nomGareDepart, Ville nomGareArrivee) {

        List<Train> lestrainsRes = new ArrayList<>();
        List<Voyage> lesvoyage = new ArrayList<>();
        lesvoyage = (List<Voyage>) voyageRepository.findAll();
        for (int i = 0; i < lesvoyage.size(); i++) {
            if (lesvoyage.get(i).getGareDepart() == nomGareDepart) {
                for (int j = 0; j < lesvoyage.size(); j++) {
                    if (lesvoyage.get(i).getGareArrivee() == lesvoyage.get(j).getGareDepart() & lesvoyage.get(j).getGareArrivee() == nomGareArrivee) {
                        lestrainsRes.add(lesvoyage.get(i).getTrain());
                        lestrainsRes.add(lesvoyage.get(j).getTrain());

                    } else {

                    }

                }
            }
        }


        return lestrainsRes;
        //
    }


    @Transactional
    public void affecterTainAVoyageur(Long idVoyageur, Ville nomGareDepart, Ville nomGareArrivee, double heureDepart) {
    	
        System.out.println("taille test");
        Voyageur c = VoyageurRepository.findById(idVoyageur).orElse(null);
        List<Voyage> lesvoyages = new ArrayList<>();
        lesvoyages = voyageRepository.RechercheVoyage(nomGareDepart, nomGareDepart, heureDepart);
        System.out.println("taille" + lesvoyages.size());
        for (int i = 0; i < lesvoyages.size(); i++) {
            if (lesvoyages.get(i).getTrain().getNbPlaceLibre() != 0) {
                lesvoyages.get(i).getMesVoyageurs().add(c);
                lesvoyages.get(i).getTrain().setNbPlaceLibre(lesvoyages.get(i).getTrain().getNbPlaceLibre() - 1);
            } else
                System.out.print("Pas de place disponible pour " + c.getNomVoyageur());
            voyageRepository.save(lesvoyages.get(i));
        }
    }

    @Override
    public void DesaffecterVoyageursTrain(Ville nomGareDepart, Ville nomGareArrivee, double heureDepart) {
        List<Voyage> lesvoyages = new ArrayList<>();
        lesvoyages = voyageRepository.RechercheVoyage(nomGareDepart, nomGareArrivee, heureDepart);
        System.out.println("taille" + lesvoyages.size());

        for (int i = 0; i < lesvoyages.size(); i++) {
            for (int j = 0; j < lesvoyages.get(i).getMesVoyageurs().size(); j++)
                lesvoyages.get(i).getMesVoyageurs().remove(j);
            lesvoyages.get(i).getTrain().setNbPlaceLibre(lesvoyages.get(i).getTrain().getNbPlaceLibre() + 1);
            lesvoyages.get(i).getTrain().setEtat(etatTrain.prevu);
            voyageRepository.save(lesvoyages.get(i));
            trainRepository.save(lesvoyages.get(i).getTrain());
        }
    }

    @Scheduled(fixedRate = 2000)
    public void TrainsEnGare() {
        List<Voyage> lesvoyages = new ArrayList<>();
        lesvoyages = (List<Voyage>) voyageRepository.findAll();
        System.out.println("taille" + lesvoyages.size());

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("In Schedular After Try");
        for (int i = 0; i < lesvoyages.size(); i++) {
            if (lesvoyages.get(i).getDateArrivee().before(date)) {
                System.out.println("les trains sont " + lesvoyages.get(i).getTrain().getCodeTrain());
            }
            else{

            }
        }
    }


}

    
