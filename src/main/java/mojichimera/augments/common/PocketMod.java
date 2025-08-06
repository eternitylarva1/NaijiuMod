package mojichimera.augments.common;

import Naijiumod.cardModifier.AbstractAugment;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mojichimera.augments.AugmentHelper;
import mojichimera.mojichimera;

public class PocketMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(PocketMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private int baseCost;
    private boolean modMagic;
    private final int COST = 0;

    @Override
    public void onInitialApplication(AbstractCard card) {
        this.baseCost = Math.max(2, card.cost);
        card.cost = COST;
        card.costForTurn = card.cost;
        if (cardCheck(card, c -> (doesntDowngradeMagic() && c.baseMagicNumber > 0)))
            this.modMagic = true;
    }

    @Override
    public float modifyBaseDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        if (card.baseDamage > 0) {
            return (float) Math.ceil(damage / this.baseCost);
        }
        return damage;
    }

    @Override
    public float modifyBaseBlock(float block, AbstractCard card) {
        if (card.baseBlock > 0) {
            return (float) Math.ceil(block / this.baseCost);
        }
        return block;
    }

    @Override
    public float modifyBaseMagic(float magic, AbstractCard card) {
        if (this.modMagic) {
            return (float) Math.ceil(magic / this.baseCost);
        }
        return magic;
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return AugmentHelper.hasVariable(card)
                && card.cost >= 2;
    }

    @Override
    public String getPrefix() { return TEXT[0]; }

    @Override
    public String getSuffix() { return TEXT[1]; }

    @Override
    public String getAugmentDescription() { return TEXT[2]; }

    @Override
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.COMMON; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new PocketMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}
