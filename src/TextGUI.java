import java.util.Scanner;

public class TextGUI {

	public TextGUI() {
		this.runProgram();
	}
	
	public void runProgram() {
		System.out.println("Velkommen til GetBigMuscles treningsapp, hva �nsker du � gj�re? Du har fire valg!");
		System.out.println("Tast '1' om du �nsker � registrere apparater, �velser eller trenins�kt med tilh�rende data.\n"
							+ "Tast '2' om du �nsker � f� opp informasjon om N siste gjennomf�rte trenings�kter med notater. \n"
							+ "Tast '3' om du �nsker � se resultatlogg for en gitt �velse i et gitt tidsintervell \n"
							+ "Tast '4' om du �nsker � lage �velsesgrupper og finne �velser som er i samme gruppe. \n");
		Scanner scanner = new Scanner(System.in);
		int brukerValg = Integer.parseInt(scanner.next());
		if (brukerValg == 1) {
			this.registerShit();
		} else if (brukerValg == 2) {
			this.getInfoBoutSessions();
		} else if (brukerValg == 3) {
			this.getResultLogg();
		} else if (brukerValg == 4) {
			this.registerSessionGroup();
		} else {
			System.out.println("Du har ikke valgt et gyldig tall");
		}
	}
	
	public static void main(String[] args) {
		TextGUI txtgui = new TextGUI();
	}
	
	private void registerShit() {
		System.out.println("Yo");
	}
	
	private void getInfoBoutSessions() {
		System.out.println("Sessssssssion");
	}
	
	private void getResultLogg() {
		System.out.println("Your session? Horrible..");
	}
	
	private void registerSessionGroup() {
		System.out.println("Ya feel lucky..? PUNCK!");
	}
	
}
