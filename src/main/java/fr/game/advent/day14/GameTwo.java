package fr.game.advent.day14;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import fr.game.utils.AbstractGame;
import fr.game.utils.FileUtils;
import fr.game.utils.LoggerUtils;

public class GameTwo extends AbstractGame<Formule, Long> {
	
	private static final long MAX_ORE = 1_000_000_000_000L;

	private static final String INPUT_FILENAME = "day14/input-day14-1";

	private Logger log;
	private List<Formule> formules;
	
	
	public GameTwo() {
		super(FileUtils::getListFromFile, INPUT_FILENAME, Formule::toFormule);
		log = LoggerUtils.getLogger();
	}

	
	
	@Override
	public Long play(List<Formule> formules) {
		this.formules = formules;
		Formule formuleOrePourUnFuel = determinerFormuleOre(new ComposantFormule("FUEL", 1));
		log.info("Formule obtenue pour 1 Fuel : " + formuleOrePourUnFuel);
		long orePourUnFuel = formuleOrePourUnFuel.getComposants().get(0).getQuantite();
		
		long minQuantiteFuel = MAX_ORE / orePourUnFuel - 1;
		long maxQuantiteFuel = 2 * minQuantiteFuel;
		long oreNecessaire = MAX_ORE + 1;
		do {
			long quantiteFuel = (minQuantiteFuel + maxQuantiteFuel) / 2;
			oreNecessaire = determinerFormuleOre(new ComposantFormule("FUEL", quantiteFuel)).getComposants().get(0).getQuantite();
			log.warning(String.format("Essai production %,d FUEL -> besoin de %,d ORE - min = %,d - max = %,d", quantiteFuel, oreNecessaire, minQuantiteFuel, maxQuantiteFuel));
			if (oreNecessaire < MAX_ORE) {
				minQuantiteFuel = quantiteFuel;
			} else {
				maxQuantiteFuel = quantiteFuel;
			}
		} while (maxQuantiteFuel - minQuantiteFuel > 1);
		return minQuantiteFuel;
	}
	
	private Optional<Formule> trouverFormuleProduisantElement(ComposantFormule composant) {
		return formules.parallelStream().filter(f -> f.getProduit().equals(composant)).findFirst();
	}
	
	private int getNiveau(ComposantFormule composant) {
		Optional<Formule> formuleProduction = trouverFormuleProduisantElement(composant);
		if (formuleProduction.isPresent()) {
			return formuleProduction.get().getComposants().stream().mapToInt(this::getNiveau).max().getAsInt() + 1;
		} else {
			return 0;
		}
	}
	
	private Formule determinerFormuleOre(ComposantFormule produit) {
		Formule formuleCourante = new Formule(trouverFormuleProduisantElement(produit).get());
		log.info("produit = " + produit + " - formule = " + formuleCourante);
		ajusterQuantite(formuleCourante, produit.getQuantite());
		log.info("produit = " + produit + " - formule après ajustement = " + formuleCourante);
		boolean isMaxLevelZero = false;
		while (!isMaxLevelZero) {
			final int maxLevel = formuleCourante.getComposants().stream().mapToInt(this::getNiveau).max().getAsInt(); 
			isMaxLevelZero = maxLevel == 0;
			if (!isMaxLevelZero) {
				ComposantFormule composant = formuleCourante.getComposants().stream().filter(c -> getNiveau(c) == maxLevel).findAny().get();
				log.info("produit = " + produit + " - formule = " + formuleCourante + " - composant " + composant + " de level " + getNiveau(composant));
				Formule formuleComposant = new Formule(trouverFormuleProduisantElement(composant).get());
				log.info("produit = " + produit + " - formule = " + formuleCourante + " - composant " + composant + " - formule composant " + formuleComposant);
				ajusterQuantite(formuleComposant, composant.getQuantite());
				log.info("produit = " + produit + " - formule = " + formuleCourante + " - composant " + composant + " - formule composant ajustée " + formuleComposant);
				remplacerComposant(formuleCourante, formuleComposant);
				log.info("produit = " + produit + " - formule après remplacement = " + formuleCourante);
			}
		}
		return formuleCourante;
	}

	private void remplacerComposant(Formule formuleInitiale, Formule formuleComposant) {
		List<ComposantFormule> composantsInitiaux = formuleInitiale.getComposants();
		composantsInitiaux.remove(formuleComposant.getProduit());
		for (ComposantFormule nouveauComposant : formuleComposant.getComposants()) {
			int indexNouveauComposant = composantsInitiaux.indexOf(nouveauComposant);
			if (indexNouveauComposant == -1) {
				composantsInitiaux.add(nouveauComposant);
			} else {
				ComposantFormule composantDejaPresent = composantsInitiaux.get(indexNouveauComposant);
				composantDejaPresent.setQuantite(composantDejaPresent.getQuantite() + nouveauComposant.getQuantite());
			}
		}
	}

	private void ajusterQuantite(Formule formule, long quantiteCible) {
		long quantiteFormule = formule.getProduit().getQuantite();
		long coefficentAjustement = calculerCoefficentAjustement(quantiteCible, quantiteFormule);
		for (ComposantFormule composant : formule.getComposants()) {
			composant.setQuantite(composant.getQuantite() * coefficentAjustement);
		}
		formule.getProduit().setQuantite(formule.getProduit().getQuantite() * coefficentAjustement);
	}

	private long calculerCoefficentAjustement(long quantiteCible, long quantiteFormule) {
		long coefficentAjustement = quantiteCible / quantiteFormule;
		if (quantiteCible % quantiteFormule != 0L) coefficentAjustement++;
		return coefficentAjustement;
	}		
}
