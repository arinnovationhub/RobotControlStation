package org.arhub.robots;

public abstract class ValueRange {

	private boolean isInverted;
	
	public boolean isInverted() {
		return isInverted;
	}

	public void setInverted(boolean isInverted) {
		this.isInverted = isInverted;
	}

	public int GetValue(float x) {
		if(isInverted){
			return map(x, getInMin(), getInMax(), getOutMax(), getOutMin()).intValue();
		}else{
			return map(x, getInMin(), getInMax(), getOutMin(), getOutMax()).intValue();
		}
		
	}

	public abstract float getInMin();

	public abstract float getInMax();

	public abstract float getOutMin();

	public abstract float getOutMax();

	public static Float map(float x, float in_min, float in_max, float out_min, float out_max) {
		return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
	}

}
