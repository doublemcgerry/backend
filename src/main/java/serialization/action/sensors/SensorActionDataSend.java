package serialization.action.sensors;

import websocketecho.ServerController;
import websocketecho.Subscriber;

public class SensorActionDataSend extends SensorsAction {

	private static final long serialVersionUID = 6442731397402415499L;
	int type;
    int accuracy;
    long timestamp;
    float[] values;
	
	@Override
	public void execute(ServerController serverController, Subscriber subscriber) {
		
		//if (this.type==4 || this.type==1) {
//s			serverController.getSensorsChart(this.type,this.values.length).logValues(this.type,this.accuracy,this.timestamp,this.values);
//			System.out.print(this.type);
//			System.out.print("---");
//			System.out.print(this.accuracy);
//			System.out.print("---");
//			System.out.print(this.timestamp);
//			System.out.print("---");
//			for (float f : values) {
//				System.out.print("---");
//				System.out.print(f);
//			}
//			System.out.println("");
		//}
		
		
	}

}
