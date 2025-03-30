package FanzhuanMod.cardModifier;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//




import FanzhuanMod.helpers.ModHelper;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.Electrodynamics;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.regex.Pattern;

public class LightningModifier extends AbstractCardModifier {
    public static String ID = ModHelper.makePath(LightningModifier.class.getSimpleName());
    private static final UIStrings uiStrings;


    public LightningModifier() {
    }
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
          }
    public String modifyDescription(String rawDescription, AbstractCard card) {
        //rawDescription中的格挡改为金属化

        String temp = "TEMP_PLACEHOLDER";
        if(card instanceof Electrodynamics){
            String target = uiStrings.TEXT[0]; // 要替换的目标词（如 "平静"）
            String replacement = "TEMP_PLACEHOLDER"; // 临时占位符
            String regex = "(?s)^((?:.*?" + Pattern.quote(target) + ".*?){1})" + Pattern.quote(target);
            String newDesc = rawDescription.replaceFirst(regex, "$1" + replacement);

            // 后续替换逻辑保持不变
            return newDesc
                    .replace(uiStrings.TEXT[1], uiStrings.TEXT[0])
                    .replace(replacement, uiStrings.TEXT[1]);
        }
        return rawDescription
                .replace(uiStrings.TEXT[0], temp)  // 平静 -> 临时
                .replace(uiStrings.TEXT[1], uiStrings.TEXT[0]) // 愤怒 -> 平静
                .replace(temp, uiStrings.TEXT[1]); // 临时 -> 愤怒

    }

    public boolean shouldApply(AbstractCard card) {
        return !(CardModifierManager.hasModifier(card, ID))&&(card.rawDescription.contains(uiStrings.TEXT[0])||card.rawDescription.contains(uiStrings.TEXT[1]));
    }

    public void onInitialApplication(AbstractCard card) {

    }

    public void onRemove(AbstractCard card) {
        String temp = "TEMP_PLACEHOLDER";
        card.rawDescription = card.rawDescription
                .replace(uiStrings.TEXT[1], temp)  // 愤怒 -> 临时
                .replace(uiStrings.TEXT[0], uiStrings.TEXT[1]) // 平静 -> 愤怒
                .replace(temp, uiStrings.TEXT[0]); // 临时 -> 平静
    }

    public AbstractCardModifier makeCopy() {
        return new LightningModifier();
    }

    public String identifier(AbstractCard card) {
        return ID;
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString(ID);
    }
}

