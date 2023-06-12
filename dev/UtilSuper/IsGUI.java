package UtilSuper;

public class IsGUI {
 //this will be static class that can return true or false if the program is running in GUI mode or not

    private static boolean isGUI;

    public static boolean getIsGUI()
    {
        return isGUI;
    }

    public static void setIsGUI(boolean isGUI)
    {
        IsGUI.isGUI = isGUI;
    }
}
