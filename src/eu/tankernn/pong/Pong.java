package eu.tankernn.pong;

import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;

import java.io.FileNotFoundException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

import eu.tankernn.gameEngine.GameLauncher;
import eu.tankernn.gameEngine.TankernnGame;
import eu.tankernn.gameEngine.entities.Entity2D;
import eu.tankernn.gameEngine.loader.font.FontType;
import eu.tankernn.gameEngine.loader.font.GUIText;
import eu.tankernn.gameEngine.loader.textures.Texture;
import eu.tankernn.gameEngine.renderEngine.gui.GuiTexture;
import eu.tankernn.gameEngine.util.InternalFile;

public class Pong extends TankernnGame {
	private Pad leftPlayer, rightPlayer;
	private Ball ball;
	private Entity2D[] entities;
	private GUIText scoreText, messageText;
	private Texture white;

	private int leftScore = 0, rightScore = 0;
	private boolean running = true;

	public Pong() {
		super("Pong");
		try {
			FontType font = new FontType(loader.loadTexture("arial.png"), new InternalFile("arial.fnt"));
			scoreText = new GUIText("0  0", 2, font, new Vector2f(-0.5f, 0), 2, true).setColor(1, 1, 1);
			messageText = new GUIText("", 2, font, new Vector2f(-0.5f, 0.5f), 2, true).setColor(1, 1, 1);
			white = loader.loadTexture("white.png");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		leftPlayer = new Pad(white, new Vector2f(-0.9f, 0.1f), Keyboard.KEY_A, Keyboard.KEY_Z);
		rightPlayer = new Pad(white, new Vector2f(0.9f, 0.1f), Keyboard.KEY_K, Keyboard.KEY_M);
		ball = new Ball(white, leftPlayer, rightPlayer);
		entities = new Entity2D[] { leftPlayer, rightPlayer, ball };

		for (Entity2D e : entities)
			guiMaster.loadGui(e);

		guiMaster.loadGui(new GuiTexture(white, new Vector2f(0.0f, 0.0f), new Vector2f(0.005f, 1.0f)));

		textMaster.loadText(scoreText);
	}

	public void update() {
		if (!running) {
			if (Keyboard.isKeyDown(Keyboard.KEY_R)) {
				ball = new Ball(white, leftPlayer, rightPlayer);
				running = true;
				entities = new Entity2D[] { leftPlayer, rightPlayer, ball };
				textMaster.removeText(messageText);
			} else {
				return;
			}
		}

		for (Entity2D e : entities)
			e.update();

		if (ball.getWinner() != null) {
			if (ball.getWinner().equals(leftPlayer)) {
				System.out.println("Player 1 wins.");
				messageText.setText("Player 1 wins.");
				leftScore++;
			} else {
				System.out.println("Player 2 wins.");
				messageText.setText("Player 2 wins.");
				rightScore++;
			}
			textMaster.loadText(messageText);
			running = false;
		}

		scoreText.setText(String.format("%d  %d", leftScore, rightScore));
	}

	public void render() {
		glClearColor(0, 0, 0, 1);
		glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

		guiMaster.render();
		textMaster.render();
		super.render();
	}

	public void cleanUp() {
		guiMaster.cleanUp();
		textMaster.cleanUp();
		super.cleanUp();
	}

	public static void main(String[] args) {
		GameLauncher.init("Pong", 800, 800);
		GameLauncher.launch(new Pong());
	}
}
