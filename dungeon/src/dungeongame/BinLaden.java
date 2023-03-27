package dungeongame;

public class BinLaden extends Villain {

	private final int MAX_HIDE = 3;
	private int hide;

	public BinLaden(String name, int hp, int att, int count) {
		super(name, hp, att, count);
		this.hide = MAX_HIDE;
	}

	public int getHide() {
		return hide;
	}

	@Override
	public void attack(Unit hero) {
		if (hero instanceof Hero) {
			Hero player = (Hero) hero;
			int damage = 0;
			if (this.hide != 0) {
				System.out.printf("%s을 찾지 못했습니다.", this.getName());
				System.out.println("공격이 불가능합니다.");
				System.out.printf("%s가 %s에게 비행기 테러를 성공시켰습니다.\n", this.getName(), player.getName());
				damage = MAX_HIDE * Main.random(this.getAtt(), 80);
				this.setPower(damage);
				this.hide--;
				if (this.hide == 0) {
					System.out.printf("%s의 위치가 발각 되었습니다.", this.getName());
					System.out.println("더 이상 공격 회피가 불가능 합니다.");
				}
			} else {
				System.out.printf("%s가 %s에게 테러 위협을 가합니다.\n", this.getName(), player.getName());
				damage = Main.random(this.getAtt(), 1);
				this.setPower(damage);
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
		}
	}

}
