package fr.game.advent.day14;

import java.util.ArrayList;
import java.util.List;

public class Formule {

	private List<ComposantFormule> composants;
	private ComposantFormule produit;
	
	private Formule() {
	}

	public Formule(Formule formule) {
		this.composants = new ArrayList<>();
		for (ComposantFormule composantFormule : formule.composants) {
			this.composants.add(new ComposantFormule(composantFormule.getElement(), composantFormule.getQuantite()));
		}
		this.produit = new ComposantFormule(formule.produit.getElement(), formule.produit.getQuantite());
	}
	
	public List<ComposantFormule> getComposants() {
		return composants;
	}

	public void setComposants(List<ComposantFormule> composants) {
		this.composants = composants;
	}

	public ComposantFormule getProduit() {
		return produit;
	}

	public void setProduit(ComposantFormule produit) {
		this.produit = produit;
	}

	@Override
	public String toString() {
		return String.format("%s => %s", composants, produit);
	}

	public static Formule toFormule(String stringFormule) {
		String[] decoupageComposantsProduit = stringFormule.split("=>");
		Formule formule = new Formule();
		formule.setProduit(ComposantFormule.toComposantFormule(decoupageComposantsProduit[1]));
		List<ComposantFormule> composants = new ArrayList<>();
		for (String stringComposantFormule : decoupageComposantsProduit[0].split(",")) {
			composants.add(ComposantFormule.toComposantFormule(stringComposantFormule));
		}
		formule.setComposants(composants);
		return formule;
	}
}
