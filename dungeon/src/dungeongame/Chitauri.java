package dungeongame;

public class Chitauri extends Villain {

	private int repeat;

	public Chitauri(String name) {
		super(name);
	}

	public Chitauri(String name, int hp, int att, int count) {
		super(name, hp, att, count);
	}

	@Override
	public void attack(Unit hero) {
		this.repeat = 2;
		this.setPower(Main.random(this.getAtt(), 10));

		if (hero instanceof Hero) {
			Hero player = (Hero) hero;

			System.out.printf("%s가 %s을(를) 공격합니다.\n", this.getName(), player.getName());
			System.out.printf("%s가 한번 더 공격합니다.\n", this.getName());
			int damage = this.getPower();
			for (int i = 0; i < this.repeat; i++) {
				if (player.getDotDamageCount() == 0)
					player.setHp(player.getHp() - damage);
				else {
					damage = this.getPower() + player.getDotDamageCount() * 2;
					System.out.println("출혈피해를 지속적으로 입습니다.");
					System.out.printf("출혈 스택 : %d", player.getDotDamageCount());
					System.out.println("(최대 3스택)");
					player.setHp(player.getHp() - damage);
				}
			}
			if (player.getHp() <= 0)
				player.setHp(0);

			Main.delay();
			damage *= 2;
			System.out.printf("%s의 HP가 %d만큼 감소합니다.\n", player.getName(), damage);
			System.out.printf("%s의 현재 HP : %d\n", player.getName(), player.getHp());
		}
	}

}
