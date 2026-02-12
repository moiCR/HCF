package git.moiCR.hcf.lib.menu.paginated;


import git.moiCR.hcf.lib.menu.button.Button;
import git.moiCR.hcf.lib.menu.misc.DecorationButton;
import git.moiCR.hcf.lib.menu.misc.NextButton;
import git.moiCR.hcf.lib.menu.misc.PrevButton;

import java.util.HashMap;
import java.util.Map;

public enum NavigateBarType {

    TOP{

        @Override
        public int getSlot(int slot, int maxButtons, int page) {
            return slot - ((maxButtons) * (page - 1)) + 9;
        }

        @Override
        public Map<Integer, Button> getBar(MenuPaginated menu) {
            return createBar(menu, 0, 8, menu.getCurrentPage());
        }
    },

    BOTTOM{
        @Override
        public int getSlot(int slot, int maxButtons, int page) {
            return slot -((maxButtons) * (page - 1));
        }

        @Override
        public Map<Integer, Button> getBar(MenuPaginated menu) {
            var nextSlot = menu.getSize() - 1;
            var prevSLot = nextSlot - 8;

            return createBar(menu, prevSLot, nextSlot, menu.getCurrentPage());
        }
    };


    protected Map<Integer, Button> createBar(MenuPaginated menu, int prevSlot, int nextSlot, int currentPage){
        Map<Integer, Button> buttons = new HashMap<>();

        for (int i = 0; i < 9; i++){
            buttons.put(i, new DecorationButton());
        }

        if (currentPage > 1){
            buttons.put(prevSlot, new PrevButton(menu, menu.getPlayer()));
        }

        buttons.put(nextSlot, new NextButton(menu, menu.getPlayer()));

        return buttons;
    }


    public abstract int getSlot(int slot, int maxButtons, int page);
    public abstract Map<Integer, Button> getBar(MenuPaginated menu);
}
