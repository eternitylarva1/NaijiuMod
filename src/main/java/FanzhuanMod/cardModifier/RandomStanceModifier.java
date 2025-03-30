package FanzhuanMod.cardModifier;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//




import FanzhuanMod.helpers.ModHelper;
import FanzhuanMod.patchs.InterruptUseCardFieldPatches;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;

public class RandomStanceModifier extends AbstractCardModifier {
    public static String ID = ModHelper.makePath(RandomStanceModifier.class.getSimpleName());
    private static final UIStrings uiStrings;


    public RandomStanceModifier() {
    }
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
          }
    public String modifyDescription(String rawDescription, AbstractCard card) {
        //rawDescription中的格挡改为金属化
        return rawDescription.replace(uiStrings.TEXT[0], uiStrings.TEXT[1]);// 愤怒 -> 平静

    }

    public boolean shouldApply(AbstractCard card) {
        return !(CardModifierManager.hasModifier(card, ID))&&(card.rawDescription.contains(uiStrings.TEXT[0])||card.rawDescription.contains(uiStrings.TEXT[1]));
    }

    public void onInitialApplication(AbstractCard card) {

    }

    public void onRemove(AbstractCard card) {
        String temp = "TEMP_PLACEHOLDER";
        card.rawDescription = card.rawDescription.replace(uiStrings.TEXT[1], uiStrings.TEXT[0]); // 平静 -> 愤怒

    }

    public AbstractCardModifier makeCopy() {
        return new RandomStanceModifier();
    }

    public String identifier(AbstractCard card) {
        return ID;
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString(ID);
    }
}

