package games.bevs.survivalgames.game.games;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GameSettings
{
	private boolean damagePVP = true;
	private boolean damageEVP = true;
	private boolean damage = true;
	
	private boolean blockBreak = true;
	private boolean blockPlace = true;
	
	private boolean gracePeriod = true;
}
