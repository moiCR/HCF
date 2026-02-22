package git.moiCR.hcf.api;


import git.moiCR.hcf.Main;
import lombok.Getter;

@Getter
public class API {
    
    @Getter
    private static API instance;
    
    private final Main main;
    
    public API(Main main){
        instance = this;
        this.main = main;
    }
    
}
