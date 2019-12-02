package fr.game.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.core.io.ClassPathResource;

/**
 * Classe utilitaire qui fournit 2 méthodes pour contruire une liste d'inputs à partir d'un fichier.
 * 
 * La première lit les lignes du fichier sous forme de String.
 * Puis applique sur chaque ligne une transformation avec un mapper pour obtenir les inputs.
 * 
 * La deuxième lit les lignes du fichier sous forme de String.
 * Puis les string sont triés par ordre croissant.
 * Enfin on applique sur chaque ligne une transformation avec un mapper pour obtenir les inputs.
 *
 * La première sert pour les 3 premiers jours.
 * La deuxième a été ajoutée pour traiter le game du jour 4.
 *
 */
public class FileUtils {

	public static <T> List<T> getListFromOneLineFileCommaSeparated(String filename, Function<String, T> mapper) {
		try {
			return Files.lines(Paths.get(new ClassPathResource(filename).getURI()))
				.limit(1)
				.map(s -> s.split(","))
				.flatMap(Arrays::stream)
				.map(mapper)
				.collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}	

	public static <T> List<T> getListFromFile(String filename, Function<String, T> mapper) {
		try {
			return Files.lines(Paths.get(new ClassPathResource(filename).getURI()))
				.map(mapper)
				.collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}	

	public static <T> List<T> getOrderedListFromFile(String filename, Function<String, T> mapper) {
		try {
			return Files.lines(Paths.get(new ClassPathResource(filename).getURI()))
			    .sorted()
				.map(mapper)
				.collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}	
}

