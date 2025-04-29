package exercicios.java.basico.exercicio02;

public class Main {
	public static void main(String[] args) {
		Clock brlclock = new BRLClock();
		brlclock.setSecond(45);
		brlclock.setMinute(15);
		brlclock.setHour(25);
		
		System.out.println(brlclock.getTime());
		System.out.println(new USClock().convert(brlclock).getTime());
	}
}
