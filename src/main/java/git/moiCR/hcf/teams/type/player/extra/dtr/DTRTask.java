package git.moiCR.hcf.teams.type.player.extra.dtr;

import git.moiCR.hcf.Main;
import git.moiCR.hcf.teams.type.player.TeamPlayer;
import lombok.AllArgsConstructor;
import org.bukkit.scheduler.BukkitRunnable;

@AllArgsConstructor
public class DTRTask implements Runnable {

    private static final double REGEN_PER_MINUTE = 0.1;
    private final Main instance;

    @Override
    public void run() {
        for (TeamPlayer team : instance.getTeamManager().getPlayerTeams()){
            if (team.isFrozen()) continue;

            double maxDTR = team.getMaxDtr();
            double currentDTR = team.getDtr();

            if (currentDTR > maxDTR){
                continue;
            }

            double newDtr = Math.min(maxDTR, currentDTR + (REGEN_PER_MINUTE / 60));
            team.setDtr(newDtr);

            if (newDtr >= maxDTR){
                //message
            }
        }
    }
}
