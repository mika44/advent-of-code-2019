package fr.game.advent.day13;

public enum JoystickOrder {

	NEUTRAL  (0L),
	TO_RIGHT (+1L),
	TO_LEFT	 (-1L);
	
	private Long joystickOrder;

	private JoystickOrder(Long joystickOrder) {
		this.joystickOrder = joystickOrder;
	}

	public Long getJoystickOrder() {
		return joystickOrder;
	}
}
