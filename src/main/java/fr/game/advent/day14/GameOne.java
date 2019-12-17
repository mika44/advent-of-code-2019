package fr.game.advent.day14;

import java.util.ArrayList;
import java.util.List;
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
		long oreNecessaire = 0L;
		for (ComposantFormule composantBase : formuleBases.getComposants()) {
			Formule formuleComposant = new Formule(trouverFormulesProduisantElement(composantBase));
			log.info("Formule pour produire " + composantBase.getElement() + " -> " + formuleComposant);
			ajusterQuantite(formuleComposant, composantBase.getQuantite());
			oreNecessaire = oreNecessaire + formuleComposant.getComposants().get(0).getQuantite();
			log.info("ORE pour produire " + composantBase.getQuantite() + " " + composantBase.getElement() + " -> " + formuleComposant.getComposants().get(0).getQuantite());
		}
		return oreNecessaire;
	}
	
	private Formule trouverFormulesProduisantElement(ComposantFormule composant) {
		return formules.parallelStream().filter(f -> f.getProduit().equals(composant)).findFirst().get();
	}
	
	private boolean isComposantDeBase(ComposantFormule composant) {
		List<ComposantFormule> composants = trouverFormulesProduisantElement(composant).getComposants();
		return composants.size() == 1 && composants.get(0).getElement().equals("ORE");
	}
	
	private Formule determinerFormuleComposantsDeBasePourProduireElement(ComposantFormule produit) {
		log.info("determinerFormuleComposantsDeBasePourProduireElement produit = " + produit);
		Formule formuleInitiale = new Formule(trouverFormulesProduisantElement(produit));
		log.info("determinerFormuleComposantsDeBasePourProduireElement produit = " + produit + " - formule production = " + formuleInitiale);
		ajusterQuantite(formuleInitiale, produit.getQuantite());
		log.info("determinerFormuleComposantsDeBasePourProduireElement produit = " + produit + " - formule production ajustée = " + formuleInitiale);
		List<Formule> formulesRemplacement = new ArrayList<>();
		for (ComposantFormule composant : formuleInitiale.getComposants()) {
			log.info("determinerFormuleComposantsDeBasePourProduireElement produit = " + produit + " - formule production ajustée = " + formuleInitiale + " - composant " + composant);
			if (!isComposantDeBase(composant)) {
				Formule formuleComposant = new Formule(determinerFormuleComposantsDeBasePourProduireElement(composant));
				log.info("determinerFormuleComposantsDeBasePourProduireElement produit = " + produit + " - formule production ajustée = " + formuleInitiale + " - composant " + composant + " - formule composant " + formuleComposant);
				ajusterQuantite(formuleComposant, composant.getQuantite());
				log.info("determinerFormuleComposantsDeBasePourProduireElement produit = " + produit + " - formule production ajustée = " + formuleInitiale + " - composant " + composant + " - formule composant ajsutée " + formuleComposant);
				formulesRemplacement.add(formuleComposant);
			}
		}
		for (Formule formuleRemplacement : formulesRemplacement) {
			log.info("determinerFormuleComposantsDeBasePourProduireElement produit = " + produit + " - formule production ajustée avant remplacement = " + formuleInitiale + " - formule remplacement " + formuleRemplacement);
			remplacerComposant(formuleInitiale, formuleRemplacement);
			log.info("determinerFormuleComposantsDeBasePourProduireElement produit = " + produit + " - formule production ajustée après remplacement = " + formuleInitiale + " - formule remplacement " + formuleRemplacement);
		}
		return formuleInitiale;
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
