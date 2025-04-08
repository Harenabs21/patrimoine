package school.hei.patrimoine.cas.example;

import static java.time.Month.*;

import com.google.common.base.Supplier;
import java.time.LocalDate;
import java.util.Set;
import school.hei.patrimoine.modele.Argent;
import school.hei.patrimoine.modele.Devise;
import school.hei.patrimoine.modele.Patrimoine;
import school.hei.patrimoine.modele.Personne;
import school.hei.patrimoine.modele.possession.*;

public class BakoPatrimoineSupplier implements Supplier<Patrimoine> {
  @Override
  public Patrimoine get() {
    Personne bako = new Personne("Bako");
    LocalDate au8Avril2025 = LocalDate.of(2025, APRIL, 8);
    LocalDate au31Decembre2025 = LocalDate.of(2025, DECEMBER, 31);
    var compteBni = new Compte("Compte BNI", au8Avril2025, new Argent(2_000_000, Devise.MGA));
    var compteEpargneBMOI =
        new Compte("Compte Epargne BMOI", au8Avril2025, new Argent(625_000, Devise.MGA));
    var coffreFort =
        new Compte("Coffre fort à la maison", au8Avril2025, new Argent(1_750_000, Devise.MGA));
    var computer =
        new Materiel(
            "ordinateur portable",
            au8Avril2025,
            au8Avril2025,
            new Argent(3_000_000, Devise.MGA),
            -0.12);
    var salaire =
        new FluxArgent(
            "Salaire",
            compteBni,
            au8Avril2025,
            au31Decembre2025,
            2,
            new Argent(2_150_000, Devise.MGA));
    var transfert =
        new TransfertArgent(
            "transfer vers compte epargne",
            compteBni,
            compteEpargneBMOI,
            au8Avril2025,
            au31Decembre2025,
            3,
            new Argent(200_000, Devise.MGA));
    var loyer =
        new FluxArgent(
            "Loyer",
            compteBni,
            au8Avril2025,
            au31Decembre2025,
            26,
            new Argent(-600_000, Devise.MGA));
    var depense =
        new FluxArgent(
            "Depense",
            compteBni,
            au8Avril2025,
            au31Decembre2025,
            1,
            new Argent(-700_000, Devise.MGA));

    return Patrimoine.of(
        "Patrimoine de Bako",
        Devise.MGA,
        au8Avril2025,
        bako,
        Set.of(
            compteBni,
            compteEpargneBMOI,
            computer,
            coffreFort,
            salaire,
            transfert,
            loyer,
            depense));
  }
}
