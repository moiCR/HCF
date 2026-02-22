package git.moiCR.hcf.storage.impl;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.ReplaceOptions;
import git.moiCR.hcf.Main;
import git.moiCR.hcf.profile.HCFProfile;
import git.moiCR.hcf.storage.IStorage;

import git.moiCR.hcf.teams.type.player.TeamPlayer;
import org.bson.Document;
import git.moiCR.hcf.teams.Team;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class MongoStorage implements IStorage {

    private final Main instance;
    private final MongoClient client;
    private final MongoDatabase database;

    private final MongoCollection<Document> teamCollection;
    private final MongoCollection<Document> profileCollection;

    public MongoStorage(Main instance, String uri) {
        this.instance = instance;
        this.client = MongoClients.create(uri);
        this.database = client.getDatabase("hcf");

        this.teamCollection = database.getCollection("teams");
        this.profileCollection = database.getCollection("profiles");
    }

    @Override
    public void load() {
        loadTeams();
        loadProfiles();
    }

    @Override
    public void unload() {
        saveProfiles();
        saveTeams();

        client.close();
    }


    @Override
    public void loadProfiles() {
        try{
            var documents = profileCollection.find();
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Loading profiles...");
            documents.forEach(profileDoc -> {
                var profile = new HCFProfile(instance, profileDoc);
                instance.getProfileManager().addProfile(profile);
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void saveProfiles() {
        var profiles = instance.getProfileManager().getProfiles().values();

        for (var profile : profiles) {
            saveProfile(profile, false);
        }
    }

    @Override
    public void saveProfile(HCFProfile profile, boolean async) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Saving profile " + profile.getName());
        try{
            if (async){
                Bukkit.getScheduler().runTaskAsynchronously(instance, () -> saveProfile(profile, false));
                return;
            }

            var document = profile.toDocument();
            profileCollection.replaceOne(new Document("_id",
                            profile.getId().toString()), document,
                    new ReplaceOptions().upsert(true));


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void loadTeams() {
        try{
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Loading teams...");
            var documents = teamCollection.find();
            documents.forEach(document -> {
                String type = document.getString("type");

                switch (type){
                    case "TeamPlayer" -> {
                        new TeamPlayer(instance, document);
                    }
                }
            });
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void saveTeams() {
        var teamList = instance.getTeamManager().getTeams();
        teamList.forEach(team -> saveTeam(team, false));
    }

    @Override
    public void saveTeam(Team team, boolean async) {
        if (async){
            Bukkit.getScheduler().runTaskAsynchronously(instance, () -> saveTeam(team, false));
            return;
        }

        try{
            var document = team.toDocument();
            document.append("type", team.getClass().getSimpleName());

            teamCollection.replaceOne(new Document("_id",
                    team.getId().toString()), document,
                    new ReplaceOptions().upsert(true));

        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
