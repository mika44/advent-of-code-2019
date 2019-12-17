package fr.game.advent.day14;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import fr.game.utils.AbstractGame;
import fr.game.utils.FileUtils;
import fr.game.utils.LoggerUtils;

public class GameOne extends AbstractGame<Formule, Long> {
	
	private static final String INPUT_FILENAME = "day14/input-day14-1";

	private Logger log;
	private List<Formule> formules;
	
	
	public GameOne() {
		super(FileUtils::getListFromFile, INPUT_FILENAME, Formule::toFormule);
		log = LoggerUtils.getLogger();
	}

	
	
	@Override
	public Long play(List<Formule> formules) {
		this.formules = formules;
		Formule formuleBases = determinerFormuleComposantsDeBasePourProduireElement(new ComposantFormule("FUEL", 1));
		log.info("Formule de base obtenue : " + formuleBases);
		return formuleBases.getComposants().get(0).getQuantite();
	}
	
	private Optional<Formule> trouverFormulesProduisantElement(ComposantFormule composant) {
		return formules.parallelStream().filter(f -> f.getProduit().equals(composant)).findFirst();
	}
	
	private int getLevel(ComposantFormule composant) {
		Optional<Formule> formuleProduction = trouverFormulesProduisantElement(composant);
		if (formuleProduction.isPresent()) {
			return formuleProduction.get().getComposants().stream().mapToInt(this::getLevel).max().getAsInt() + 1;
		} else {
			return 0;
		}
	}
	
	private Formule determinerFormuleComposantsDeBasePourProduireElement(ComposantFormule produit) {
		Formule formuleCourante = new Formule(trouverFormulesProduisantElement(produit).get());
		log.info("produit = " + produit + " - formule = " + formuleCourante);
		boolean isMaxLevelZero = false;
		while (!isMaxLevelZero) {
			final int maxLevel = formuleCourante.getComposants().stream().mapToInt(this::getLevel).max().getAsInt(); 
			isMaxLevelZero = maxLevel == 0;
			if (!isMaxLevelZero) {
				ComposantFormule composant = formuleCourante.getComposants().stream().filter(c -> getLevel(c) == maxLevel).findAny().get();
				log.info("produit = " + produit + " - formule = " + formuleCourante + " - composant " + composant + " de level " + getLevel(composant));
				Formule formuleComposant = new Formule(trouverFormulesProduisantElement(composant).get());
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
