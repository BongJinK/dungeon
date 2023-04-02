package dungeongame;

abstract public class Unit {

	private int posY;
	private int posX;

	private int hp;
	private int att;
	private String name;
	


	public Unit(String name) {
		this.name = name;
	}

	public Unit(String name, int hp, int att) {
		this.name = name;
		this.hp = hp;
		this.att = att;

		this.posY = 0;
		this.posX = 0;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public String getName() {
		return name;
	}

	public int getHp() {
		return this.hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getAtt() {
		return this.att;
	}

	abstract void attack(Unit unit);
}
