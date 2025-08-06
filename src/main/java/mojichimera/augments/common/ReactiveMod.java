package mojichimera.augments.common;

import Naijiumod.cardModifier.AbstractAugment;
import basemod.abstracts.AbstractCardModifier;
import basemod.cardmods.RetainMod;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import mojichimera.augments.AugmentHelper;
import mojichimera.mojichimera;

@SpirePatch(clz = AbstractCard.class, method = SpirePatch.CLASS)
public class ReactiveMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(ReactiveMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private static final int EFFECT = 1;
    public static final SpireField<Integer> bonusMagic = new SpireField<>(() -> 0);

    @Override
    public float modifyBaseMagic(float magic, AbstractCard card) {
        return magic + bonusMagic.get(card);
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
//        card.selfRetain = true;
        CardModifierManager.addModifier(card, new RetainMod());
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return AugmentHelper.hasMagic(card)
                && AugmentHelper.isRetainValid(card)
                && AugmentHelper.isNormal(card);
    }

    @Override
    public void onRetained(AbstractCard card) {
//        card.magicNumber += EFFECT;
        bonusMagic.set(card, bonusMagic.get(card) + EFFECT);
    }

    @Override
    public String getPrefix() { return TEXT[0]; }

    @Override
    public String getSuffix() { return TEXT[1]; }

    @Override
    public String getAugmentDescription() { return TEXT[2]; }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
//        if (rawDescription.contains(CARD_TEXT[0])) {
            return insertAfterText(rawDescription, String.format(CARD_TEXT[1], EFFECT));
//        }
//        return insertAfterText(insertBeforeText(rawDescription, CARD_TEXT[0]), String.format(CARD_TEXT[1], EFFECT));
    }

    @Override
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.COMMON; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new ReactiveMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}
