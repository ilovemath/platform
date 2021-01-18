package wang.hijack.mfe.gateway.model.enums;

/**
 * @author Jack
 */
public enum MenuType {
    /* 顶级菜单 */
    TOP,
    /* 子目录 */
    DIR,
    /* 菜单 */
    MENU,
    /* 按钮 */
    BUTTON,
    /*无效类型*/
    INVALID;

    public static MenuType from(String type) {
        switch (type) {
            case "TOP":
                return TOP;
            case "DIR":
                return DIR;
            case "MENU":
                return MENU;
            case "BUTTON":
                return BUTTON;
            default:
                return INVALID;
        }
    }

    public boolean isMenu() {
        return this == MENU;
    }
}
