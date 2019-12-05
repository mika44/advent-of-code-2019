package fr.game.advent.day05;

import java.util.ArrayList;

public class day5 {

	   public static void main(String[] args) {
	       // part1();
	        part2();
	    }

	    public static void part1() {
	        System.out.println(runProgramme(1));
	    }

	    public static void part2() {
	        System.out.println(runProgramme(5));
	    }

	    public static String runProgramme(int id) {
	        String[] liste = ImportUtils.getString("./src/main/resources/fr/game/advent/day05/input-day05-1").split(",");
	        StringBuffer output = new StringBuffer();
	        ArrayList<Integer> listeCode = new ArrayList<>();
	        for (int i = 0; i < liste.length; i++) {
	            listeCode.add(Integer.valueOf(liste[i]));
	        }

	        int pointeur = 0;
	        mainLoop: while (true) {
				//System.out.println(String.format(" IP = %d - %s - Memory = %s", pointeur, listeCode.get(pointeur), listeCode));
	            int parameter1;
	            int parameter2;
	            int parameter3;
	            switch (listeCode.get(pointeur) % 100) {
	            case 1:
	                parameter1 = (listeCode.get(pointeur) % 1000) / 100 == 1 ? listeCode.get(pointeur + 1)
	                        : listeCode.get(listeCode.get(pointeur + 1));
	                parameter2 = (listeCode.get(pointeur) % 10000) / 1000 == 1 ? listeCode.get(pointeur + 2)
	                        : listeCode.get(listeCode.get(pointeur + 2));
	                parameter3 = listeCode.get(pointeur + 3);
	        		System.out.print("  ADD -");
	        		System.out.print(String.format(" op1=%s -> %d -", ((listeCode.get(pointeur) % 1000) / 100 == 0) ? "[" + listeCode.get(pointeur + 1) + "]" : listeCode.get(pointeur + 1), parameter1));
	        		System.out.print(String.format(" op2=%s -> %d -", ((listeCode.get(pointeur) % 10000) / 1000 == 0) ? "[" + listeCode.get(pointeur + 2) + "]" : listeCode.get(pointeur + 2), parameter2));
	        		System.out.println(String.format(" op3=[%d] -> %d", parameter3, parameter1 + parameter2));
	        		
	                listeCode.set(parameter3, parameter1 + parameter2);
	                pointeur += 4;
	                break;
	            case 2:
	                parameter1 = (listeCode.get(pointeur) % 1000) / 100 == 1 ? listeCode.get(pointeur + 1)
	                        : listeCode.get(listeCode.get(pointeur + 1));
	                parameter2 = (listeCode.get(pointeur) % 10000) / 1000 == 1 ? listeCode.get(pointeur + 2)
	                        : listeCode.get(listeCode.get(pointeur + 2));
	                parameter3 = listeCode.get(pointeur + 3);
	        		System.out.print("  MUL -");
	        		System.out.print(String.format(" op1=%s -> %d -", ((listeCode.get(pointeur) % 1000) / 100 == 0) ? "[" + listeCode.get(pointeur + 1) + "]" : listeCode.get(pointeur + 1), parameter1));
	        		System.out.print(String.format(" op2=%s -> %d -", ((listeCode.get(pointeur) % 10000) / 1000 == 0) ? "[" + listeCode.get(pointeur + 2) + "]" : listeCode.get(pointeur + 2), parameter2));
	        		System.out.println(String.format(" op3=[%d] -> %d", parameter3, parameter1 * parameter2));

	        		listeCode.set(parameter3, parameter1 * parameter2);
	                pointeur += 4;
	                break;
	            case 3:
	        		System.out.print("  READ -");
	        		System.out.println(String.format(" op1=[%d] -> %d", listeCode.get(pointeur + 1), id));

	        		listeCode.set(listeCode.get(pointeur + 1), id);
	                pointeur += 2;
	                break;
	            case 4:
	        		System.out.print("  WRITE -");
	        		System.out.println(String.format(" op1=%s -> %d -", "[" + listeCode.get(pointeur + 1) + "]", listeCode.get(listeCode.get(pointeur + 1))));

	                output.append(listeCode.get(listeCode.get(pointeur + 1)) + "\n");
	                pointeur += 2;
	                break;
	            case 5:
	                parameter1 = (listeCode.get(pointeur) % 1000) / 100 == 1 ? listeCode.get(pointeur + 1)
	                        : listeCode.get(listeCode.get(pointeur + 1));
	                parameter2 = (listeCode.get(pointeur) % 10000) / 1000 == 1 ? listeCode.get(pointeur + 2)
	                        : listeCode.get(listeCode.get(pointeur + 2));
	        		
	        		System.out.print("  JMPIT -");
	        		System.out.print(String.format(" op1=%s -> %d -", ((listeCode.get(pointeur) % 1000) / 100 == 0) ? "[" + listeCode.get(pointeur + 1) + "]" : listeCode.get(pointeur + 1), parameter1));
	        		System.out.print(String.format(" op2=%s -> %d -", ((listeCode.get(pointeur) % 10000) / 1000 == 0) ? "[" + listeCode.get(pointeur + 2) + "]" : listeCode.get(pointeur + 2), parameter2));
	        		System.out.println(" IP -> " + (parameter1 != 0 ? parameter2 : "IP + 3"));
	        		
	                if (parameter1 != 0) {
	                    pointeur = parameter2;
	                } else {
	                    pointeur += 3;
	                }
	                break;
	            case 6:
	                parameter1 = (listeCode.get(pointeur) % 1000) / 100 == 1 ? listeCode.get(pointeur + 1)
	                        : listeCode.get(listeCode.get(pointeur + 1));
	                parameter2 = (listeCode.get(pointeur) % 10000) / 1000 == 1 ? listeCode.get(pointeur + 2)
	                        : listeCode.get(listeCode.get(pointeur + 2));
	                parameter3 = listeCode.get(pointeur + 3);
	        		
	        		System.out.print("  JMPIF -");
	        		System.out.print(String.format(" op1=%s -> %d -", ((listeCode.get(pointeur) % 1000) / 100 == 0) ? "[" + listeCode.get(pointeur + 1) + "]" : listeCode.get(pointeur + 1), parameter1));
	        		System.out.print(String.format(" op2=%s -> %d -", ((listeCode.get(pointeur) % 10000) / 1000 == 0) ? "[" + listeCode.get(pointeur + 2) + "]" : listeCode.get(pointeur + 2), parameter2));
	        		System.out.println(" IP -> " + (parameter1 == 0 ? parameter2 : "IP + 3"));
	        		
	                if (parameter1 == 0) {
	                    pointeur = parameter2;
	                } else {
	                    pointeur += 3;
	                }
	                break;
	            case 7:
	                parameter1 = (listeCode.get(pointeur) % 1000) / 100 == 1 ? listeCode.get(pointeur + 1)
	                        : listeCode.get(listeCode.get(pointeur + 1));
	                parameter2 = (listeCode.get(pointeur) % 10000) / 1000 == 1 ? listeCode.get(pointeur + 2)
	                        : listeCode.get(listeCode.get(pointeur + 2));
	                parameter3 = listeCode.get(pointeur + 3);
	        		
	        		System.out.print("  LESS -");
	        		System.out.print(String.format(" op1=%s -> %d -", ((listeCode.get(pointeur) % 1000) / 100 == 0) ? "[" + listeCode.get(pointeur + 1) + "]" : listeCode.get(pointeur + 1), parameter1));
	        		System.out.print(String.format(" op2=%s -> %d -", ((listeCode.get(pointeur) % 10000) / 1000 == 0) ? "[" + listeCode.get(pointeur + 2) + "]" : listeCode.get(pointeur + 2), parameter2));
	        		System.out.println(String.format(" op3=[%d] -> %d", parameter3, parameter1 < parameter2 ? 1 : 0));

	        		listeCode.set(parameter3, parameter1 < parameter2 ? 1 : 0);
	                pointeur += 4;
	                break;
	            case 8:
	                parameter1 = (listeCode.get(pointeur) % 1000) / 100 == 1 ? listeCode.get(pointeur + 1)
	                        : listeCode.get(listeCode.get(pointeur + 1));
	                parameter2 = (listeCode.get(pointeur) % 10000) / 1000 == 1 ? listeCode.get(pointeur + 2)
	                        : listeCode.get(listeCode.get(pointeur + 2));
	                parameter3 = listeCode.get(pointeur + 3);
	        		
	        		System.out.print("  EQUAL -");
	        		System.out.print(String.format(" op1=%s -> %d -", ((listeCode.get(pointeur) % 1000) / 100 == 0) ? "[" + listeCode.get(pointeur + 1) + "]" : listeCode.get(pointeur + 1), parameter1));
	        		System.out.print(String.format(" op2=%s -> %d -", ((listeCode.get(pointeur) % 10000) / 1000 == 0) ? "[" + listeCode.get(pointeur + 2) + "]" : listeCode.get(pointeur + 2), parameter2));
	        		System.out.println(String.format(" op3=[%d] -> %d", parameter3, parameter1 == parameter2 ? 1 : 0));

	                listeCode.set(parameter3, parameter1 == parameter2 ? 1 : 0);
	                pointeur += 4;
	                break;
	            case 99:
	                break mainLoop;
	            default:
	                System.err.println("Pas normal");
	                break;
	            }
	        }
	        return output.toString();
	    }

}
