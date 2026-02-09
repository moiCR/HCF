package git.moiCR.hcf.lib.menu.paginated;

import git.moiCR.hcf.Main;
import git.moiCR.hcf.lib.menu.Menu;
import git.moiCR.hcf.lib.menu.button.Button;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public abstract class MenuPaginated extends Menu {

    private Map<Integer, Button> navigateBar;
    private NavigateBarType navigateBarType;
    private int currentPage = 1;

    public MenuPaginated(Main instance, Player player, boolean autoUpdate) {
        super(instance, player, autoUpdate);
        this.navigateBar = new HashMap<>();
        this.navigateBarType = NavigateBarType.TOP;

        navigateBar.putAll(navigateBarType.getBar(this));
    }

    public Map<Integer, Button> getFinalButtons(){
        Map<Integer, Button> finalButtons = new HashMap<>();
        int size = getSize();
        int maxButtons = size - 9;

        getPaginatedButtons().forEach((slot, button) -> finalButtons.put(navigateBarType.getSlot(slot, maxButtons, currentPage), button));

        finalButtons.putAll(navigateBar);

        return finalButtons;
    }

    @Override
    public Map<Integer, Button> getButtons() {
        return getFinalButtons();
    }

    @Override
    public void clean() {
        super.clean();
        navigateBar.clear();
        this.getPaginatedButtons().clear();
    }

    public void next(){
        if (currentPage == getMaxPages()) return;
        currentPage += 1;
        this.update();
    }

    public void prev(){
        if (currentPage == 1){
            return;
        }

        currentPage -= 1;
        this.update();
    }

    public int getMaxPages(){
        int maxButtons = getSize();
        int totalButtons = getPaginatedButtons().size();
        return (int) Math.ceil((double) totalButtons / maxButtons);
    }

    public abstract Map<Integer, Button> getPaginatedButtons();
}
