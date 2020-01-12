package Protocol;

public enum PhaseStatus {

	Frozed, Waiting, Active, Closed, Waiting_To_Set_Evaluator, Waiting_To_Set_Time_Required_For_Evaluation,
	Active_And_Waiting_For_Time_Extension, Waiting_To_Set_Time_Required_For_Phase,
	Waiting_To_Confirm_Time_Required_For_Phase, Waiting_To_Set_Executer, Waiting_For_More_Data;
	
	public String nameNo_() {
		return this.name().replace('_', ' ');
	}
	
	public static PhaseStatus valueOfAdvanced(String str) {
		String res = str.replace(' ', '_');
		return valueOf(res);
	}
	
	public static void main(String[] args) {
		System.out.println(Waiting_To_Set_Time_Required_For_Phase.nameNo_());
	}
}
