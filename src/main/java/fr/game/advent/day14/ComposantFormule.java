package fr.game.advent.day14;

import java.util.Objects;

public class ComposantFormule {

	private String element;
	private long quantite;

	public ComposantFormule(String element, long quantite) {
		super();
		this.element = element;
		this.quantite = quantite;
	}

	public String getElement() {
		return element;
	}

	public void setElement(String element) {
		this.element = element;
	}

	public long getQuantite() {
		return quantite;
	}

	public void setQuantite(long quantite) {
		this.quantite = quantite;
	}

	@Override
	public int hashCode() {
		return Objects.hash(element);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ComposantFormule other = (ComposantFormule) obj;
		return Objects.equals(element, other.element);
	}

	@Override
	public String toString() {
		return String.format("%s %s", quantite, element);
	}

	public static ComposantFormule toComposantFormule(String stringComposantFormule) {
		String[] decomposition = stringComposantFormule.trim().split(" ");
		return new ComposantFormule(decomposition[1], Long.valueOf(decomposition[0]));
	}
}
