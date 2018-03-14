package DataMod;
import java.sql.SQLException;
import java.util.Scanner;
import DataMod.SQLConnector;

public class TextGUI {

	public TextGUI() {
		this.runProgram();
	}
	
	Scanner scanner = new Scanner(System.in);
	
	public void runProgram() {
		System.out.println("Velkommen til GetBigMuscles treningsapp, hva onsker du � gjore? Du har fire valg!");
		System.out.println("Tast '1' om du onsker aa registrere apparater, ovelser eller treninsokt med tilhorende data.\n"
							+ "Tast '2' om du onsker aa f� opp informasjon om N siste gjennomforte treningsokter med notater. \n"
							+ "Tast '3' om du onsker aa se resultatlogg for en gitt ovelse i et gitt tidsintervell \n"
							+ "Tast '4' om du onsker aa lage ovelsesgrupper og finne ovelser som er i samme gruppe. ");
		int brukerValg = Integer.parseInt(scanner.next());
		if (brukerValg == 1) {
			this.registerShit();
		} else if (brukerValg == 2) {
			System.out.println("Hvor mange av de siste øktene ønsker du å se?");
			this.getInfoBoutSessions(scanner.nextInt());
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
		int brukerValg = Integer.parseInt(scanner.next());
		if (brukerValg == 1) {
			this.registerApparat();
		} else if (brukerValg == 2) {
			System.out.println("Tast '1' om du onsker aa legge til ovelse som inkluderer et apparat. \n"
					+ "Tast '2' om du onsker aa legge til ovelse som IKKE inkluderer et apparat.");
			int brukerValg2 = Integer.parseInt(scanner.next());
			if (brukerValg2 == 1) {
				this.registerExerciseApp();
			} else if (brukerValg2 == 2) {
				this.registerExerciseNonApp();
			} else {
				System.out.println("Skjerpings..!");
			}
		} else if (brukerValg == 3) {
			this.registerSession();
		} else {
			System.out.println("Du maa da klare aa legge inn riktig tall...");
		}
	}
	
	
	private void getResultLogg() {
		System.out.println("Your session? Horrible..");
	}
	
	private void registerSessionGroup() {
		System.out.println("Ya feel lucky..? PUNCK!");
	}
	
	private void registerApparat() {
		System.out.println("Hva heter apparatet?: ");
		String apparat = scanner.next();
		System.out.println("Gi en kort beskrivelse: ");
		String beskrivelse = scanner.next();
		SQLConnector.createApparatus(apparat, beskrivelse);
	}	

	private void registerSession() {
		System.out.println("Hva er datoen? (yyyy-mm-dd)");
		String date = scanner.next();
		System.out.println("N�r p� dagen ble okten gjennomfort?: (hh:mm)");
		String time = scanner.next();
		System.out.println("P� en skala fra 1-10, hvor god form er du i?: ");
		int form = Integer.parseInt(scanner.next());
		System.out.println("P� en skala fra 1-10, hvor bra gikk okten?: ");
		int perf = Integer.parseInt(scanner.next());
		System.out.println("Skriv et kjapt notat om okten: ");
		String note = scanner.next();
		System.out.println("Hvor lang tid tok okten, i minutter?: ");
		int dur = Integer.parseInt(scanner.next());
		SQLConnector.createSession(date, time, form, perf, note, dur);
	}
	
	private void getInfoBoutSessions(int antall) {
		try {
			SQLConnector.getSessions(antall);
		} catch (SQLException e) {
			System.out.println("error");
		}
	}

	private void registerExerciseNonApp() {
		System.out.println("Yo yo yo!");
	}
	
	private void registerExerciseApp() {
		System.out.println("Good job motherfucker!");
	}
}
