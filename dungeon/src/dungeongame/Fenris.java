package dungeongame;

public class Fenris extends Villain {

	private final int GIGANTIC = 400;
	private int singleUse;

	public Fenris(String name) {
		super(name);
	}

	public Fenris(String name, int hp, int att) {
		super(name, hp, att);
		this.setCount(3);
		this.singleUse = 1;
	}

	public int getGIGANTIC() {
		return GIGANTIC;
	}

	public int getSingleUse() {
		return this.singleUse;
	}

	public void setSingleUse(int singleUse) {
		this.singleUse = singleUse;
	}

	@Override
	public void attack(Unit hero) {
		if (hero instanceof Hero) {
			Hero player = (Hero) hero;

			this.setPower(Main.random(this.getAtt(), 20));
			int damage = this.getPower();
			if (this.singleUse == 0) {
				System.err.printf("%s가 흥분하여 공격력이 소폭 상승합니다.\n", this.getName());
				damage += 10;
			}
			if (player.getDotDamageCount() == 0) {
				player.setHp(player.getHp() - damage);
			} else {
				damage += player.getDotDamageCount() * 2;
				System.out.println("출혈피해를 지속적으로 입습니다.");
				System.out.printf("출혈 스택 : %d", player.getDotDamageCount());
				System.out.println("(최대 3스택)");
				player.setHp(player.getHp() - damage);
			}

			if (player.getHp() <= 0) {
				player.setHp(0);
			}
			System.out.printf("%s가 %s을(를) 공격합니다.\n", this.getName(), hero.getName());
			Main.delay();
			System.out.printf("%s의 HP가 %d만큼 감소합니다.\n", hero.getName(), damage);
			System.out.printf("%s의 현재 HP : %d\n", hero.getName(), hero.getHp());
		}
	}

}
