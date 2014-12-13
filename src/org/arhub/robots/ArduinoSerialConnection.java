package org.arhub.robots;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;

import org.apache.commons.lang3.StringUtils;

public class ArduinoSerialConnection {

	private SerialPort serialPort;

	public List<String> GetComPorts() {
		return Arrays.asList(SerialPortList.getPortNames());
	}

	public void Connect(String comPort) throws SerialPortException {
		serialPort = new SerialPort(comPort);
		try{
			serialPort.openPort();
		}catch(SerialPortException ex){
			//if busy, try one more time.
			if(ex.getExceptionType().equals("Port busy")){
				serialPort.openPort();
			}else{
				throw ex;
			}
		}
		serialPort.setParams(SerialPort.BAUDRATE_9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
				SerialPort.PARITY_NONE);
	}

	public void SendGamepadState(List<ControlState> controlStates) throws SerialPortException {
		List<String> states = new ArrayList<String>();
		for (ControlState controlState : controlStates) {
			states.add(Integer.toString(controlState.value));
		}

		if (serialPort != null) {
			serialPort.writeBytes(((StringUtils.join(states, ",") + "\n").getBytes()));
		}
		System.out.println(StringUtils.join(states, ","));

	}

	public void disconnect() throws SerialPortException {
		serialPort.closePort();
	}

}
