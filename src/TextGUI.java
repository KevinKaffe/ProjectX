import java.util.Scanner;

public class TextGUI {

	public TextGUI() {
		this.runProgram();
	}
	
	public void runProgram() {
		System.out.println("Velkommen til GetBigMuscles treningsapp, hva ønsker du å gjøre? Du har fire valg!");
		System.out.println("Tast '1' om du ønsker å registrere apparater, øvelser eller treninsøkt med tilhørende data.\n"
							+ "Tast '2' om du ønsker å få opp informasjon om N siste gjennomførte treningsøkter med notater. \n"
							+ "Tast '3' om du ønsker å se resultatlogg for en gitt øvelse i et gitt tidsintervell \n"
							+ "Tast '4' om du ønsker å lage øvelsesgrupper og finne øvelser som er i samme gruppe. \n");
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
