package DataMod;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import DataMod.SQLConnector;

public class TextGUI {

	public TextGUI() {
		this.runProgram();
	}
	
	Scanner scanner = new Scanner(System.in);
	
	public void runProgram() {
		System.out.println("Velkommen til GetBigMuscles treningsapp, hva onsker du aa gjore? Du har fire valg!");
		System.out.println("Tast '1' om du onsker aa registrere apparater, ovelser eller treninsokt med tilhorende data.\n"
							+ "Tast '2' om du onsker aa faa opp informasjon om N siste gjennomforte treningsokter med notater. \n"
							+ "Tast '3' om du onsker aa se resultatlogg for en gitt ovelse i et gitt tidsintervell \n"
							+ "Tast '4' om du onsker aa lage ovelsesgrupper og finne ovelser som er i samme gruppe. ");
		int brukerValg = Integer.parseInt(scanner.next());
		if (brukerValg == 1) {		// Ferdig
			this.registerShit();
		} else if (brukerValg == 2) {	// Ferdig
			System.out.println("Hvor mange av de siste oktene onsker du aa se?");
			this.getInfoBoutSessions(scanner.nextInt());
		} else if (brukerValg == 3) {
			this.getResultLogg();
		} else if (brukerValg == 4) {	// Ferdig
			this.registerExerciseGroup();
		} else {
			System.out.println("Du har ikke valgt et gyldig tall");
		}
	}
	
	public static void main(String[] args) {
		TextGUI txtgui = new TextGUI();
	}
	
	// Denne metoden er ferdig implementert, nesten
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
	
	// Nesten ferdig koda og implementert
	private void getResultLogg() {

		System.out.println("Your session? Horrible..");
		try {
			SQLConnector.getAppExercise("Bench", "2018-09-09", "2018-11-11");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Er ovelsen med eller uten apparat? Tast '1' for apparat, '2' ellers.");
		int choise = Integer.parseInt(scanner.next());
		// Sender inn true om den er med apparat, false om den er uten
		System.out.println("Hvilken ovelse vil du se resultatloggen til?");
		String name = scanner.next();
		System.out.println("Innenfor hvilket tidsrom vil du se resultatloggen? (yyyy-mm-dd=yyyy-mm-dd)");
		String timeIntervall = scanner.next();
		// Forste element gir startdato, siste element gir sluttdato
		String[] intervallList = timeIntervall.trim().split("=");
		if (choise == 1) {
			SQLConnector.getAppExercise(name, intervallList[0], intervallList[1]);
		} else if (choise == 2) {
			SQLConnector.getNonsAppExercise(name, intervallList[0], intervallList[1]);
		}
	}
	
	// Ferdig koda og implementert
	private void registerExerciseGroup() {
		System.out.println("Hva heter ovelsesgruppen?");
		String name = scanner.next();
		try {
			SQLConnector.createExerciseGroup(name);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Ferdig koda og implementert
	private void registerApparat() {
		System.out.println("Hva heter apparatet?: ");
		String apparat = scanner.next();
		System.out.println("Gi en kort beskrivelse: ");
		String beskrivelse = scanner.next();
		SQLConnector.createApparatus(apparat, beskrivelse);
	}	
	
	// Ferdig koda og implementert
	private void registerSession() {
		System.out.println("Hva er datoen? (yyyy-mm-dd)");
		String date = scanner.next();
		System.out.println("Naar pa dagen ble okten gjennomfort?: (hh:mm)");
		String time = scanner.next();
		System.out.println("Paa en skala fra 1-10, hvor god form er du i?: ");
		int form = Integer.parseInt(scanner.next());
		System.out.println("Paa en skala fra 1-10, hvor bra gikk okten?: ");
		int perf = Integer.parseInt(scanner.next());
		System.out.println("Hvor lang tid tok okten, i minutter?: ");
		int dur = Integer.parseInt(scanner.next());
		// Dummy nextline() som fanger opp '\n', m� v�re der!
		String noteUseless = scanner.nextLine();
		System.out.println("Skriv et kjapt notat om okten: ");
		String note = scanner.nextLine();
		SQLConnector.createSession(date, time, form, perf, note, dur);
	}
	
	private void getInfoBoutSessions(int antall) {
		try {
			SQLConnector.getSessions(antall);
		} catch (SQLException e) {
			System.out.println("error");
		}
	}
	
	// Ferdig koda og implementert, nesten
	private void registerExerciseNonApp() {
		System.out.println("Hva heter ovelsen?");
		String name = scanner.next();
		System.out.println("Hva er ID'en til okten?");
		int id = Integer.parseInt(scanner.next());
		System.out.println("Angi en kort beskrivelse av ovelsen");
		String note = scanner.next();
		// Maa vise ovelsesgrupper med ID
		System.out.println("Hva er ID'en til ovelsesgruppa?");
		int id1 = Integer.parseInt(scanner.next());
		SQLConnector.createNonAppExercise(name, id, id1, note);
	}
	
	// Maa bare legge inn tabeller som viser nodvendig informasjon
	private void registerExerciseApp() {
		System.out.println("Hva heter ovelsen?");
		String name = scanner.next();
		try {
			SQLConnector.getSessions();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Hva er ID'en til ovelsen?");
		int id = Integer.parseInt(scanner.next());
		System.out.println("Hvor mange sett?");
		int sett = Integer.parseInt(scanner.next());
		System.out.println("Hvor mye vakt?");
		int weight = Integer.parseInt(scanner.next());
		// Vis alle apparater med tilhorende ID
		System.out.println("Hva er ID'en til apparatet?");
		int id2 = Integer.parseInt(scanner.next());
	}
}
