package dungeongame;

public class Hela extends Villain {

	private final int EVEN = 2;
	private int healingfactor;
	private String saying;

	public Hela(String name, int hp, int att) {
		super(name, hp, att);
		healingfactor = 10;
		saying = "Kneel. Before your Queen";
	}

	public int getHealingfactor() {
		return healingfactor;
	}

	public String getDeath() {
		return saying;
	}

	public void attack(Unit hero) {
		this.setPower(Main.random(this.getAtt(), 110));
		int regen = Main.random(this.getAtt(), 1) * healingfactor;

		if (hero instanceof Hero) {
			Hero player = (Hero) hero;

			int damage = this.getPower();
			if (Main.random(healingfactor, 0) % EVEN == 0) {
				System.out.println(saying);
				System.out.printf("%s가 무자비한 공격을 가합니다.\n", this.getName());
				this.setPower(this.getPower() * EVEN);
				damage *= EVEN;
				this.setHp(this.getHp() + regen * EVEN);
			} else {
				System.out.printf("%s가 %s을(를) 공격합니다.\n", this.getName(), player.getName());
				this.setHp(this.getHp() + regen);
			}

			if (player.getDotDamageCount() != 0) {
				System.out.println("출혈피해를 지속적으로 입습니다.");
				System.out.printf("출혈 스택 : %d", player.getDotDamageCount());
				System.out.println("(최대 3스택)");
				damage += player.getDotDamageCount() * 2;
			}

			player.setHp(player.getHp() - damage);
			if (player.getHp() <= 0)
				player.setHp(0);

			Main.delay();
			System.out.printf("%s의 HP가 %d만큼 감소합니다.\n", player.getName(), damage);
			System.out.printf("%s의 현재 HP : %d\n", player.getName(), player.getHp());
			System.out.printf("%s HP : %d\n", this.getName(), this.getHp());
		}
	}

}
