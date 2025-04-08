package school.hei.patrimoine.cas.example;

import com.google.common.base.Supplier;
import school.hei.patrimoine.modele.Argent;
import school.hei.patrimoine.modele.Devise;
import school.hei.patrimoine.modele.Patrimoine;
import school.hei.patrimoine.modele.Personne;
import school.hei.patrimoine.modele.possession.*;

import java.time.LocalDate;
import java.util.Set;

import static java.time.Month.*;

public class BakoPatrimoineSupplier implements Supplier<Patrimoine> {
    @Override
    public Patrimoine get() {
        Personne bako = new Personne("Bako");
        LocalDate au8Avril2025 = LocalDate.of(2025, APRIL, 8);
        LocalDate au31Decembre2025 = LocalDate.of(2025, DECEMBER, 31);
        var compteBni = new Compte("Compte BNI", au8Avril2025,new Argent(2_000_000, Devise.MGA));
        var compteEpargneBMOI = new Compte("Compte Epargne BMOI", au8Avril2025, new Argent(625_000,Devise.MGA));
        var coffreFort = new Compte("Coffre fort à la maison", au8Avril2025, new Argent(1_750_000,Devise.MGA));
        var computer = new Materiel("ordinateur portable",au8Avril2025,au8Avril2025,new Argent(3_000_000,Devise.MGA),-0.12);
        new FluxArgent("Salaire",compteBni,au8Avril2025,LocalDate.MAX,2, new Argent(2_150_000,Devise.MGA));
        new TransfertArgent("transfer vers compte epargne",compteBni,compteEpargneBMOI,au8Avril2025,au31Decembre2025,3,new Argent(200_000,Devise.MGA));
        var colocationEtDepense = new GroupePossession("Colocation et depense",Devise.MGA,au8Avril2025, Set.of(
                new FluxArgent(
                        "Loyer",
                        compteBni,
                        au8Avril2025,
                        au31Decembre2025,
                        26,
                        new Argent(-600_000,Devise.MGA)),
                new FluxArgent(
                        "Depense",
                        compteBni,
                        au8Avril2025,
                        au31Decembre2025,
                        1,
                        new Argent(-700_000,Devise.MGA)))
        );




        return Patrimoine.of(
                "Patrimoine de Bako",
                Devise.MGA,
                au8Avril2025,
                bako,
                Set.of(compteBni,compteEpargneBMOI,computer,coffreFort,colocationEtDepense)
        );
    }
}
