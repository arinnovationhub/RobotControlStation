package org.arhub.robots;

public class ServoValueRange extends ValueRange {

	@Override
	public float getInMin() {
		return -1f;
	}

	@Override
	public float getInMax() {
		return 1f;
	}

	@Override
	public float getOutMin() {
		return 0f;
	}

	@Override
	public float getOutMax() {
		return 180f;
	}

}
