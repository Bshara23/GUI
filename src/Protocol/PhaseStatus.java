package Protocol;

public enum PhaseStatus {

	Frozed, Waiting, Active, Closed, Waiting_To_Set_Evaluator;
	
	
	
	
	public static void main(String[] args) {
		System.out.println(Waiting_To_Set_Evaluator.name().replace('_', ' '));
	}
}
