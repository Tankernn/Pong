package eu.tankernn.pong;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;

import eu.tankernn.gameEngine.entities.Entity2D;
import eu.tankernn.gameEngine.loader.textures.Texture;

public class Pad extends Entity2D {

	private static final float SPEED = 0.05f;

	private int upKey, downKey;

	public Pad(Texture texture, Vector2f position, int upKey, int downKey) {
		// TODO Fix size of this thing, render single-color textures
		super(texture, position, 0.3f);
		this.upKey = upKey;
		this.downKey = downKey;
		this.position = position;
	}

	@Override
	public void update() {
		if (Keyboard.isKeyDown(upKey) && position.y + getSize().y < 1) {
			this.setVelocity(new Vector2f(0, SPEED));
		} else if (Keyboard.isKeyDown(downKey) && position.y - getSize().y > -1) {
			this.setVelocity(new Vector2f(0, -SPEED));
		} else {
			this.setVelocity(new Vector2f(0, 0));
		}

		super.update();
	}
}
