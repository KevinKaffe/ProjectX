import java.util.Scanner;

public class TextGUI {

	public TextGUI() {
		this.runProgram();
	}
	
	public void runProgram() {
		System.out.println("Velkommen til GetBigMuscles treningsapp, hva onsker du å gjore? Du har fire valg!");
		System.out.println("Tast '1' om du onsker aa registrere apparater, ovelser eller treninsokt med tilhorende data.\n"
							+ "Tast '2' om du onsker aa få opp informasjon om N siste gjennomforte treningsokter med notater. \n"
							+ "Tast '3' om du onsker aa se resultatlogg for en gitt ovelse i et gitt tidsintervell \n"
							+ "Tast '4' om du onsker aa lage ovelsesgrupper og finne ovelser som er i samme gruppe. \n");
		Scanner scanner = new Scanner(System.in);
		int brukerValg = Integer.parseInt(scanner.next());
		scanner.close();
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
		System.out.println("Tast '1' om du onsker aa registrere ett apparat. \n"
							+ "Tast '2' om du onsker aa legge til en ovelse. \n"
							+ "Tast '3' om du onsker aa legge til en treningsokt.");
		Scanner scanner = new Scanner(System.in);
		int brukerValg = Integer.parseInt(scanner.next());
		if (brukerValg == 1) {
			
		}
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
