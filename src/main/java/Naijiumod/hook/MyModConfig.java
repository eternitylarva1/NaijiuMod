package Naijiumod.hook;

import Naijiumod.helpers.ModHelper;
import basemod.EasyConfigPanel;

public class MyModConfig extends EasyConfigPanel {
    public MyModConfig() {
        super(LoadMySpireMod.ModID, ModHelper.makePath(MyModConfig.class.getSimpleName()));
         setNumberRange("Rare",1,15);
         setNumberRange("UnCommon",1,15);
         setNumberRange("Common",1,15);
         setNumberRange("Other",1,15);
          setNumberRange("Attack",1,15);
          setNumberRange("Skill",1,15);
          setNumberRange("Power",1,15);
          setupTextField("Description", 0, 0);
    }
    public static String Description="";
    public static int Rare=6;
    public static int Damo=10;
    public static int UnCommon = 5;
    public static int Common = 4;
    public static int Other = 3;
    public static int Attack = 4;
    public static int Skill = 3;
    public static int Power = 2;
}
