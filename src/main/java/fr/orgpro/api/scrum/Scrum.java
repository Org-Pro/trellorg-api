package fr.orgpro.api.scrum;

import fr.orgpro.api.project.State;
import fr.orgpro.api.project.Tache;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by sartr on 03/03/2018.
 */
public class Scrum {

    public static List<Tache> listerTacheState(List<Tache> liste, State state){
        List<Tache> taches = new ArrayList<Tache>();
        for(Tache tache : liste){
            if(tache.getState().toString().equals(state.toString())){
                taches.add(tache);
            }
        }
        return taches;
    }

    public static List<Tache> listerTacheScheduled(List<Tache> liste){
        List<Tache> taches = new ArrayList<Tache>();
        for(Tache tache : liste){
            if(tache.getScheduled() != null) {
                if (tache.getScheduled().before(new Date()) && tache.getState() == State.TODO) {
                    taches.add(tache);
                }
            }
        }
        return taches;
    }

    public static Integer compareCout(List<Tache> liste){
        if(liste.size() <= 0){
            return null;
        }
        int coutIteration =  Integer.parseInt(liste.get(0).getHeader(Tache.HEADER_COST));
        int coutTache = 0;
        for(Tache tache : liste){
            if(tache.getLevel() == 1 && tache.getState() == State.ONGOING){
                coutTache += tache.getCout();
            }
        }
        return coutIteration-coutTache;
    }
}
