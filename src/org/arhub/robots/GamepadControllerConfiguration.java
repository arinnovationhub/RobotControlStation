package org.arhub.robots;

import java.util.ArrayList;
import java.util.List;

public class GamepadControllerConfiguration {

	private String name;
	private List<GamepadControllerComponentConfiguration> componentConfiguration = new ArrayList<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<GamepadControllerComponentConfiguration> getComponentConfiguration() {
		return componentConfiguration;
	}

	public void setComponentConfiguration(List<GamepadControllerComponentConfiguration> componentConfiguration) {
		this.componentConfiguration = componentConfiguration;
	}

	@Override
	public String toString() {
		return name;
	}

}
