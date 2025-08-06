package mojichimera.augments.common;

import Naijiumod.cardModifier.AbstractAugment;
import basemod.abstracts.AbstractCardModifier;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import mojichimera.mojichimera;

@SpirePatch(clz = AbstractCard.class, method = SpirePatch.CLASS)
public class LanternMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(LanternMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private static final int EFFECT = 1;
    public static final SpireField<Integer> drawnTimes = new SpireField<>(() -> 0);

    @Override
    public boolean validCard(AbstractCard card) {
        return true;
    }

    @Override
    public void onDrawn(AbstractCard card) {
        drawnTimes.set(card, drawnTimes.get(card) + 1);
        if (drawnTimes.get(card) == 1) {
            addToBot(new GainEnergyAction(EFFECT));
        }
    }

    @Override
    public String getPrefix() { return TEXT[0]; }

    @Override
    public String getSuffix() { return TEXT[1]; }

    @Override
    public String getAugmentDescription() { return TEXT[2]; }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return insertAfterText(rawDescription, String.format(CARD_TEXT[0], 1));
    }

    @Override
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.COMMON; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new LanternMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}
