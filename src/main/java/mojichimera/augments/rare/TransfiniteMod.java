package mojichimera.augments.rare;

import Naijiumod.cardModifier.AbstractAugment;
import Naijiumod.cardModifier.SearingMod;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import mojichimera.augments.AugmentHelper;
import mojichimera.mojichimera;

public class TransfiniteMod extends SearingMod {
    public static final String ID = mojichimera.makeID(TransfiniteMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;

//    @Override
//    public void onInitialApplication(AbstractCard card) {
//        InfiniteUpgradesPatches.InfUpgradeField.inf.set(card, true);
//    }

    @Override
    public boolean validCard(AbstractCard card) {
        return AugmentHelper.isPower(card)
                && card.canUpgrade()
                && cardCheck(card, c -> upgradesAVariable())
                && doesntOverride(card, "canUpgrade", new Class[0])
                && !(card instanceof com.evacipated.cardcrawl.mod.stslib.cards.interfaces.BranchingUpgradesCard)
                && !(card instanceof com.evacipated.cardcrawl.mod.stslib.cards.interfaces.MultiUpgradeCard);
    }

    @Override
    public String getPrefix() { return TEXT[0]; }

    @Override
    public String getSuffix() { return TEXT[1]; }

    @Override
    public String getAugmentDescription() { return TEXT[2]; }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return insertAfterText(rawDescription, CARD_TEXT[0]);
    }

    @Override
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.RARE; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new TransfiniteMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}
