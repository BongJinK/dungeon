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
				Main.sb.append(String.format("%s�� ã�� ���߽��ϴ�. ������ �Ұ����մϴ�.\n", this.getName()));
				Main.sb.append(String.format("%s�� ����� �׷��� �������׽��ϴ�.\n", this.getName()));
				damage = MAX_HIDE * Main.random(this.getAtt(), 80);
				this.setPower(damage);
				this.hide--;
				if (this.hide == 0) {
					Main.sb.append(String.format("%s�� ��ġ�� �߰� �Ǿ����ϴ�.", this.getName()));
					Main.sb.append("�� �̻� ���� ȸ�ǰ� �Ұ��� �մϴ�.");
				}
			} else {
				Main.sb.append(String.format("%s�� �׷� ������ ���մϴ�.\n", this.getName()));
				damage = Main.random(this.getAtt(), 1);
				this.setPower(damage);
			}

			if (player.getDotDamageCount() != 0) {
				Main.sb.append("�������ظ� ���������� �Խ��ϴ�.\n");
				Main.sb.append(String.format("���� ���� : %d (�ִ� 3����)", player.getDotDamageCount()));
				damage += player.getDotDamageCount() * 2;
			}

			player.setHp(player.getHp() - damage);
			if (player.getHp() <= 0)
				player.setHp(0);

			Main.delay();
			Main.sb.append(String.format("%s�� HP�� %d��ŭ �����մϴ�.\n", player.getName(), damage));
			Main.sb.append(String.format("%s�� ���� HP : %d\n", player.getName(), player.getHp()));
			Main.write(Main.sb.toString());
		}
	}

}
