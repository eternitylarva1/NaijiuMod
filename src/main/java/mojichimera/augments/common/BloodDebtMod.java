package mojichimera.augments.common;

import Naijiumod.cardModifier.AbstractAugment;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.Hemokinesis;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import mojichimera.augments.AugmentHelper;
import mojichimera.mojichimera;

public class BloodDebtMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(BloodDebtMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private static final int HP = 2;

    @Override
    public void onInitialApplication(AbstractCard card) {
        card.cost--;
        if (card.cost < 0)
            card.cost = 0;
        card.costForTurn = card.cost;
        if (card instanceof Hemokinesis) {
            card.baseMagicNumber += HP;
            card.magicNumber = card.baseMagicNumber;
        }
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return AugmentHelper.hasStaticCost(card, 1);
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        if (card instanceof Hemokinesis)
            return;
        addToTop((AbstractGameAction)new LoseHPAction((AbstractCreature)AbstractDungeon.player, (AbstractCreature)AbstractDungeon.player, HP));
        addToTop((AbstractGameAction)new SFXAction("BLOOD_SPLAT", 0.8F));
    }

    @Override
    public String getPrefix() { return TEXT[0]; }

    @Override
    public String getSuffix() { return TEXT[1]; }

    @Override
    public String getAugmentDescription() { return TEXT[2]; }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        if (card instanceof Hemokinesis)
            return rawDescription;
        return insertBeforeText(rawDescription, String.format(CARD_TEXT[0], HP));
    }

    @Override
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.COMMON; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new BloodDebtMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}
