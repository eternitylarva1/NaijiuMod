package mojichimera.augments.uncommon;

import Naijiumod.cardModifier.AbstractAugment;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardBorderGlowManager;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import mojichimera.augments.AugmentHelper;
import mojichimera.mojichimera;
import mojichimera.util.MojiHelper;

public class HighCardMod extends AbstractAugment {
    public static final String ID = mojichimera.makeID(HighCardMod.class.getSimpleName());
    public static final String[] TEXT = CardCrawlGame.languagePack.getUIString(ID).TEXT;
    public static final String[] CARD_TEXT = CardCrawlGame.languagePack.getUIString(ID).EXTRA_TEXT;
    private static final float MULTIPLIER = 1.2F;
    private static final int PERCENT = 20;

    @Override
    public float modifyBaseDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        if (card.baseDamage > 0)
            return damage * getMultiplier(card);
        return damage;
    }

    @Override
    public float modifyBaseBlock(float block, AbstractCard card) {
        if (card.baseBlock > 0)
            return block * getMultiplier(card);
        return block;
    }

    private float getMultiplier(AbstractCard card) {
        if (!MojiHelper.isInCombat())
            return 1.0F;
        return allDistinctCards(AbstractDungeon.player.hand) ? MULTIPLIER : 1.0F;
    }

    private boolean allDistinctCards(CardGroup group) {
//        return group.group.stream().map(c -> c.cardID).distinct().count() == group.size();
        return group.group.stream().map(c -> c.name).distinct().count() == group.size();
    }

    @Override
    public boolean validCard(AbstractCard card) {
        return AugmentHelper.isPlayable(card)
                && AugmentHelper.reachesDamageOrBlock(card, 5)
                && AugmentHelper.isNormal(card)
                && !card.rarity.equals(AbstractCard.CardRarity.BASIC);
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
    public AbstractCardModifier makeCopy() { return (AbstractCardModifier)new HighCardMod(); }

    @Override
    public String identifier(AbstractCard card) { return ID; }

    private boolean shouldGlow(AbstractCard card) {
        return allDistinctCards(AbstractDungeon.player.hand);
    }

    public CardBorderGlowManager.GlowInfo getGlowInfo() {
        return new CardBorderGlowManager.GlowInfo() {
            public boolean test(AbstractCard card) {
                return HighCardMod.this.hasThisMod(card) && HighCardMod.this.shouldGlow(card);
            }

            public Color getColor(AbstractCard card) {
                return Color.GOLD.cpy();
            }

            public String glowID() {
                return HighCardMod.ID + "Glow";
            }
        };
    }
}
