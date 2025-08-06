package mojichimera.augments.uncommon;

import Naijiumod.cardModifier.AbstractAugment;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardBorderGlowManager;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mojichimera.augments.AugmentHelper;
import mojichimera.mojichimera;

public class DeadOnMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(DeadOnMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private static final int PERCENT = 33;
    private static final float MULTIPLIER = 1.3333334F;

    @Override
    public float modifyBaseDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        if (card.baseDamage > 0)
            return damage * (isDeadOn(card) ? MULTIPLIER : 1.0F);
        return damage;
    }

    @Override
    public float modifyBaseBlock(float block, AbstractCard card) {
        if (card.baseBlock > 0)
            return block * (isDeadOn(card) ? MULTIPLIER : 1.0F);
        return block;
    }

    private boolean isDeadOn(AbstractCard card) {
        if (AbstractDungeon.player == null || !AbstractDungeon.player.hand.contains(card)) {
            return false;
        }
        double hand_pos = AbstractDungeon.player.hand.group.indexOf(card) + 0.5;
        double hand_size = AbstractDungeon.player.hand.size();
        double relative = Math.abs(hand_pos - hand_size / 2.0);
        return relative < 1.0;
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return AugmentHelper.reachesDamageOrBlock(card, 3)
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
        return insertAfterText(rawDescription, String.format(CARD_TEXT[0], PERCENT));
    }

    @Override
    public AbstractAugment.AugmentRarity getModRarity() { return AbstractAugment.AugmentRarity.UNCOMMON; }

    @Override
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new DeadOnMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }

    public CardBorderGlowManager.GlowInfo getGlowInfo() {
        return new CardBorderGlowManager.GlowInfo() {
            public boolean test(AbstractCard card) {
                return DeadOnMod.this.hasThisMod(card) && DeadOnMod.this.isDeadOn(card);
            }

            public Color getColor(AbstractCard card) {
                return Color.GOLD.cpy();
            }

            public String glowID() {
                return DeadOnMod.ID + "Glow";
            }
        };
    }
}
