
package Naijiumod.patchs;

import Naijiumod.cardModifier.ItemMod;
import Naijiumod.hook.MyModConfig;
import Naijiumod.ui.FumoOption;
import Naijiumod.utils.Invoker;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.screens.CombatRewardScreen;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import com.megacrit.cardcrawl.ui.campfire.SmithOption;

import java.util.ArrayList;

public class CamfirePatch {
    @SpirePatch(clz = CampfireUI.class, method = "initializeButtons")
    public static class ModifyRewardScreenStuff {
        @SpirePrefixPatch
        public static void patch(CampfireUI __instance) {
            ArrayList<AbstractCampfireOption> buttons = Invoker.getField(__instance, "buttons");
            if (buttons != null) {
                boolean changed =false;
                for(AbstractCard c: AbstractDungeon.player.masterDeck.group){
                    if(CardModifierManager.hasModifier(c, ItemMod.ID)){
                         ItemMod itemMod    = (ItemMod)(CardModifierManager.getModifiers(c, ItemMod.ID)).get(0);
                         if (itemMod.damo >= MyModConfig.Damo){
                             changed = true;
                             break;
                         }
                    }
                }
                buttons.add(new FumoOption(changed));
            }
        }
    }
}
