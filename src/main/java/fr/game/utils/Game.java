package fr.game.utils;

import java.util.List;

/**
 * Une partie de adventOfCode consiste généralement à lire un fichier d'inputs et à le traiter.
 * On décrit donc une partie (Game) par :
 * - soit une méthode play sans paramètre qui lance le traitement sur un fichier d'inputs par défaut;
 * - soit une seconde méthode play qui traite directement une liste d'items. 
 *  
 *
 * @param <I> type des inputs
 * @param <R> type résultat
 */
public interface Game<I, R> {

	R play(List<I> listOfInputs);

	R play();

}