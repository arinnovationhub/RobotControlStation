package org.arhub.robots;

public class ControlState {

	public ControlState(String name, int value) {
		this.value = value;
		this.name = name;
	}

	public int value;
	public String name;

	@Override
	public String toString() {
		return name + ":" + value;
	}

}
