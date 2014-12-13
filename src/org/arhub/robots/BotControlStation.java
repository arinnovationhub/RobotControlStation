package org.arhub.robots;

public class BotControlStation {

	public static void main(String[] args) throws Exception {
		ArduinoSerialConnection connection = new ArduinoSerialConnection();
		new BotControlStationUI(new GamepadController(connection), connection).Show();
	}
}
