package mojichimera.augments.rare;

import Naijiumod.cardModifier.AbstractAugment;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mojichimera.augments.AugmentHelper;
import mojichimera.mojichimera;
import mojichimera.util.MojiHelper;

public class UnsociableMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(UnsociableMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private static final int PERCENT = 25;
    private static final float MULTIPLIER = 2.0F;
    private static final float EXTRA_MULTIPLIER = 0.25F;

    @Override
    public float modifyDamageFinal(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        if (card.baseDamage > 0)
            return damage * getMultiplier(card);
        return damage;
    }

    @Override
    public float modifyBlockFinal(float block, AbstractCard card) {
        if (card.baseBlock > 0)
            return block * getMultiplier(card);
        return block;
    }

    private float getMultiplier(AbstractCard card) {
        if (!MojiHelper.isInCombat())
            return MULTIPLIER;
        float multiplier = MULTIPLIER;
        for (AbstractCard otherCard : AbstractDungeon.player.hand.group) {
            if (otherCard != card && otherCard.type == card.type) {
                multiplier -= EXTRA_MULTIPLIER;
            }
        }
        return Math.max(multiplier, 1.0F);
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return AugmentHelper.hasDamageOrBlock(card);
    }

    @Override
    public String getPrefix() { return TEXT[0]; }

    @Override
    public String getSuffix() { return TEXT[1]; }

    @Override
    public String getAugmentDescription() { return TEXT[2]; }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return insertAfterText(rawDescription, String.format(CARD_TEXT[0], PERCENT));
    }

    @Override
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.RARE; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new UnsociableMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }
}

