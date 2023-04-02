package dungeongame;

public class VillainBinLaden extends Villain {

	private final int MAX_HIDE = 3;
	private int hide;

	public VillainBinLaden(String name, int hp, int att, int count) {
		super(name, hp, att, count);
		this.hide = MAX_HIDE;
	}

	public int getHide() {
		return hide;
	}

	@Override
	public void attack(Unit hero) {
		if (hero instanceof UnitHero) {
			UnitHero player = (UnitHero) hero;
			int damage = 0;
			Main.sb.setLength(0);
			if (this.hide != 0) {
				Main.sb.append(String.format("%s을 찾지 못했습니다. 공격이 불가능합니다.\n", this.getName()));
				Main.sb.append(String.format("%s가 비행기 테러를 성공시켰습니다.\n", this.getName()));
				damage = MAX_HIDE * Main.random(this.getAtt(), 80);
				this.setPower(damage);
				this.hide--;
				if (this.hide == 0) {
					Main.sb.append(String.format("%s의 위치가 발각 되었습니다.", this.getName()));
					Main.sb.append("더 이상 공격 회피가 불가능 합니다.");
				}
			} else {
				Main.sb.append(String.format("%s가 테러 협박을 가합니다.\n", this.getName()));
				damage = Main.random(this.getAtt(), 1);
				this.setPower(damage);
			}

			if (player.getDotDamageCount() != 0) {
				Main.sb.append("출혈피해를 지속적으로 입습니다.\n");
				Main.sb.append(String.format("출혈 스택 : %d (최대 3스택)", player.getDotDamageCount()));
				damage += player.getDotDamageCount() * 2;
			}

			player.setHp(player.getHp() - damage);
			if (player.getHp() <= 0)
				player.setHp(0);

			Main.delay();
			Main.sb.append(String.format("%s의 HP가 %d만큼 감소합니다.\n", player.getName(), damage));
			Main.sb.append(String.format("%s의 현재 HP : %d\n", player.getName(), player.getHp()));
			Main.write(Main.sb.toString());
		}
	}

}
