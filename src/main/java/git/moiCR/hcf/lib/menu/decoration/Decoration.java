package git.moiCR.hcf.lib.menu.decoration;


import git.moiCR.hcf.lib.menu.Menu;
import git.moiCR.hcf.lib.menu.misc.DecorationButton;

public enum Decoration {

    FILL{
        @Override
        public void decorate(Menu menu) {
            int size = menu.getSize();
            var button = new DecorationButton();
            var inventory = menu.getInventory();

            for (int i = 0; i < size; i++){
                inventory.setItem(i, button.getIcon());
            }
        }
    },

    BORDER{
        @Override
        public void decorate(Menu menu) {
            int size = menu.getSize();
            var button = new DecorationButton();
            var inventory = menu.getInventory();

            for (int i = 0; i < 9; i++){
                if (i < size) inventory.setItem(i, button.getIcon());
            }

            var lastRowStart = size - (size % 9);
            for (int i = lastRowStart; i < size; i++){
                inventory.setItem(i, button.getIcon());
            }

            for (int i = 0; i < size/9; i++){
                inventory.setItem(i * 9, button.getIcon());
                if (size > 9){
                    inventory.setItem(i * 9 + 8, button.getIcon());
                }
            }
        }
    };


   public abstract void  decorate(Menu menu);
}
