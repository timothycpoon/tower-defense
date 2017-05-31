import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;

public abstract class Enemy extends Actor {
	private int id;
	private int distance;
	private Path path;

	public Enemy(int id) {
		super();
		this.id = id;
		path = new BasicPath(this);
	}

	public void attacked(int damage) {
		takeDamage(damage);
		System.out.println("I was hit!" + getHealth());
		if (getHealth() <= 0 && getWorld() != null) {
			getWorld().addMoney(getBounty());
			Actor bob = new RagDoll();
			bob.setImage(new Image("file:death" + Constants.enemyTypes[id].getImage(), 50, 50, false, false));
			bob.setX(getX());
			bob.setY(getY());
			getWorld().add(bob);
			getWorld().getDeathSound().play();
			getWorld().remove(this);
			System.out.println("I died!");
		}
	}

	/**
	 * Move along a path
	 */
	@Override
	public void act(long now) {
		distance++;
		path.nextMove();

		Base base = getWorld().getBase();
		if (isIntersecting(getX(), getY(), getWidth(), getHeight(), base.getX(), base.getY(), base.getHeight(),
				base.getWidth())) {
			base.attacked(getDamage());
			getWorld().remove(this);
		}

	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public Path getPath() {
		return path;
	}

	public abstract int getDamage();

	public abstract int getBounty();

	public abstract void takeDamage(int damage);

	public abstract int getHealth();

	public int getDistance() {
		return distance;
	}

	public int getID() {
		return id;
	}

}
