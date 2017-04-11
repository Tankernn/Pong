package eu.tankernn.pong;

import org.lwjgl.util.vector.Vector2f;

import eu.tankernn.gameEngine.entities.Entity2D;
import eu.tankernn.gameEngine.loader.textures.Texture;

public class Ball extends Entity2D {

	private static final float SPEED = 0.03f;

	private Pad p1, p2;
	private Pad winner = null;

	public Ball(Texture texture, Pad p1, Pad p2) {
		super(texture, new Vector2f(0, 0), 0.03f);
		this.p1 = p1;
		this.p2 = p2;

		double rads = Math.toRadians(Math.random() * 360);
		this.setAngle(rads);
	}

	@Override
	public void update() {
		if (position.y - getSize().y < -1 || position.y + getSize().y > 1) {
			velocity.y *= -1;
			position.y = Math.max(position.y, -1 + getSize().y);
			position.y = Math.min(position.y, 1 - getSize().y);
		}

		Vector2f padPos = null;

		if (this.collides(p1)) {
			padPos = p1.getPosition();
		} else if (this.collides(p2)) {
			padPos = p2.getPosition();
		}

		if (padPos != null) {
			Vector2f delta = Vector2f.sub(position, padPos, null);
			System.out.println(delta);
			double angle;
			if (delta.y >= 0)
				angle = Math.atan(delta.x / delta.y);
			else
				angle = Math.PI - Math.atan(delta.x / Math.abs(delta.y));
			System.out.println(Math.toDegrees(angle));
			this.setAngle(angle);
		}

		if (position.x - getSize().x < -1) {
			winner = p2;
		} else if (position.x + getSize().x > 1) {
			winner = p1;
		}

		super.update();
	}

	private void setAngle(double angle) {
		this.setVelocity(new Vector2f((float) (SPEED * Math.sin(angle)), (float) (SPEED * Math.cos(angle))));
	}

	public Pad getWinner() {
		return winner;
	}
}
