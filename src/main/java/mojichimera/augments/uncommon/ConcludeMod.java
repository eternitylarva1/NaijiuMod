package mojichimera.augments.uncommon;

import Naijiumod.cardModifier.AbstractAugment;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.purple.Conclude;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mojichimera.augments.AugmentHelper;
import mojichimera.mojichimera;

public class ConcludeMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(ConcludeMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private static final float MULTIPLIER = 1.75F;
//    private boolean modMagic;

//    @Override
//    public void onInitialApplication(AbstractCard card) {
//        if (cardCheck(card, c -> (doesntDowngradeMagic() && c.baseMagicNumber > 0)))
//            this.modMagic = true;
//    }

    @Override
    public float modifyBaseDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        if (card.baseDamage > 0)
            return damage * MULTIPLIER;
        return damage;
    }

    @Override
    public float modifyBaseBlock(float block, AbstractCard card) {
        if (card.baseBlock > 0)
            return block * MULTIPLIER;
        return block;
    }

//    @Override
//    public float modifyBaseMagic(float magic, AbstractCard card) {
//        if (this.modMagic)
//            return magic * MULTIPLIER;
//        return magic;
//    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        if (card instanceof Conclude)
            return;
        addToBot(new PressEndTurnButtonAction());
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return AugmentHelper.reachesDamageOrBlock(card, 2)
                && AugmentHelper.isPlayable(card);
    }

    @Override
    public String getPrefix() { return TEXT[0]; }

    @Override
    public String getSuffix() { return TEXT[1]; }

    @Override
    public String getAugmentDescription() { return TEXT[2]; }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        if (card instanceof Conclude)
            return rawDescription;
        return insertAfterText(rawDescription, CARD_TEXT[0]);
    }

    @Override
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.UNCOMMON; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new ConcludeMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}