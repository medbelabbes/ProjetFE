package PFE.Interfaces;


import PFE.Model.Etudiant;

import java.util.List;

/**
 * Created by Mohamed on 26/04/2017.
 */
public interface interEtudiant {
    List<Etudiant> select();
    void saveOrUpdate(Etudiant e);
    void delete(Etudiant e);
}
