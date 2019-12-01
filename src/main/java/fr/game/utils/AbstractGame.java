package fr.game.utils;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Implémentation abstraite de Game.
 * Elle se construit avec :
 * - un builder qui construit une liste d'items de type I à partir d'un nom de fichier et d'un mapper 
 * - un nom de fichier d'inputs (on impose par défaut que le fichier est dans un sous répertoire de fr/game/advent)
 * - un mapper qui transforme une ligne du fichier (String) en item de type I
 * 
 * La méthode play sans paramètre appelle le builder avec les paramétres nom de fichier et mapper.
 * La méthode play avec liste d'item est abstraite. C'est la principale méthode à écrire dans chaque partie.
 *
 * @param <I> type des inputs
 * @param <R> type résultat
 */
public abstract class AbstractGame<I, R> implements Game<I, R> {
	
	protected final static String BASE_DIRECTORY = "fr/game/advent/";
	
	protected String filename;
	protected Function<String, I> mapper;
	protected BiFunction<String, Function<String, I>, List<I>> builderListFromFile;
	
	public AbstractGame(BiFunction<String, Function<String, I>, List<I>> constructorListFromFile, String filename, Function<String, I> mapper) {
		this.builderListFromFile = constructorListFromFile;
		this.filename = filename;
		this.mapper = mapper;
	}
	
	public void setFilename(String filename) {
		this.filename = filename;
	}

	@Override
	public abstract R play(List<I> listOfInputs);
	
	@Override
	public R play() {
		return play( builderListFromFile.apply(BASE_DIRECTORY + filename, mapper) );
	}

}

