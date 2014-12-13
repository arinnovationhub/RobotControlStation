package org.arhub.robots;

import java.util.ArrayList;
import java.util.List;

import jssc.SerialPortException;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

public class GamepadController {

	public GamepadController(ArduinoSerialConnection arduinoSerialConnection) {
		this.arduinoSerialConnection = arduinoSerialConnection;
	}

	private ArduinoSerialConnection arduinoSerialConnection;
	private Controller controller;
	private GamepadControllerConfiguration currentGamepadConfiguration = null;
	private boolean running = false;

	public List<GamepadControllerConfiguration> getAvailableGamepadControllers() {
		List<GamepadControllerConfiguration> gamepadConfigrations = new ArrayList<GamepadControllerConfiguration>();
		for (Controller controller : ControllerEnvironment.getDefaultEnvironment().getControllers()) {
			GamepadControllerConfiguration gamepadConfig = new GamepadControllerConfiguration();
			gamepadConfig.setName(controller.getName());

			for (Component component : controller.getComponents()) {
				GamepadControllerComponentConfiguration componentConfig = new GamepadControllerComponentConfiguration();
				componentConfig.setIdentifier(component.getIdentifier());
				componentConfig.setName(component.getName());
				componentConfig.setInverted(false);
				componentConfig.setAnalog(component.isAnalog());
				gamepadConfig.getComponentConfiguration().add(componentConfig);
			}
			gamepadConfigrations.add(gamepadConfig);
		}
		return gamepadConfigrations;
	}

	public void setControllerConfiguration(GamepadControllerConfiguration gamepadConfiguration) {
		for (Controller controller : ControllerEnvironment.getDefaultEnvironment().getControllers()) {
			if (controller.getName().equals(gamepadConfiguration.getName())) {
				this.controller = controller;
			}
			this.currentGamepadConfiguration = gamepadConfiguration;
		}
	}

	public void Start() {
		if (running == true)
			return;
		running = true;

		new Thread(new Runnable() {

			@Override
			public void run() {
				while (running) {
					List<ControlState> controlStates = new ArrayList<ControlState>();
					controller.poll();
					for (GamepadControllerComponentConfiguration componentConfig : currentGamepadConfiguration
							.getComponentConfiguration()) {

						if (!componentConfig.isActive())
							continue;

						Component component = controller.getComponent(componentConfig.getIdentifier());
						int data = componentConfig.getValueRange().GetValue(component.getPollData());
						ControlState state = new ControlState(componentConfig.getName(), data);
						controlStates.add(state);
					}
					try {
						arduinoSerialConnection.SendGamepadState(controlStates);
					} catch (SerialPortException e1) {
						//eat the exception
					}

					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
						running = false;
					}

				}
				System.out.println("Done sending controller state");

			}
		}).start();
	}

	public void Stop() {
		running = false;
	}

}
