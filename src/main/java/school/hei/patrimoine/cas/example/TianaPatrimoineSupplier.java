package school.hei.patrimoine.cas.example;

import static school.hei.patrimoine.modele.Argent.ariary;

import com.google.common.base.Supplier;
import java.time.LocalDate;
import java.util.Set;
import school.hei.patrimoine.modele.Devise;
import school.hei.patrimoine.modele.Patrimoine;
import school.hei.patrimoine.modele.Personne;
import school.hei.patrimoine.modele.possession.*;

public class TianaPatrimoineSupplier implements Supplier<Patrimoine> {

  private final LocalDate ajd = LocalDate.of(2025, 4, 8);
  private final LocalDate debutProjet = LocalDate.of(2025, 6, 1);
  private final LocalDate finProjet = LocalDate.of(2025, 12, 31);
  private final LocalDate finSimulation = LocalDate.of(2026, 6, 1);

  private Compte compteBancaire() {
    return new Compte("Compte bancaire de Tiana", ajd, ariary(60_000_000));
  }

  private Materiel terrain() {
    return new Materiel("terrain", ajd, ajd, ariary(100_000_000), -0.1);
  }

  private FluxArgent depenseCourante() {
    return new FluxArgent(
        "depense courante", compteBancaire(), ajd, finSimulation, 1, ariary(-4_000_000));
  }

  private GroupePossession projetDeTiana() {
    var depense =
        new FluxArgent(
            "depense par mois", compteBancaire(), debutProjet, finProjet, 5, ariary(-5_000_000));
    var premierTrancheDeRevenu =
        new FluxArgent(
            "premiere tranche de revenu (10%)",
            compteBancaire(), debutProjet.minusMonths(1), ariary(7_000_000));
    var deuxiemeTrancheDeRevenu =
        new FluxArgent(
            "deuxieme tranche de revenu (90%)",
            compteBancaire(), finProjet.plusMonths(1), ariary(67_000_000));
    return new GroupePossession(
        "projet de Tiana",
        Devise.MGA,
        debutProjet,
        Set.of(depense, premierTrancheDeRevenu, deuxiemeTrancheDeRevenu));
  }

  private GroupePossession detteDeTiana() {
    var dateDette = LocalDate.of(2025, 7, 27);
    var pretBancaire =
        new FluxArgent("Prêt de la banque", compteBancaire(), dateDette, ariary(20_000_000));

    var detteDeTiana = new Dette("Dette issue du prêt", dateDette, ariary(-24_000_000));

    var remboursementDeLaDette =
        new TransfertArgent(
            "Remboursement de la dette",
            compteBancaire(),
            detteDeTiana,
            LocalDate.of(2025, 8, 27),
            LocalDate.of(2026, 7, 27),
            27,
            ariary(2_000_000));
    return new GroupePossession(
        "Dette de Tiana",
        Devise.MGA,
        dateDette,
        Set.of(pretBancaire, detteDeTiana, remboursementDeLaDette));
  }

  @Override
  public Patrimoine get() {
    var tiana = new Personne("Tiana");
    var compteBancaire = compteBancaire();
    var terrain = terrain();
    var depenseCourante = depenseCourante();
    var projetDeTiana = projetDeTiana();
    var detteDeTiana = detteDeTiana();
    return Patrimoine.of(
        "Patrimoine de Tiana",
        Devise.MGA,
        ajd,
        tiana,
        Set.of(compteBancaire, terrain, depenseCourante, projetDeTiana, detteDeTiana));
  }
}
