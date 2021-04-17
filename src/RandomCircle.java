import java.util.Random;
import javafx.scene.shape.*;
import javafx.util.Duration;
import javafx.scene.paint.*;
import javafx.animation.*;

public class RandomCircle extends Circle {
	
	Random randomObject = new Random();
	int radius = (int)randomObject.nextInt(15) + 15;

	RandomCircle(int windowHeight, int windowWidth) {
		this.setRadius(radius);
		this.setCenterX(radius);
		this.setCenterY(radius);
		this.setFill(randomColor());
		this.setStroke(Color.BLACK);
	}

	public Color randomColor() {
		return Color.rgb(randomObject.nextInt(256), randomObject.nextInt(256), randomObject.nextInt(256));
	}
	
	public int getRad() {
		return radius;
	}
}
