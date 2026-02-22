package git.moiCR.hcf.storage;

import git.moiCR.hcf.profile.Profile;
import git.moiCR.hcf.teams.Team;

public interface IStorage {

    void loadProfiles();
    void saveProfiles();
    void saveProfile(Profile profile, boolean async);

    void loadTeams();
    void saveTeams();
    void saveTeam(Team team, boolean async);


    void load();
    void unload();

}
