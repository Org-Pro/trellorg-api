package fr.trellorg.api.project;

import fr.trellorg.api.orgzly.OrgHead;
import fr.trellorg.api.orgzly.OrgProperties;
import fr.trellorg.api.orgzly.parser.OrgParserWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by sartr on 01/02/2018.
 */
public class Tache {
    private OrgHead tache;

    private OrgParserWriter ecriture;
    private SimpleDateFormat dateFormat;

    public Tache(String title,int level) {
        ecriture = new OrgParserWriter();
        this.tache = new OrgHead(title);
        this.tache.setLevel(level);
        this.tache.setState(State.TODO.toString());
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }

    public boolean changeState(State state){
        if(tache.getState() == state.toString()){
            return false;
        }
        if(tache.getState() == State.DONE.toString() && state.toString() == State.TODO.toString()){
            return false;
        }
        String log = "- State \"" + state.toString() + "\" FROM \"" + tache.getState().toString() + "\" ";
        this.ajoutLogBook(log);
        this.tache.setState(state.toString());

        return true;
    }

    public void ajoutTag(String tag){
        List<String> tags = tache.getTags();
        tags.add(tag);
        int i = 0;
        String[] tagsTemp = new String[tags.size()];
        for (String tagTemp: tags) {
            tagsTemp[i]=tagTemp;
            i++;
        }
        tache.setTags(tagsTemp);
    }

    public void supprimerTag(String tag){
        List<String> tags = tache.getTags();
        int i = 0;
        for (String tagTemp: tags) {
            if(tagTemp == tag){
                tags.remove(i);
                break;
            }
            i++;
        }
        i = 0;
        String[] tagsTemp = new String[tags.size()];
        for (String tagTemp: tags) {
            tagsTemp[i]=tagTemp;
            i++;
        }
        tache.setTags(tagsTemp);
    }

    public boolean ajoutDeadline(String deadline){
        try {
            Date deadlineDate = dateFormat.parse(deadline);
            tache.setDeadline(deadlineDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean ajoutScheduled(String scheduled){
        try {
            Date scheduledDate = dateFormat.parse(scheduled);
            tache.setScheduled(scheduledDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean ajoutClosed(String closed){
        try {
            Date closedDate = dateFormat.parse(closed);
            tache.setClosed(closedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void ajoutProperty(String name, String value){
        tache.addProperty(name,value);
    }

    public void supprimerProperty(String name){
        OrgProperties properties = tache.getProperties();
        properties.remove(name);
        tache.setProperties(properties);
    }

    private void ajoutLogBook(String log){
        Date date = new Date();
        log += new SimpleDateFormat("<yyyy-MM-dd HH:mm>").format(date);
        tache.addLog(log);
    }


    public void ecritureFichier(String path, boolean append){
        OrgParserWriter ecriture = new OrgParserWriter();
        String ecrire = ecriture.whiteSpacedHead(tache,tache.getLevel(),true);
        try {
            FileWriter ffw=new FileWriter(path,append);
            ffw.write(ecrire);
            ffw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return ecriture.whiteSpacedHead(tache,tache.getLevel(),true);
    }
}
