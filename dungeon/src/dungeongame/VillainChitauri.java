package dungeongame;

public class VillainChitauri extends Villain {

	private int repeat;

	public VillainChitauri(String name) {
		super(name);
	}

	public VillainChitauri(String name, int hp, int att, int count) {
		super(name, hp, att, count);
	}

	@Override
	public void attack(Unit hero) {
		this.repeat = 2;
		this.setPower(Main.random(this.getAtt(), 10));

		if (hero instanceof UnitHero) {
			UnitHero player = (UnitHero) hero;
			Main.sb.setLength(0);
			Main.sb.append(String.format("%s�� %s��(��) �����մϴ�.\n", this.getName(), player.getName()));
			Main.sb.append(String.format("%s�� �ѹ� �� �����մϴ�.\n", this.getName()));

			int damage = this.getPower();
			for (int i = 0; i < this.repeat; i++) {
				if (player.getDotDamageCount() == 0)
					player.setHp(player.getHp() - damage);
				else {
					damage = this.getPower() + player.getDotDamageCount() * 2;
					Main.sb.append("�������ظ� ���������� �Խ��ϴ�.\n");
					Main.sb.append(String.format("���� ���� : %d (�ִ� 3����)\n", player.getDotDamageCount()));
					player.setHp(player.getHp() - damage);
				}
			}
			if (player.getHp() <= 0)
				player.setHp(0);

			Main.delay();
			damage *= 2;
			Main.sb.append(String.format("%s�� HP�� %d��ŭ �����մϴ�.\n", player.getName(), damage));
			Main.sb.append(String.format("%s�� ���� HP : %d\n", player.getName(), player.getHp()));
			Main.write(Main.sb.toString());
		}
	}

}
